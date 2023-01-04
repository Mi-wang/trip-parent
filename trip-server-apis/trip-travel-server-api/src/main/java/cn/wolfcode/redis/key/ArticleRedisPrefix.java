package cn.wolfcode.redis.key;

import cn.wolfcode.key.BaseRedisPrefix;

import java.util.concurrent.TimeUnit;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 15:33
 */
public class ArticleRedisPrefix extends BaseRedisPrefix {

    /**
     * 攻略统计数据前缀
     */
    public static final ArticleRedisPrefix STRATEGIES_STAT_PREFIX = new ArticleRedisPrefix("strategies:stat");
    /**
     * 攻略文章收藏用户集合
     */
    public static final ArticleRedisPrefix STRATEGIES_FAVOR_SET = new ArticleRedisPrefix("strategies:favor");

    /**
     * 攻略置顶用户集合
     */
    public static final ArticleRedisPrefix STRATEGIES_THUMB_SET = new ArticleRedisPrefix("strategies:thumb:set");

    /**
     * 1 小时内攻略文章变动 key 管理
     */
    public static final ArticleRedisPrefix STRATEGIES_STAT_KEY_CHANGE_ZSET = new ArticleRedisPrefix("strategies:stat:key:change:zset", 61L, TimeUnit.MINUTES);

    protected ArticleRedisPrefix(String prefix) {
        super(prefix);
    }

    public ArticleRedisPrefix(String prefix, long expireTime, TimeUnit unit) {
        super(prefix, expireTime, unit);
    }

}
