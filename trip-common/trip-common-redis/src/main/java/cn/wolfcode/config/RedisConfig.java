package cn.wolfcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:20
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 外部 key/value 的序列化方式
        // 设置 redis key 的序列化方式为 String
        template.setKeySerializer(stringRedisSerializer);
        // 设置 value 的序列化方式为 jdk 的序列化
        // template.setValueSerializer(new JdkSerializationRedisSerializer());
        // 设置 value 的序列化方式为 jdk 的序列化
        template.setValueSerializer(genericJackson2JsonRedisSerializer);

        // hash key/value 的序列化方式
        // 设置 hash 类型的 value 序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // 设置 hash 类型的 value 序列化方式
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
