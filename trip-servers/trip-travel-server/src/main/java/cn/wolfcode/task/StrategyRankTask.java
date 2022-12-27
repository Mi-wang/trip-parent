package cn.wolfcode.task;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyRank;
import cn.wolfcode.service.IStrategyRankService;
import cn.wolfcode.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/26 19:12
 * 定时统计攻略排行信息，每小时执行一次
 */
@Component
public class StrategyRankTask {

    public static final Logger log = LoggerFactory.getLogger("StrategyRankTask");

    @Autowired
    private IStrategyRankService strategyRankService;

    @Autowired
    private IStrategyService strategyService;
    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statAbroad0StrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计国外数据-----------------");
        // 统计国外的数据
        List<Strategy> strategies = strategyService.list(new QueryWrapper<Strategy>()
                .eq("isabroad", Strategy.ABROAD_YES)
                // 点赞数 + 收藏数
                .orderByDesc("thumbsupnum+favornum")
                .last("limit 10"));

        strategyRankService.insertRanks(StrategyRank.TYPE_ABROAD, strategies,
                s -> (long) (s.getThumbsupnum() + s.getFavornum()));
        log.info("[攻略排行数据统计]-----------------国外数据统计结束-----------------");
    }

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statStrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计国内数据-----------------");
        // 统计国外的数据
        List<Strategy> strategies = strategyService.list(new QueryWrapper<Strategy>()
                .eq("isabroad", Strategy.ABROAD_NO)
                // 点赞数 + 收藏数
                .orderByDesc("thumbsupnum+favornum")
                .last("limit 10"));

        strategyRankService.insertRanks(StrategyRank.TYPE_CHINA, strategies,
                s -> (long) (s.getThumbsupnum() + s.getFavornum()));
        log.info("[攻略排行数据统计]-----------------国内数据统计结束-----------------");
    }

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statHotStrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计热门数据-----------------");
        // 统计国外的数据
        List<Strategy> strategies = strategyService.list(new QueryWrapper<Strategy>()
                // 浏览数 + 评论数
                .orderByDesc("viewnum+replynum")
                .last("limit 10"));

        strategyRankService.insertRanks(StrategyRank.TYPE_HOT, strategies,
                s -> (long) (s.getViewnum() + s.getReplynum()));
        log.info("[攻略排行数据统计]-----------------热门数据统计结束-----------------");
    }

}
