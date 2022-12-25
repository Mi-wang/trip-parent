package cn.wolfcode.service;

import cn.wolfcode.domain.Region;
import cn.wolfcode.domain.StrategyCatalog;
import cn.wolfcode.domain.StrategyTheme;
import cn.wolfcode.query.BaseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:24
 */
public interface IStrategyThemeService extends IService<StrategyTheme> {
    
    Page<StrategyTheme> queryPage(BaseQuery qo);
}
