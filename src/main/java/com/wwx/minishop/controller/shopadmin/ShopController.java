package com.wwx.minishop.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.PersonInfoUtils;
import com.wwx.minishop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController()
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    private ShopExecution execution = new ShopExecution();

    private Map<String,Object> map = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @PutMapping("/modifyshop")
    public Msg ModifyShop(HttpServletRequest request) throws IOException {
        String shopStr = HttpServletRequestUtils.getString(request,"shop");
        Shop shop;
        try {
            shop = objectMapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        //解析图片
        MultipartFile shopImage = null;
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(resolver.isMultipart(request)){
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            shopImage = servletRequest.getFile("shopImg");
        }

        PersonInfo person = PersonInfoUtils.getPersonInfo(request);
        shop.setOwner(person);

        if(shopImage!=null){
            if(!ValidateUtil.checkShopImage(shopImage.getOriginalFilename())){
                return Msg.fail().add("msg","文件格式错误！请传入图片");
            }
            execution = shopService.modifyShop(shop, new ImageHolder(shopImage.getOriginalFilename(),shopImage.getInputStream()));
        }else
            execution = shopService.modifyShop(shop,null);

        if (execution.getState().equals(ShopStateEnum.SUCCESS.getState())){
            return Msg.success().add("msg","更新成功");
        }else {
            return Msg.fail().add("msg","更新失败");
        }
    }

    @PostMapping("/registershop")
    public Msg AddShop(HttpServletRequest request){
        //转换json数据
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");
        Shop shop;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().add("msg", "服务器内部错误");
        }

        if (shop==null){
            return Msg.fail().add("msg","请填写店铺信息");
        }

        //解析图片
        MultipartFile shopImage;
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(resolver.isMultipart(request)){
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            shopImage = servletRequest.getFile("shopImg");
        } else {
            return Msg.fail().add("msg", "上传图片不能为空");
        }

        if(!ValidateUtil.checkShopImage(shopImage.getOriginalFilename())){
            return Msg.fail().add("msg","文件格式错误！请传入图片");
        }

        try {
            //前端页面不添加用户信息
            PersonInfo info = PersonInfoUtils.getPersonInfo(request);
            shop.setOwner(info);

            execution = shopService.addShop(shop,new ImageHolder(shopImage.getOriginalFilename(),shopImage.getInputStream()));

            if (execution.getState().equals(ShopStateEnum.CHECK.getState())) {
                return Msg.success().add("execution",execution);
            } else {
                return Msg.fail().add("msg", execution.getStateInfo());
            }
        } catch (Exception e) {
            return Msg.fail().add("msg:","服务器内部错误");
        }
    }

    @GetMapping("/getshop/{shopId}")
    public Msg getShopById(@PathVariable("shopId") Integer shopId){
        if(shopId!=null){
            Shop shop = shopService.getShopById(shopId);
            map.put("shop",shop);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","无效商铺");
        }
    }

    @GetMapping("/getshoplist")
    public Msg getShopList(HttpServletRequest request){
        PersonInfo personInfo = PersonInfoUtils.getPersonInfo(request);
        try {
            Shop shop = new Shop();
            shop.setOwner(personInfo);
            if(personInfo!=null && personInfo.getUserId()!=null && personInfo.getUserId()>0){
                List<Shop> shopList = shopService.findShopListWithOwner(shop);
                if(shopList!=null && shopList.size()> 0 ){
                    map.put("shops",shopList);
                    map.put("count",shopList.size());
                }else {
                    map.put("msg","当前账户没有开设店铺");
                }
                return Msg.success().add("map",map);
            }
            map.put("msg","服务器内部错误");
            return Msg.fail().add("map",map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","服务器内部错误");
            return Msg.fail().add("map",map);
        }
    }
}
