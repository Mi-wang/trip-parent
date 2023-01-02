package cn.wolfcode.redis.key;

import cn.wolfcode.key.BaseRedisPrefix;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-02 002 15:33
 */
public class ArticleRedisPrefix extends BaseRedisPrefix {

    /**攻略统计数据前缀*/
    public static final ArticleRedisPrefix STRATEGIES_STAT_PREFIX = new ArticleRedisPrefix("strategies:stat");
    public static final ArticleRedisPrefix STRATEGIES_FAVOR_SET = new ArticleRedisPrefix("strategies:favor");

    protected ArticleRedisPrefix(String prefix) {
        super(prefix);
    }
}
