package com.wwx.minishop.controller.frontend;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend")
public class FrontendProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getProductList/{id}")
    public Msg getProductList(@PathVariable("id")Integer id,@Param("pn")Integer pn, Map<String,Object> map){
        PageMethod.startPage(pn,10);

        List<Product> list = productService.findProductListWithShopId(id);
        if(list!=null&&list.size()>0){
            PageInfo<Product> pageInfo = new PageInfo<>(list,5);
            map.put("pageInfo",pageInfo);
            return Msg.success().add("map",map);
        }
        return Msg.fail().add("msg","当前店铺未创建任何商品");
    }

}
