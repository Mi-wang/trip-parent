package cn.wolfcode.service.impl;

import cn.wolfcode.domain.*;
import cn.wolfcode.mapper.StrategyContentMapper;
import cn.wolfcode.mapper.StrategyMapper;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IDestinationService;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.service.IStrategyService;
import cn.wolfcode.service.IStrategyThemeService;
import cn.wolfcode.utils.OSSUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:43
 */
@Service
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements IStrategyService {

    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IDestinationService destinationService;
    @Resource
    private StrategyContentMapper strategyContentMapper;

    @Override
    public Page<Strategy> queryPage(BaseQuery qo) {
        // 条件构造器
        LambdaQueryWrapper<Strategy> wrapper = new LambdaQueryWrapper<Strategy>()
                // like(condition, column, value)
                // 当 condition 成立时，才会将这个条件拼接在 sql 语句上
                .like(StringUtils.hasLength(qo.getKeyword()), Strategy::getTitle, qo.getKeyword());

        // 分页方法
        return super.page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
    }

    @Override
    public boolean save(Strategy entity) {
        // 封面图片上传
        if (OSSUtils.isBase64Img(entity.getCoverUrl())) {
            String url = OSSUtils.uploadBase64ImgToOSS("images", entity.getCoverUrl());
            entity.setCoverUrl(url);
        }

        // 主题 =》 保存主题名称
        StrategyTheme theme = strategyThemeService.getById(entity.getThemeId());
        if (theme != null) {
            entity.setThemeName(theme.getName());
        }

        // 分类 =》 保存分类名称
        StrategyCatalog catalog = strategyCatalogService.getById(entity.getCatalogId());
        if (catalog != null) {
            entity.setCatalogName(catalog.getName());

            // 目的地 =》 保存目的地的id与名称
            entity.setDestId(catalog.getDestId());
            entity.setDestName(catalog.getDestName());

            // 是否国外 =》 基于目的地的地点来判断是否是国外
            List<Destination> toasts = destinationService.queryToasts(entity.getDestId());
            if (toasts != null && toasts.size() > 0) {
                Destination dest = toasts.get(0); // 最外层的国家对象
                if (dest.getId() != 1) { // 如果国家的 id != 1 表示就是国外
                    entity.setIsabroad(Strategy.ABROAD_YES);
                }
            }
        }

        // 创建时间
        entity.setCreateTime(new Date());
        super.save(entity); // 保存

        // 内容 =》 额外保存到内容表
        StrategyContent content = entity.getContent();
        content.setId(entity.getId()); // 将 id 设置为主对象的 id
        strategyContentMapper.insert(entity.getContent());

        return true;
    }
}

