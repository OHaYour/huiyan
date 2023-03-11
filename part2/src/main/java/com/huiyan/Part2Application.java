package com.huiyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huiyan.dao")
public class Part2Application {

    public static void main(String[] args) {
        SpringApplication.run(Part2Application.class, args);
    }

}
