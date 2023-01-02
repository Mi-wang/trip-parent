package cn.wolfcode.service;

import cn.wolfcode.key.KeyPrefix;

import java.util.Map;

/**
 * 专门用于访问 redis 的服务
 *
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

    /**
     *
     * @param prefix 前缀
     * @param hashFiled hash 字段
     * @param value    原子性增加得值
     * @param suffix   后缀
     */
    void hincr(K prefix, String hashFiled, V value, String... suffix);

    /**
     * 获取整个hash对象
     * @param prefix hash 前缀
     * @param suffix hash 后缀
     * @return 完整得 hash 对象
     */
    Map<Object, V> hgetAll(K prefix, String... suffix);

    Boolean isMember(K prefix, V value, String... suffix);
}
