package com.wwx.minishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/shopManagementLogin")
    public String shopManagementLogin(){
        return "login/shopManagementLogin";
    }
}
