package cn.wolfcode.service.impl;

import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.domain.StrategyTheme;
import cn.wolfcode.mapper.StrategyCatalogMapper;
import cn.wolfcode.mapper.StrategyThemeMapper;
import cn.wolfcode.query.BaseQuery;
import cn.wolfcode.service.IStrategyCatalogService;
import cn.wolfcode.service.IStrategyThemeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:26
 */
@Service
public class StrategyThemeServiceImpl extends ServiceImpl<StrategyThemeMapper, StrategyTheme> implements IStrategyThemeService {

    @Override
    public Page<StrategyTheme> queryPage(BaseQuery qo) {
        // 条件构造器
        LambdaQueryWrapper<StrategyTheme> wrapper = new LambdaQueryWrapper<StrategyTheme>()

                // 当condition成立的时候，才会将这个条件拼接在 sql 语句上
                .like(StringUtils.hasLength(qo.getKeyword()), StrategyTheme::getName, qo.getKeyword());
        // 分页查询方法
        return super.page(new Page<>(qo.getCurrentPage(), qo.getPageSize()), wrapper);
    }
}
