package com.wwx.minishop.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    /*将配置的druid加入到容器中*/
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /*配置druid监控*/
    /*1.配置一个管理后台的servlet*/
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String > initParam = new HashMap<>();

        initParam.put("loginUsername", "admin");
        initParam.put("loginPassword", "123456");
        initParam.put("allow", "");// 默认就是允许所有访问

        bean.setInitParameters(initParam);

        return bean;
    }

    /*2.配置一个filter*/
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String > initParam = new HashMap<>();
        /*不拦截js、css文件以及/druid/下的所有请求*/
        initParam.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParam);
        /*拦截所有请求*/
        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }
}
