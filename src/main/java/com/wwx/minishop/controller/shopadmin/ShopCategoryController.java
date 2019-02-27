package com.wwx.minishop.controller.shopadmin;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopCategoryController {

    @Autowired
    ShopCategoryService shopCategoryService;

    private Map<String,Object> map = new HashMap<>();

    @GetMapping("/getshopinitinfo/{parentId}")
    public Msg getShopInitInfo(HttpServletRequest request, @PathVariable("parentId") Integer parentId){

        List<ShopCategory> shopCategoryList = (List<ShopCategory>) request.getSession().getAttribute("shopCategoryList");
        if(shopCategoryList!=null && shopCategoryList.size()>0){
            map.put("shopCategoryList",shopCategoryList);
            return Msg.success().add("map",map);
        }


        if(parentId==null||parentId<0){
            return Msg.fail().add("msg","查询类别错误");
        }

        ShopCategory parentCategory = new ShopCategory();
        ShopCategory childCategory = new ShopCategory();
        parentCategory.setShopCategoryId(parentId);
        childCategory.setParent(parentCategory);

        try {
            shopCategoryList = shopCategoryService.findShopCategoryWithParentId(childCategory);
            map.put("shopCategoryList",shopCategoryList);
            request.getSession().setAttribute("shopCategoryList",shopCategoryList);
            return Msg.success().add("map",map);
        } catch (Exception e) {
            return Msg.fail().add("msg","服务器错误");
        }
    }

}
