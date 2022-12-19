package cn.wolfcode.utils;

import cn.wolfcode.Constants;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.redis.key.UserRedisPrefix;
import cn.wolfcode.service.IRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class JwtUtils {

    // 令牌自定义标识
    @Value("${jwt.header}")
    private String header;

    // 令牌秘钥
    @Value("${jwt.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${jwt.expireTime}")
    private int expireTime;

    @Autowired
    private IRedisService<KeyPrefix, Object> redisService;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserInfo getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                UserInfo user = (UserInfo) redisService.get(UserRedisPrefix.USER_LOGIN_INFO, uuid);
                return user;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(UserInfo loginUser, String uuid) {
        if (loginUser != null && StringUtils.isNotEmpty(uuid)) {
            refreshToken(loginUser, uuid);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String uuid) {
        if (StringUtils.isNotEmpty(uuid)) {
            redisService.del(UserRedisPrefix.USER_LOGIN_INFO, uuid);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(UserInfo loginUser) {
        // 1. 创建 uuid，后面用于存入 redis 的 key
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 2. 将 uuid 作为 key 用户对象作为 value 存入 redis
        refreshToken(loginUser, uuid);

        // 3. 将 uuid 存入 jwt，方便后续查询用户
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, uuid);

        // 4. 创建 jwt token
        return createToken(claims);
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(UserInfo loginUser, String uuid) {
        // 根据 uuid 将 loginUser 缓存
        UserRedisPrefix prefix = UserRedisPrefix.USER_LOGIN_INFO;
        if (expireTime > 0) {
            // 如果配置文件中有，就使用配置文件中的，否则使用默认的
            prefix.setExpireTime(expireTime);
        }
        redisService.set(prefix, loginUser, uuid);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求 token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        return request.getHeader(header);
    }

    private String getTokenKey(String uuid) {
        // 存入 redis 的 key == login_user_key:{uuid}
        return UserRedisPrefix.USER_LOGIN_INFO.join(uuid);
    }
}
