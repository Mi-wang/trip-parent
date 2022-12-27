package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyRank;
import cn.wolfcode.mapper.StrategyRankMapper;
import cn.wolfcode.service.IStrategyRankService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/26 19:14
 */
@Service
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper, StrategyRank> implements IStrategyRankService {

    @Override
    public void deleteByType(int typeAbroad) {
        super.remove(new LambdaQueryWrapper<StrategyRank>().eq(StrategyRank::getType, typeAbroad));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertRanks(int type, List<Strategy> strategies) {
        // 2. 删除之前的数据
        this.deleteByType(type);

        // 3. 将数据保存到表中
        List<StrategyRank> ranks = new ArrayList<>();
        Date now = new Date();

        for (Strategy strategy : strategies) {
            StrategyRank rank = new StrategyRank();
            rank.setDestId(strategy.getDestId());
            rank.setDestName(strategy.getDestName());
            rank.setStrategyTitle(strategy.getTitle());
            rank.setStrategyId(strategy.getId());
            rank.setType(type);
            rank.setStatisTime(now);
            // 统一相同规则
            rank.setStatisnum((long) (strategy.getThumbsupnum() + strategy.getFavornum()));

            // 加入到集合
            ranks.add(rank);
        }

        // 批量保存
        super.saveBatch(ranks);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertRanks(int type, List<Strategy> strategies, Function<Strategy, Long> statnumFun) {
        // 2. 删除之前的数据
        this.deleteByType(type);

        // 3. 将数据保存到表中
        List<StrategyRank> ranks = new ArrayList<>();
        Date now = new Date();

        for (Strategy strategy : strategies) {
            StrategyRank rank = new StrategyRank();
            rank.setDestId(strategy.getDestId());
            rank.setDestName(strategy.getDestName());
            rank.setStrategyTitle(strategy.getTitle());
            rank.setStrategyId(strategy.getId());
            rank.setType(type);
            rank.setStatisTime(now);
            // 统一相同规则
            rank.setStatisnum(statnumFun.apply(strategy));

            // 加入到集合
            ranks.add(rank);
        }

        // 批量保存
        super.saveBatch(ranks);
    }

    @Override
    public List<StrategyRank> queryByType(int type) {
        return list(new LambdaQueryWrapper<StrategyRank>().eq(StrategyRank::getType,type));
    }
}
