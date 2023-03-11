package com.huiyan;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.huiyan.util.BaiduMapUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.huiyan.dao")
public class Part1Application {

    public static void main(String[] args) {
        SpringApplication.run(Part1Application.class, args);

    }

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
