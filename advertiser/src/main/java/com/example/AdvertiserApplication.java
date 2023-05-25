package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.dao")
@EnableTransactionManagement
@EnableDiscoveryClient
public class AdvertiserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertiserApplication.class, args);
    }

}
