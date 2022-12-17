package cn.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/17 15:53
*/

@MapperScan("cn.wolfcode.mapper")
@Configuration
public class MyBatisPlusConfig {
}
