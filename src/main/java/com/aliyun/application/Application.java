package com.aliyun.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author davisyou
 * @version 1.0
 * @date 2022/12/14 11:20 PM
 */
@SpringBootApplication
@MapperScan("com.aliyun.application")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
