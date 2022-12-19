package cn.wolfcode.service.impl;

import cn.wolfcode.constants.SmsContants;
import cn.wolfcode.domain.UserInfo;
import cn.wolfcode.dto.UserRegisterDTO;
import cn.wolfcode.mapper.UserInfoMapper;
import cn.wolfcode.service.IUserInfoService;
import cn.wolfcode.utils.AssertUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/15 11:42
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
        String key = "sms:" + SmsContants.SMS_TYPE_REGISTER + ":send:" + registerDTO.getPhone();
        Object code = redisTemplate.opsForValue().get(key);
        AssertUtils.notNull(code,"验证码错误");

        // 3.获取到验证码，将该验证码与前端传入的验证码进行对比，如果错误提示验证码错误
        AssertUtils.isTrue(registerDTO.getCode().equals(code),"验证码错误");

        // 删除 redis 中的验证码
        redisTemplate.delete(key);

        // 4.用户密码加密
        String enpass = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());

        // 5.保存用户信息
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(registerDTO,userInfo);
        userInfo.setPassword(enpass);

        super.save(userInfo);
    }
}
