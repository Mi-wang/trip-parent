package cn.wolfcode.service;

import cn.wolfcode.key.KeyPrefix;

/**
 * 专门用于访问 redis 的服务
 * @author 20463
 */
public interface IRedisService<K extends KeyPrefix, V> {

    /**
     * 访问 STRING 类型的设置值命令
     */
    void set(K prefix, V value, String... suffix);

    /**
     * 从 STRING 结构中获取 value
     */
    V get(K prefix, String... suffix);

    /**
     * 删除一个 key
     */
    void del(K prefix, String... suffix);
}
