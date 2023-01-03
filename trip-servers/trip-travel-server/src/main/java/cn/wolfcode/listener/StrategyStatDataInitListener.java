package cn.wolfcode.listener;

import cn.wolfcode.domain.Strategy;
import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.service.IRedisService;
import cn.wolfcode.service.IStrategyService;
import cn.wolfcode.vo.ArticleStatVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 攻略文章统计数据初始化监听器
 * 在 Spring 容器启动成功后执行
 * 负责将 MySQL 中的数据初始化到 Redis 中
 *
 * @author wby
 * @version 1.0
 * @date 2023-01-03 003 18:42
 */
@Component
public class StrategyStatDataInitListener implements ApplicationListener<ContextRefreshedEvent> {


    public static final Logger log = LoggerFactory.getLogger("ArticleStatDataInitListener");

    private final IStrategyService strategyService;
    private final IRedisService<KeyPrefix, Object> redisService;

    public StrategyStatDataInitListener(IRedisService<KeyPrefix, Object> redisService, IStrategyService strategyService) {
        this.redisService = redisService;
        this.strategyService = strategyService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("------------------文章统计数据初始化开始------------------");
        int count = 0;
        // 查询所有的文章
        List<Strategy> strategies = strategyService.list();
        for (Strategy strategy : strategies) {
            // 遍历文章, 判断是否在 redis 中存在
            Boolean exists = redisService.exists(ArticleRedisPrefix.STRATEGIES_STAT_PREFIX, strategy.getId() + "");
            if (!exists) {
                // 如果不存在, 将其存入 redis, 如果存在则不更新
                Map<String, Object> map = new HashMap<>();
                map.put(ArticleStatVo.FAVOR_NUM, strategy.getFavornum());
                map.put(ArticleStatVo.REPLAY_NUM, strategy.getReplynum());
                map.put(ArticleStatVo.SHARE_NUM, strategy.getSharenum());
                map.put(ArticleStatVo.THUMBS_NUM, strategy.getThumbsupnum());
                map.put(ArticleStatVo.VIEW_NUM, strategy.getViewnum());

                // 一次性将整个 map put 到 redis 的 hash 中
                redisService.hputAll(ArticleRedisPrefix.STRATEGIES_STAT_PREFIX, map, strategy.getId() + "");
                count++;
            }
        }
        log.info("------------------文章初始化完成, 初始化数量:{}------------------", count);
    }
}
