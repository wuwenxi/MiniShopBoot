package com.wwx.minishop.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.service.ProductCategoryService;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ShopService shopService;

    private Map<String,Object> map = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @DeleteMapping("/deleteProductCategory/{productCategoryId}")
    public Msg deleteProductCategory(HttpServletRequest request, @PathVariable("productCategoryId")Integer productCategoryId){
        if(productCategoryId>0){
            int num =  productCategoryService.deleteProductCategory(productCategoryId);
            if(num>0){
                List<ProductCategory> productCategoryList = (List<ProductCategory>) request.getSession().getAttribute("productCategoryList");
                if(productCategoryList!=null && productCategoryList.size()>0){
                    for(ProductCategory productCategory:productCategoryList){
                        if(productCategory.getProductCategoryId().equals(productCategoryId)){
                            productCategoryList.remove(productCategory);
                            break;
                        }
                    }
                    request.getSession().setAttribute("productCategoryList",productCategoryList);
                }
                return Msg.success();
            }
        }
        map.put("msg","删除失败");
        return Msg.fail().add("map",map);
    }

    @PutMapping("/modifyProductCategory")
    public Msg modifyProductCategory(HttpServletRequest request){
        String categoryStr = HttpServletRequestUtils.getString(request,"productCategory");

        ProductCategory productCategory = null;

        try {
            productCategory = objectMapper.readValue(categoryStr,ProductCategory.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(productCategory!=null && productCategory.getProductCategoryId()>0){
            int num = productCategoryService.modifyProductCategory(productCategory);
            if (num>0){
                List<ProductCategory> productCategoryList = (List<ProductCategory>)request.getSession().getAttribute("productCategoryList");
                if(productCategoryList!=null && productCategoryList.size()>0){
                    for (ProductCategory productCategory1:productCategoryList){
                        if(productCategory1.getProductCategoryId().equals(productCategory.getProductCategoryId())){
                            productCategoryList.remove(productCategory1);
                            productCategoryList.add(productCategory);
                            break;
                        }
                    }
                    request.getSession().setAttribute("productCategoryList",productCategoryList);
                }
                return Msg.success();
            }
        }
        map.put("msg","更新失败！");
        return Msg.fail().add("map",map);
    }

    //商品类别类别查询及分页
    @GetMapping("/getProductCategoryList/{pn}")
    public Msg getProductCategoryList(HttpServletRequest request,@PathVariable("pn")Integer pn){
        PersonInfo info = new PersonInfo();
        info.setUserId(1);
        Shop shop = new Shop();
        shop.setOwner(info);

        //分页
        PageMethod.startPage(pn,10);

        List<ProductCategory> productCategoryList = (List<ProductCategory>) request.getSession().getAttribute("productCategoryList");
        if(productCategoryList!=null&&productCategoryList.size()>0){
            PageInfo<ProductCategory> productCategoryPageInfo = new PageInfo<>(productCategoryList,5);
            map.put("productCategoryPageInfo",productCategoryPageInfo);
            return Msg.success().add("map",map);
        }

        productCategoryList = new ArrayList<>();
        if(info.getUserId()!=null && info.getUserId()>0){
            List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
            if (shopList!=null && shopList.size()>0){
                for (Shop shop1:shopList){
                    List<ProductCategory> productCategories = productCategoryService.getCategoryList(shop1.getShopId());
                    productCategoryList.addAll(productCategories);
                }
                PageInfo<ProductCategory> productCategoryPageInfo = new PageInfo<>(productCategoryList,5);
                map.put("productCategoryPageInfo",productCategoryPageInfo);
            }else {
                shopList = shopService.findShopListWithOwner(shop);
                if(shopList.size()>0){
                    request.getSession().setAttribute("shopList",shopList);
                    for (Shop shop1:shopList){
                        List<ProductCategory> productCategories = productCategoryService.getCategoryList(shop1.getShopId());
                        productCategoryList.addAll(productCategories);
                    }
                    if (productCategoryList.size()>0){
                        PageInfo<ProductCategory> productCategoryPageInfo = new PageInfo<>(productCategoryList,5);
                        map.put("productCategoryPageInfo",productCategoryPageInfo);
                        request.getSession().setAttribute("productCategoryList",productCategoryList);
                    }else {
                        map.put("msg","当前店铺没有商品类别");
                        return Msg.fail().add("map",map);
                    }
                }
            }
            return Msg.success().add("map",map);
        }
        map.put("msg","获取商品类别信息失败");
        return Msg.fail().add("map",map);
    }

    @PostMapping("/addProductCategory")
    public Msg addProductCategory(HttpServletRequest request){

        String productCategoryStr = HttpServletRequestUtils.getString(request,"productCategory");
        ProductCategory productCategory = null;
        try {
            productCategory = objectMapper.readValue(productCategoryStr,ProductCategory.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(productCategory!=null){
            int num = productCategoryService.addProductCategory(productCategory);
            if(num>0){
                map.put("msg","保存成功");
                //将类别添加到session中
                List<ProductCategory> productCategoryList = (List<ProductCategory>) request.getSession().getAttribute("productCategoryList");
                if(productCategoryList==null||productCategoryList.size()==0){
                    productCategoryList = new ArrayList<>();
                }
                productCategoryList.add(productCategory);
                request.getSession().setAttribute("productCategoryList",productCategoryList);
                return Msg.success().add("map",map);
            }
            map.put("msg","服务器内部错误");
            return Msg.fail().add("map",map);
        }else {
            map.put("msg","类别信息为空");
            return Msg.fail().add("map",map);
        }
    }

    @GetMapping("/getcategorylist/{shopId}")
    public Msg getProductInitInfo(@PathVariable("shopId") Integer shopId){
        List<ProductCategory> productCategories = productCategoryService.getCategoryList(shopId);
        if (productCategories.size()>0){
            map.put("categoryList",productCategories);
            return Msg.success().add("map",map);
        }else {
            map.put("msg","当前店铺没有创建商品类别");
            return Msg.fail().add("map",map);
        }
    }
}
