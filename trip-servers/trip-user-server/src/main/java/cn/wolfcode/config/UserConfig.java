package cn.wolfcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/19 21:45
 */
@Configuration
public class UserConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
