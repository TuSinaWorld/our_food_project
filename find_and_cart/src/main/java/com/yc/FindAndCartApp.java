package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 乐哥
 * @Date: 2023/5/23
 * @Time: 14:15
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.yc.mapper")
public class FindAndCartApp {
    public static void main(String[] args) {
        SpringApplication.run(FindAndCartApp.class);
    }
}
