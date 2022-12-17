package cn.wolfcode;

import cn.wolfcode.config.SmsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/11 20:30
 */
@SpringBootApplication
// 启用属性自动映射
@EnableConfigurationProperties(SmsProperties.class)
public class TripUserServer {
    public static void main(String[] args) {
        SpringApplication.run(TripUserServer.class,args);
    }
}
