package com.wwx.minishop.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/shopManagerLogin")
                .failureUrl("/shopManagerLogin?error=true")
                .defaultSuccessUrl("/shopAdmin")//跳转指定页面
                .and()
                .authorizeRequests()
                .antMatchers("/shopManagerLogin","/userLogin").permitAll()
                .antMatchers("/shopAdmin","/shopAdmin/**",
                        "/shop/**","/product/**","/personInfo/**").hasAuthority("shopAdmin");

        http.logout()
                .permitAll().
                invalidateHttpSession(true).
                deleteCookies("remember-me").
                logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .sessionManagement()
                .maximumSessions(10)
                .expiredUrl("/shopManagerLogin");
        //
        http.rememberMe()
                .rememberMeParameter("remember-me").tokenValiditySeconds(1209600);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return (httpServletRequest, httpServletResponse, authentication) -> {
            try {
                String localAuthName = authentication.getName();
                //logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
                System.out.println("USER : " + localAuthName + " LOGOUT SUCCESS !  ");
            } catch (Exception e) {
                System.out.println("LOGOUT EXCEPTION , e : " + e.getMessage());
            }
            httpServletResponse.sendRedirect("/shopManagerLogin");
        };
    }

}
