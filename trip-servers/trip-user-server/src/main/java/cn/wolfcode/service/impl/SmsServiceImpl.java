package cn.wolfcode.service.impl;

import cn.wolfcode.config.SmsProperties;
import cn.wolfcode.service.ISmsService;
import cn.wolfcode.utils.AssertUtils;
import cn.wolfcode.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:14
 */
@RefreshScope
@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SmsProperties smsProperties;

    @Override
    public void send(String phone, String type) {
        // 生成验证码
        String code = VerifyCodeUtils.genCode(6);

        //将验证码存入 redis, 设置过期时间为 30 分钟
        redisTemplate.opsForValue().set("sms:" + type + ":send:" + phone, code, 30, TimeUnit.MINUTES);

        // 获取验证码内容, 拼接验证码
        Map<String, String> typeMsg = smsProperties.getTypeMsg();
        AssertUtils.notNull(typeMsg, "短信配置错误");

        String content = typeMsg.get(type);
        AssertUtils.notEmpty(content, "发送短信类型错误");
        content = String.format(content, code);
        // 将 content 字符串中第一个 %s 替换为 code 的值

        // 调用第三方平台发送验证码
        // TODO: 2022/12/17 模拟第三方接口
        System.out.println(content);
    }

    public static void main(String[] args) {
        String str = "测试代码: %s";
        String code = "123456";

        System.out.println(String.format(str, code));
    }
}
