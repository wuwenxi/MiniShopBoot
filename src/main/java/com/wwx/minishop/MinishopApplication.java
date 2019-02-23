package com.wwx.minishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching  //开启缓存
@SpringBootApplication
public class MinishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinishopApplication.class, args);
    }

}

