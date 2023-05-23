package com.ourfood.background;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.ourfood.background.dao")
@EnableTransactionManagement
@EnableDiscoveryClient
public class BackGroundApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackGroundApplication.class, args);
    }
}
