package cn.wolfcode.task;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.service.IRedisService;
import cn.wolfcode.service.IStrategyService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据落地
 * 将攻略数据同步到 MySQL 服务的任务
 * 执行周期: 每小时一次
 *
 * @author wby
 * @version 1.0
 * @date 2023-01-03 003 19:04
 */
@Component
public class StrategyStatDataToMysqlTask {

    public static final Logger log = LoggerFactory.getLogger("StrategyStatDataToMySQLTask");

    @Autowired
    private IRedisService<KeyPrefix, Object> redisService;
    @Autowired
    private IStrategyService strategyService;

    @Scheduled(cron = "0 0 * * * ?")
    public void syncData() {
        log.info("------------------Redis 攻略数据同步至 MySQL 开始---------------");
        // 基于 keys 命令模糊匹配到所有攻略统计数据的 key
        Set<String> keys = redisService.keys(ArticleRedisPrefix.STRATEGIES_STAT_PREFIX.join("*"));
        List<Strategy> strategies = new ArrayList<>(keys.size());

        for (String key : keys) {
            // 得到 key 以后从 redis 中得到统计数据
            Map<Object, Object> map = redisService.hgetAll(key);
            // 将其转换为攻略对象, 将攻略对象存入集合
            Strategy strategy = new Strategy();
            try {
                // 从 key 中获取到攻略 id
                strategy.setIsabroad(null);
                strategy.setState(null);
                Long id = this.getIdByKey(key);
                // 将 map 中的数据拷贝到目标攻略中
                BeanUtils.copyProperties(strategy, map);
                strategy.setId(id);
                // 将攻略对象存入批量更新的集合
                strategies.add(strategy);
            } catch (Exception e) {
                log.error("[攻略数据同步任务] Redis 同步攻略数据出错", e);
            }
        }

        // 批量更新数据
        strategyService.updateBatchById(strategies, 10);
        log.info("------------------Redis 攻略数据同步至 MySQL 结束---------------");
    }

    private Long getIdByKey(String key) {
        // 将 redis key 转换为 id
        String id = key.substring(key.lastIndexOf(":") + 1);
        return Long.valueOf(id);
    }
}
