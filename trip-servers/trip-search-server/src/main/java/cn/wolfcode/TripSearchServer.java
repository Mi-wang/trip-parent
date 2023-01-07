package cn.wolfcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wby
 * @version 1.0
 * @date 2023-01-06 006 20:45
 */
@EnableFeignClients
@SpringBootApplication
public class TripSearchServer {

    public static void main(String[] args) {
        SpringApplication.run(TripSearchServer.class, args);
    }
}
