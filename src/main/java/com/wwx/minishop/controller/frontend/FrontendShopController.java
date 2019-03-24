package com.wwx.minishop.controller.frontend;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.service.ShopCategoryService;
import com.wwx.minishop.service.ShopService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendShopController {

    @Autowired
    ShopCategoryService shopCategoryService;

    @GetMapping("/initShopCategory")
    public Msg initShopCategory(Map<String,Object> map){
        List<ShopCategory> list = shopCategoryService.findAllShopCategory(new ShopCategory());
        if(list!=null && list.size()>0){
            map.put("categoryList",list);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","没有商品类别");
        }
    }

    /**
     *
     *                   根据子类别查询店铺
     * @param shopCategoryId
     * @return
     */
    @GetMapping("/initWithShopCategory/{shopCategoryId}")
    public Msg initWithShopCategory(@PathVariable("shopCategoryId") Integer shopCategoryId,@Param("pn")Integer pn,Map<String,Object> map){

        PageMethod.startPage(pn,20);

        ShopCategory shopCategory = new ShopCategory();
        //查询当前子类别下的所有店铺
        shopCategory.setShopCategoryId(shopCategoryId);
        //查询当前类别
        shopCategory = shopCategoryService.findShopCategoryById(shopCategoryId);

        List<Shop> list = shopService.findShopListWithShopCategory(shopCategory);

        map.put("shopCategory",shopCategory);

        //前端对list进行判断是否为空
        if(list ==null){
            list = new ArrayList<>();
        }
        PageInfo<Shop> pageInfo = new PageInfo<>(list,5);
        map.put("pageInfo",pageInfo);

        return Msg.success().add("map",map);
    }

    @Autowired
    ShopService shopService;

    /**
     *
     *                 根据一级类别查询店铺
     * @param parentId
     * @return
     */
    @GetMapping("/initShopWithParentId/{id}")
    public Msg initShopWithParentId(@PathVariable("id")Integer parentId,@Param("pn")Integer pn,Map<String,Object> map){

        //每一页最多店铺数
        PageMethod.startPage(pn,20);

        //1. 查出parentId下的所有子类别
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(parentId);
        shopCategory.setParent(parent);

        List<ShopCategory> shopCategories = shopCategoryService.findShopCategoryWithParentId(shopCategory);
        //2.查出每个子类别下的所有店铺
        List<Shop> shops = new ArrayList<>();
        for (ShopCategory category:shopCategories){
            List<Shop> list = shopService.findShopListWithShopCategory(category);
            if(list!=null && list.size()>0){
                shops.addAll(list);
            }
        }

        if(shops.size() > 0){
            PageInfo<Shop> pageInfo = new PageInfo<>(shops,5);
            map.put("pageInfo",pageInfo);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","当前类别下没有店铺");
        }
    }

    /**
     *
     *         获取店铺信息
     * @param id
     * @return
     */
    @GetMapping("/getShop/{id}")
    public Msg getShop(@PathVariable("id")Integer id,Map<String,Object> map){

        Shop shop = shopService.getShopById(id);
        map.put("shop",shop);
        return Msg.success().add("map",map);
    }
}
