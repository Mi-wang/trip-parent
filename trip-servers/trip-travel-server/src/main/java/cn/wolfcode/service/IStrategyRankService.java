package cn.wolfcode.service;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyRank;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/26 19:13
 */
public interface IStrategyRankService extends IService<StrategyRank> {

    void deleteByType(int typeAbroad);

    /**
     * 按照类型新增攻略排行统计数据
     */
    void insertRanks(int type, List<Strategy> strategies);

    void insertRanks(int type, List<Strategy> strategies, Function<Strategy, Long> statnumFun);
}

