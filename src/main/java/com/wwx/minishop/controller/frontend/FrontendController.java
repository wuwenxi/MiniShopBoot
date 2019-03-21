package com.wwx.minishop.controller.frontend;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.service.ShopCategoryService;
import com.wwx.minishop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendController {

    @Autowired
    ShopCategoryService shopCategoryService;

    private Map<String,Object> map = new HashMap<>();

    @GetMapping("/initShopCategory")
    public Msg initShopCategory(){
        List<ShopCategory> list = shopCategoryService.findAllShopCategory(new ShopCategory());
        if(list!=null && list.size()>0){
            map.put("categoryList",list);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","没有商品类别");
        }
    }

    @Autowired
    ShopService shopService;

    @GetMapping("/initWithShopCategory/{id}")
    public Msg initWithShopCategory(@PathVariable("id")Integer shopCategoryId){
        List<Shop> list = shopService.findShopListWithShopCategory(shopCategoryId);
        if(list!=null){
            map.put("list",list);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","没有当前类别的店铺");
        }
    }
}
