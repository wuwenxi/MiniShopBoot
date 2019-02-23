package com.wwx.minishop.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.*;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.exception.ProductException;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.service.ProductCategoryService;
import com.wwx.minishop.service.ProductImgService;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ValidateUtil;
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

@RestController
@RequestMapping("/product")
public class ProductManagerController {

    private static final int IMAGE_MAX_COUNT = 6;

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ShopService shopService;

    private Map<String,Object> map = new HashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getproduct/{id}")
    public Msg getProduct(@PathVariable("id")Integer id){
        map.put("product",productService.findProduct(id));
        return Msg.success().add("map",map);
    }

    @DeleteMapping("/deleteProductCategory/{productCategoryId}")
    public Msg deleteProductCategory(@PathVariable("productCategoryId")Integer productCategoryId){
        if(productCategoryId>0){
           int num =  productCategoryService.deleteProductCategory(productCategoryId);
           if(num>0){
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

        if(productCategory!=null&&productCategory.getProductCategoryId()>0){
            int num = productCategoryService.modifyProductCategory(productCategory);
            if (num>0){
                return Msg.success();
            }
        }
        map.put("msg","更新失败！");
        return Msg.fail().add("map",map);
    }

    @GetMapping("/getProduct/{productId}")
    public Msg getProductWithProductId(@PathVariable("productId")Integer productId){
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

    @PutMapping("/modifyProduct")
    public Msg modifyProduct(HttpServletRequest request){
        String productStr = HttpServletRequestUtils.getString(request,"product");
        Product product=null;
        try {
            product = objectMapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //解析缩略图
        ImageHolder image = null;
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

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

    //商品类别类别查询及分页
    @GetMapping("/getProductCategoryList/{pn}")
    public Msg getProductCategoryList(HttpServletRequest request,@PathVariable("pn")Integer pn){
        PersonInfo info = new PersonInfo();
        info.setUserId(1);
        Shop shop = new Shop();
        shop.setOwner(info);

        //分页
        PageMethod.startPage(pn,10);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        if(info.getUserId()!=null && info.getUserId()>0){
            List<Shop> shopList = shopService.findShopListWithOwner(shop);
            if(shopList.size()>0){
                for (Shop shop1:shopList){
                    List<ProductCategory> productCategories = productCategoryService.getCategoryList(shop1.getShopId());
                    productCategoryList.addAll(productCategories);
                }
                if (productCategoryList.size()>0){
                    PageInfo<ProductCategory> productCategoryPageInfo = new PageInfo<>(productCategoryList,5);
                    map.put("productCategoryPageInfo",productCategoryPageInfo);
                }
                return Msg.success().add("map",map);
            }
        }
        map.put("msg","获取商品类别信息失败");
        return Msg.fail().add("map",map);
    }

    //商品类别及分页
    @GetMapping("/getProductList/{pn}")
    public Msg getProductList(HttpServletRequest request,@Param("shopId")Integer shopId,@PathVariable("pn")Integer pn){
        //1.查出所有当前用户下的所有店铺
        //2.通过店铺查询每个店铺下的商品
        //PersonInfo info = (PersonInfo) request.getSession().getAttribute("user");
        PersonInfo info = new PersonInfo();
        info.setUserId(1);
        Shop shop = new Shop();
        shop.setOwner(info);

        PageMethod.startPage(pn,10);
        List<Product> productList = new ArrayList<>();
        List<Product> products;

        if(shopId == null){
            //List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
            if(info.getUserId()!=null && info.getUserId()>0){
                List<Shop> shopList = shopService.findShopListWithOwner(shop);
                for (Shop shop1:shopList){
                    products = productService.findProductListWithShopId(shop1.getShopId());
                    productList.addAll(products);
                }
                map.put("msg","获取商品信息失败");
                return Msg.fail().add("map",map);
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
        if (product==null){
            map.put("msg","商品信息为空，请填写商品信息");
            return Msg.fail().add("map",map);
        }

        //解析缩略图
        ImageHolder image = null;
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

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
                map.put("msg","上传成功");
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

    private ImageHolder resolveImage(HttpServletRequest request, ImageHolder image, List<ImageHolder> productImgList) {
        try {
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            //1. 获取缩略图
            MultipartFile productImg = servletRequest.getFile("productImg");
            if (productImg != null) {
                image = new ImageHolder(productImg.getOriginalFilename(),productImg.getInputStream());
            }
            //获取详细图
            for (int i=1;i<IMAGE_MAX_COUNT;i++){
                //前端页面按顺序返回图片名0-5
                MultipartFile productImages = servletRequest.getFile("productImgs"+ i);
                if(productImages != null){
                    ImageHolder imageHolder = new ImageHolder(productImages.getOriginalFilename(),productImages.getInputStream());
                    productImgList.add(imageHolder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
