package cn.wolfcode.key;

import java.util.concurrent.TimeUnit;

public class BaseRedisPrefix implements KeyPrefix {
    // 默认的 redis key, 有效时间为 30 分钟
//    DEFAULT("wolf2w", TimeUnit.MINUTES, 30L); // 最少需要写一个公共常量对象
    public static final BaseRedisPrefix DEFAULT = new BaseRedisPrefix("wolf2w", 30L, TimeUnit.MINUTES);

    public static final String SEP = ":";

    protected BaseRedisPrefix(String prefix) {
        // 如果后期使用时, 超时时间为 -1, 就代表没有超时时间
        this(prefix, -1L, TimeUnit.SECONDS);
    }

    protected BaseRedisPrefix(String prefix, long expireTime, TimeUnit unit) {
        this.prefix = prefix;
        this.unit = unit;
        this.expireTime = expireTime;
    }

    private String prefix; // 前缀
    private TimeUnit unit; // 单位
    private long expireTime; // 过期时间

    @Override
    public String join(String... suffix) {
        // 没有后缀, 就直接返回前缀
        if (suffix == null || suffix.length == 0) {
            return this.prefix;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix); // 默认拼接前缀

        for (String s : suffix) { // 如果有后缀的话, 拼接后缀, 并且分隔符为 :
            sb.append(SEP).append(s);
        }

        return sb.toString();
    }

    @Override
    public TimeUnit getUnit() {
        return this.unit;
    }

    @Override
    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
