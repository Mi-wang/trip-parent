package cn.wolfcode.key;

import java.util.concurrent.TimeUnit;

/**
 * 接口：定义规范
 * key 的前缀
 */
public interface KeyPrefix {

    /**
     * 将多个后缀与指定的前缀拼接起来
     */
    String join(String... suffix);

    /**
     * 返回 key 的过期单位
     */
    TimeUnit getUnit();

    /**
     * 获取 key 过期时间
     */
    long getExpireTime();
}
