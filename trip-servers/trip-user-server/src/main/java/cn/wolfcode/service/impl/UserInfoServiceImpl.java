package cn.wolfcode.service.impl;

import cn.wolfcode.constants.SmsContants;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.dto.UserRegisterDTO;
import cn.wolfcode.key.KeyPrefix;
import cn.wolfcode.mapper.UserInfoMapper;
import cn.wolfcode.redis.key.UserRedisPrefix;
import cn.wolfcode.service.IRedisService;
import cn.wolfcode.service.IUserInfoService;
import cn.wolfcode.utils.AssertUtils;
import cn.wolfcode.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/15 11:42
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private IRedisService<KeyPrefix, Object> redisService;

    @Autowired
    private UserTokenService tokenService;

    @Override
    public UserInfo getByPhone(String phone) {
        return getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, phone));
    }

    @Override
    public void register(UserRegisterDTO registerDTO) {
        // 1. 验证手机号是否存在， 如果存在抛出异常提示手机号已存在
        UserInfo exists = getByPhone(registerDTO.getPhone());
        AssertUtils.isNull(exists, "该手机号已注册");

        // 2.基于手机号从 redis 中获取验证码，如果获取不到，提示验证码错误//
        Object code = redisService.get(UserRedisPrefix.SMS_REGISTER, "send", registerDTO.getPhone());
        AssertUtils.notNull(code, "验证码错误");

        // 删除 redis 中的验证码
        redisService.del(UserRedisPrefix.SMS_REGISTER, "send", registerDTO.getPhone());

        // 3.获取到验证码，将该验证码与前端传入的验证码进行对比，如果错误提示验证码错误
        AssertUtils.isTrue(registerDTO.getCode().equals(code),"验证码错误");

        // 4.用户密码加密
        String enpass = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());

        // 5.保存用户信息
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(registerDTO,userInfo);
        // 设置为加密的密码
        userInfo.setPassword(enpass);

        super.save(userInfo);
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        // 1. 校验用户名是否正确
        UserInfo info = getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, username));
        AssertUtils.notNull(info, "用户名或密码错误");

        // 2. 校验密码是否正确（数据库的密码是加密的）
        // BCrypt.checkpw(前端未加密的密码， 数据库中已加密的密码)
        AssertUtils.isTrue(BCrypt.checkpw(password, info.getPassword()), "用户名或密码错误");

        // 3. 生成 token
        // 4. 以 token 为 key，用户为 value 保存到 redis
        // 5. 利用 Jwt 创建 token，将自己生成的 token 存入 Jwt
        String token = tokenService.createToken(info);

        // 6. 封装 Jwt 的 token 与用户对象到 map 中
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", info);
        return result;
    }
}
