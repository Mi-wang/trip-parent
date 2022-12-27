package cn.wolfcode.task;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.domain.StrategyCondition;
import cn.wolfcode.domain.StrategyRank;
import cn.wolfcode.service.IStrategyConditionService;
import cn.wolfcode.service.IStrategyRankService;
import cn.wolfcode.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 9:56
 */
@Component
public class StrategyConditionTask {

    public static final Logger log = LoggerFactory.getLogger("StrategyRankTask");

    @Autowired
    private IStrategyConditionService strategyConditionService;

    @Autowired
    private IStrategyService strategyService;

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void statAbroad0StrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计国外数据-----------------");
        // 统计国外的数据
        List<Map<String, Object>> conditions = strategyService.listMaps(new QueryWrapper<Strategy>()
                .select("dest_id refid, dest_name name, count(*) count")
                .eq("isabroad", Strategy.ABROAD_YES)
                .groupBy("dest_id", "dest_name"));

        strategyConditionService.insertConditions(StrategyCondition.TYPE_ABROAD, conditions);
        log.info("[攻略排行数据统计]-----------------国外数据统计结束-----------------");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void statStrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计国外数据-----------------");
        // 统计国外的数据
        List<Map<String, Object>> conditions = strategyService.listMaps(new QueryWrapper<Strategy>()
                .select("dest_id refid, dest_name name, count(*) count")
                .eq("isabroad", Strategy.ABROAD_NO)
                .groupBy("dest_id", "dest_name"));

        strategyConditionService.insertConditions(StrategyCondition.TYPE_CHINA, conditions);
        log.info("[攻略排行数据统计]-----------------国外数据统计结束-----------------");
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void statHotStrategyRanking() {
        log.info("[攻略排行数据统计]-----------------开始统计国外数据-----------------");
        // 统计国外的数据
        List<Map<String, Object>> conditions = strategyService.listMaps(new QueryWrapper<Strategy>()
                .select("theme_id refid, theme_name name, count(*) count")
                .eq("isabroad", Strategy.ABROAD_YES)
                .groupBy("theme_id", "theme_name"));

        strategyConditionService.insertConditions(StrategyCondition.TYPE_THEME, conditions);
        log.info("[攻略排行数据统计]-----------------国外数据统计结束-----------------");
    }

}
