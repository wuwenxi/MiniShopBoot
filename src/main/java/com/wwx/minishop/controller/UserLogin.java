package com.wwx.minishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserLogin {


    @RequestMapping("/shopManagerLogin")
    public String shopManagerLogin(){
        return "login/shopManagerLogin";
    }

    @RequestMapping("/userLogin")
    public String userLogin(){
        return "login/userLogin";
    }

    @GetMapping("/register")
    public String userRegister(){
        return "login/userRegister";
    }

    @RequestMapping("/registerShop")
    public String registerShop(){
        return "registerShop";
    }
}
