package com.gaoqiao.usercenterbacked;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gaoqiao.usercenterbacked.mapper")
public class UserCenterBackedApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterBackedApplication.class, args);
    }

}
