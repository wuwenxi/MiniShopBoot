package com.wwx.minishop.controller.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.service.PersonInfoService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ValidateUtil;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/personInfo")
public class LocalAuthController {

    @Autowired
    PersonInfoService personInfoService;

    @GetMapping("/initUserInfo")
    public Msg initUser(HttpServletRequest request){
        System.out.println("初始化用户...");
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if(personInfo!=null){
            return Msg.success().add("personInfo",personInfo);
        }
        return Msg.fail();
    }

    @PostMapping("/checkPersonInfo")
    public Msg checkPersonInfo(@Param("username")String username,@Param("password")String password,HttpServletRequest request){
        if(username!=null&&password!=null){
            System.out.println("username="+username+",password="+password);
            PersonInfo personInfo = personInfoService.findPersonInfoWithName(username);
            if(personInfo==null){
                return Msg.fail().add("msg","不存在当前用户名！");
            }
            if(!password.equals(personInfo.getPassword())){
                return Msg.fail().add("msg","密码错误！");
            }
            request.getSession().setAttribute("personInfo",personInfo);
            return Msg.success();
        }
        return Msg.fail().add("msg","请输入用户名及密码");
    }

    @PostMapping("/register")
    public Msg register(HttpServletRequest request){
        String personInfoStr = HttpServletRequestUtils.getString(request,"personInfo");
        PersonInfo personInfo;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            personInfo = objectMapper.readValue(personInfoStr,PersonInfo.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        if(personInfo == null){
            return Msg.fail().add("msg","用户信息为空，请填写用户信息");
        }

        if(personInfo.getUserName()!=null){
            PersonInfo info = personInfoService.findPersonInfoWithName(personInfo.getUserName());
            if(info!=null){
                return Msg.fail().add("msg","当前用户名已注册");
            }
        }

        //解析图片
        MultipartFile userImage = null;
        CommonsMultipartResolver resolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断是否有文件上传
            if(resolver.isMultipart(request)){
                MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
                userImage = servletRequest.getFile("userImg");
                if(!ValidateUtil.checkShopImage(userImage.getOriginalFilename())){
                    return Msg.fail().add("msg","文件格式错误！请传入图片");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = false;
        if(userImage!=null){
            try {
                flag = personInfoService.addPersonInfo(personInfo, new ImageHolder(userImage.getName(), userImage.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            flag = personInfoService.addPersonInfo(personInfo, null);
        }

        if(flag){
            return Msg.success();
        }else {
            return Msg.fail().add("msg","注册失败");
        }
    }
}
