package cn.wolfcode;

import cn.wolfcode.handler.KeyChangeHandler;
import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.redis.key.ArticleRedisPrefix;
import cn.wolfcode.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-04 004 19:04
 */
@Component
public class ArticleStatKeyChangeHandler implements KeyChangeHandler {

    public static final Logger log = LoggerFactory.getLogger("ArticleStatKeyChangeHandler");

    @Autowired
    private IRedisService<KeyPrefix, Object> redisService;

    @Override
    public void handle(String key, Object... arguments) {
        log.info("[文章统计key处理] key:{} 发生改变, 准备持久化并记录改变数量", key);
        Double newScore = redisService.zincrBy(ArticleRedisPrefix.STRATEGIES_STAT_KEY_CHANGE_ZSET, key, 1);
        log.info("[文章统计key处理] key:{} 分数添加成功, 新的分数为:{}", key, newScore);
    }
}

