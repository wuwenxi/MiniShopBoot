package com.wwx.minishop.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.*;
import com.wwx.minishop.exception.ProductException;
import com.wwx.minishop.service.ProductCategoryService;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.PersonInfoUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wwx.minishop.utils.InsertImageUtils.resolveImage;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ShopService shopService;

    private Map<String,Object> map = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getProduct/{productId}")
    public Msg getProductWithId(@PathVariable("productId")Integer productId){
        if (productId>0){
            Product product = productService.findProduct(productId);
            if(product!=null){
                map.put("product",product);
                return Msg.success().add("map",map);
            }
        }
        map.put("msg","服务器内部错误");
        return Msg.fail().add("map",map);
    }

    @PostMapping("/modifyProduct")
    public Msg modifyProduct(HttpServletRequest request){
        String productStr = HttpServletRequestUtils.getString(request,"product");
        Product product = null;
        try {
            product = objectMapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (product==null || (product.getShop().getShopId()==null && product.getShop().getShopId()<0)){
            map.put("msg","商品信息为空，请填写商品信息");
            return Msg.fail().add("map",map);
        }

        //解析缩略图
        ImageHolder image = null;
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (resolver.isMultipart(request)) {
                //2. 获取详细图
                image = resolveImage(request,image,productImgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int num = 0;
        try {
            //更新成功后 清空session中的商品列表
            num = productService.modifyProduct(product,image,productImgList);
        } catch (ProductException e) {
            e.printStackTrace();
        }
        if (num>0){
            return Msg.success();
        }else {
            map.put("msg", "添加商品失败");
            return Msg.fail().add("map", map);
        }
    }

    //商品类别及分页
    @GetMapping("/getProductList/{pn}")
    public Msg getProductList(HttpServletRequest request,@Param("shopId")Integer shopId,@PathVariable("pn")Integer pn){
        //1.查出所有当前用户下的所有店铺
        //2.通过店铺查询每个店铺下的商品
        PersonInfo info = PersonInfoUtils.getPersonInfo(request);
        Shop shop = new Shop();
        shop.setOwner(info);

        PageMethod.startPage(pn,10);


        //若没有商品列表
        List<Product> productList = new ArrayList<>();
        List<Product> products;
        if(shopId == null){
            if(info.getUserId()!=null && info.getUserId()>0){
                try {
                    List<Shop> shopList = shopService.findShopListWithOwner(shop);
                    if(shopList!=null&&shopList.size()>0){
                        //request.getSession().setAttribute("shopList",shopList);
                        for (Shop shop1:shopList){
                            products = productService.findProductListWithShopId(shop1.getShopId());
                            productList.addAll(products);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("msg","获取商品信息失败");
                    return Msg.fail().add("map",map);
                }
            }
        }else {
            products = productService.findProductListWithShopId(shopId);
            productList.addAll(products);
        }

        if (productList.size()>0){
            PageInfo<Product> productPageInfo = new PageInfo<>(productList,5);
            map.put("productPageInfo",productPageInfo);
        }else {
            map.put("msg","店铺未创建任何商品");
            return Msg.fail().add("map",map);
        }
        return Msg.success().add("map",map);
    }

    @PostMapping("/addProduct")
    public Msg addProduct(HttpServletRequest request){
        String productStr = HttpServletRequestUtils.getString(request,"productStr");

        Product product=null;
        try {
            product = objectMapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (product==null || (product.getShop().getShopId()==null && product.getShop().getShopId()<0)){
            map.put("msg","商品信息为空，请填写商品信息");
            return Msg.fail().add("map",map);
        }

        //解析缩略图
        ImageHolder image = null;
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (resolver.isMultipart(request)) {
                //2. 获取详细图
                image = resolveImage(request,image,productImgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int num = productService.addProduct(product,image,productImgList);
        if (num>0){
            return Msg.success();
        }else {
            map.put("msg", "添加商品失败");
            return Msg.fail().add("map", map);
        }
    }


}
