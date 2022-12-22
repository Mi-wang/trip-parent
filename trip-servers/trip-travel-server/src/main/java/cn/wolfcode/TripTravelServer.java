package cn.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/11 20:30
 */
@SpringBootApplication
public class TripTravelServer {
    public static void main(String[] args) {
        SpringApplication.run(TripTravelServer.class,args);
    }
}
