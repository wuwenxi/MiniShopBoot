package com.wwx.minishop.utils;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.entity.PersonInfo;
import javax.servlet.http.HttpServletRequest;

public class PersonInfoUtils {

    public static PersonInfo getPersonInfo(HttpServletRequest request){
        LocalAuth localAuth = (LocalAuth) request.getSession().getAttribute("localAuth");
        if(localAuth.getPersonInfo()!=null){
            request.getSession().setAttribute("personInfo",localAuth.getPersonInfo());
            return localAuth.getPersonInfo();
        }
        return null;
    }
}
