package cn.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wby
 * @version 1.0
 * @date 2022/12/11 20:30
 */
@EnableScheduling
@SpringBootApplication
public class TripTravelServer {
    public static void main(String[] args) {
        SpringApplication.run(TripTravelServer.class,args);
    }
}
