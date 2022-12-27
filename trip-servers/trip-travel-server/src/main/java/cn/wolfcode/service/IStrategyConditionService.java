package cn.wolfcode.service;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyCondition;
import cn.wolfcode.domain.StrategyRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 9:41
 */
public interface IStrategyConditionService extends IService<StrategyCondition> {

    void deleteByType(int type);

    /**
     * 按照类型新增攻略排行统计数据
     */
    void insertConditions(int type, List<Map<String, Object>> conditions);

    List<StrategyCondition> queryByType(int type);
}
