package cn.wolfcode.service.impl;

import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void hincr(KeyPrefix prefix, String hashFiled, Object value, String... suffix) {
        redisTemplate.opsForHash().increment(prefix.join(suffix), hashFiled, (Long) value);
    }

    @Override
    public Map<Object, Object> hgetAll(KeyPrefix prefix, String... suffix) {
        return redisTemplate.opsForHash().entries(prefix.join(suffix));
    }

    @Override
    public Boolean isMember(KeyPrefix prefix, Object value, String... suffix) {
        return redisTemplate.opsForSet().isMember(prefix.join(suffix), value);
    }

    @Override
    public void sdel(KeyPrefix prefix, Object value, String... suffix) {
        redisTemplate.opsForSet().remove(prefix.join(suffix), value);
    }

    @Override
    public void sadd(KeyPrefix prefix, Object value, String... suffix) {
        redisTemplate.opsForSet().add(prefix.join(suffix), value);
    }

    @Override
    public void expire(KeyPrefix prefix, long expireTime, TimeUnit unit, String... suffix) {
        redisTemplate.expire(prefix.join(suffix), expireTime, unit);
    }

    @Override
    public Boolean exists(KeyPrefix prefix, String... suffix) {
        return redisTemplate.hasKey(prefix.join(suffix));
    }

    @Override
    public void hputAll(KeyPrefix prefix, Map<String, Object> map, String... suffix) {
        redisTemplate.opsForHash().putAll(prefix.join(suffix), map);
    }
}
