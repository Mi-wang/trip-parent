package cn.wolfcode.redis.key;

import cn.wolfcode.Constants;
import cn.wolfcode.constants.SmsContants;
import cn.wolfcode.key.BaseRedisPrefix;

import java.util.concurrent.TimeUnit;

/**
 * @author 20463
 */
public class UserRedisPrefix extends BaseRedisPrefix {

    public static final String SMS_USER_REGISTER_PREFIX = "sms:" + SmsContants.SMS_TYPE_REGISTER;

    // 用户注册发短信 redis key
    public static final UserRedisPrefix SMS_REGISTER = new UserRedisPrefix(SMS_USER_REGISTER_PREFIX, 30L, TimeUnit.MINUTES);
    // 用户登录信息 key
    public static final UserRedisPrefix USER_LOGIN_INFO = new UserRedisPrefix(Constants.LOGIN_USER_KEY, 30L, TimeUnit.MINUTES);

    public UserRedisPrefix(String prefix) {
        this(prefix, -1L, TimeUnit.SECONDS);
    }

    public UserRedisPrefix(String prefix, long expireTime, TimeUnit unit) {
        super(prefix, expireTime, unit);
    }
}
