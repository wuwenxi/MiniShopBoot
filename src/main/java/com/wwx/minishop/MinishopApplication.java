package com.wwx.minishop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching  //开启缓存
@SpringBootApplication
@MapperScan("com.wwx.minishop.dao")
public class MinishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopApplication.class, args);
    }

}

