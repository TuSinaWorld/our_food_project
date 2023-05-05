package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 乐哥
 * @Date: 2023/5/5
 * @Time: 19:43
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApp {
    public static void main(String[] args){
        SpringApplication.run(  GateWayApp.class, args);
    }

}
