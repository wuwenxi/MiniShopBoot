package com.wwx.minishop.controller.shopadmin;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {

    @Autowired
    LocalAuthService localAuthService;

    private Map<String,Object> map = new HashMap<>();

    @GetMapping("/initPersonInfo")
    public Msg initPersonInfo(HttpServletRequest request){
        String localAuthName = request.getUserPrincipal().getName();
        if(localAuthName!=null){
            LocalAuth localAuth = localAuthService.findLocalAuthWithName(localAuthName);
            if(localAuth!=null){
                request.getSession().setAttribute("localAuth",localAuth);
                map.put("personInfo",localAuth.getPersonInfo());
                return Msg.success().add("map",map);
            }
        }
        map.put("msg","未登录");
        return Msg.fail().add("map",map);
    }

}
