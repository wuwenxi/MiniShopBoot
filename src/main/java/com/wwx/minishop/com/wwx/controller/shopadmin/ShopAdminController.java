package com.wwx.minishop.com.wwx.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopAdmin")
public class ShopAdminController {

    @GetMapping()
    public String ShopAdminIndex(){
        return "ShopAdmin/ShopAdmin";
    }

    @GetMapping("/shoplist")
    public String ShopList(){
        return "ShopAdmin/ShopList";
    }

    @GetMapping("/productlist")
    public String ProductList(){
        return "ShopAdmin/ProductList";
    }

    @GetMapping("/productcategory")
    public String productCategory(){
        return "ShopAdmin/ProductCategoryList";
    }

    @GetMapping("/addshop")
    public String addShop(){
        return "ShopAdmin/AddShop";
    }

    @GetMapping("/addproduct")
    public String addProduct(){
        return "ShopAdmin/AddProduct";
    }

    @GetMapping("/addproductcategoty")
    public String addProductCategory(){
        return "ShopAdmin/AddProductCategory";
    }

    @GetMapping("/userinfo")
    public String UserInfo(){
        return "ShopAdmin/UserInfo";
    }

    @GetMapping("/feedback")
    public String Feedback(){
        return "ShopAdmin/Feedback";
    }

}
