package cn.wolfcode.service.impl;

import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 20463
 */
@Service
public class RedisTemplateService implements IRedisService<KeyPrefix, Object> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(KeyPrefix prefix, Object value, String... suffix) {
        if (prefix.getExpireTime() > -1) {
            // 需要过期时间
            redisTemplate.opsForValue().set(prefix.join(suffix), value, prefix.getExpireTime(), prefix.getUnit());
        } else {
            // 不需要过期时间
            redisTemplate.opsForValue().set(prefix.join(suffix), value);
        }
    }

    @Override
    public Object get(KeyPrefix prefix, String... suffix) {
        return redisTemplate.opsForValue().get(prefix.join(suffix));
    }

    @Override
    public void del(KeyPrefix prefix, String... suffix) {
        redisTemplate.delete(prefix.join(suffix));
    }
}
