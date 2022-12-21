package cn.wolfcode.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 16:37
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class WebConfig {

    /*@Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许 所有请求方法， 如 POST | GET
        config.addAllowedMethod("*");
        // 允许携带所有的请求头
        config.addAllowedHeader("*");
        // 允许任意来源，若是需要指定  写指定的服务器的域名就行了
        config.addAllowedOrigin("*");
        // 允许携带token
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 匹配那些路径使用这个规则
        source.registerCorsConfiguration("/**",config);
        // 创建跨域请求过滤器
        return new CorsWebFilter(source);
    }*/
}
