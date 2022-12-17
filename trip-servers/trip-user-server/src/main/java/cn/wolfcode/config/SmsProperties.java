package cn.wolfcode.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 17:10
 * 自动将配置文件前缀为 sms 的属性名, 匹配当前类中的属性并设置值
 */
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private Map<String, String> typeMsg;

    public Map<String, String> getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(Map<String, String> typeMsg) {
        this.typeMsg = typeMsg;
    }
}
