package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Banner;
import cn.wolfcode.mapper.BannerMapper;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IBannerService;
import cn.wolfcode.utils.OSSUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 18:49
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    public Page<Banner> queryPage(BaseQuery qo) {
        // 条件构造器
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<Banner>()
                // like(condition, column, value)
                // 当 condition 成立时，才会将这个条件拼接在 sql 语句上
                .like(StringUtils.hasLength(qo.getKeyword()), Banner::getTitle, qo.getKeyword())
                // 进行排序
                .orderByAsc(Banner::getSeq);

        // 分页方法
        return super.page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
    }

    @Override
    public List<Banner> listByType(int type, int num) {
        // 基于类型 + 状态进行搜索
        // 基于 seq 进行排序
        // 最终只查前 5 条
        LambdaQueryWrapper<Banner> wrapper = Wrappers.<Banner>lambdaQuery()
                .eq(Banner::getType, type)
                .eq(Banner::getState, Banner.STATE_NORMAL)
                .orderByAsc(Banner::getSeq)
                .last("limit " + num);

        return list(wrapper);
    }

    @Override
    public boolean save(Banner entity) {
        // 处理文件上传
        if (OSSUtils.isBase64Img(entity.getCoverUrl())) {
            String url = OSSUtils.uploadBase64ImgToOSS("images", entity.getCoverUrl());
            entity.setCoverUrl(url);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(Banner entity) {
        // 处理文件上传
        if (OSSUtils.isBase64Img(entity.getCoverUrl())) {
            String url = OSSUtils.uploadBase64ImgToOSS("images", entity.getCoverUrl());
            entity.setCoverUrl(url);
        }
        return super.updateById(entity);
    }
}

