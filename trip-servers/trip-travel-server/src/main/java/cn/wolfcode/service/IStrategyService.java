package cn.wolfcode.service;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.query.BaseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/25 15:42
 */
public interface IStrategyService extends IService<Strategy> {

    Page<Strategy> queryPage(BaseQuery qo);
}
