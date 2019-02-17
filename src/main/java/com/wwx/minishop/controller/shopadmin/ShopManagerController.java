package com.wwx.minishop.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.service.ShopCategoryService;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ValidateUtil;
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


@RestController()
@RequestMapping("/shop")
public class ShopManagerController {

    @Autowired
    ShopService shopService;

    @Autowired
    ShopCategoryService shopCategoryService;

    private ShopExecution execution = new ShopExecution();

    private Map<String,Object> map = new HashMap<>();

    @PutMapping("/modifyshop")
    public Msg ModifyShop(HttpServletRequest request){
        String shopStr = HttpServletRequestUtils.getString(request,"shop");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop;
        try {
            shop = objectMapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }
        ShopExecution execution = shopService.modifyShop(shop, null);
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
        ObjectMapper objectMapper = new ObjectMapper();
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
            //从session中获取用户信息  注册店铺需要登录  即可得到用户的信息
            //PersonInfo info = (PersonInfo) request.getSession().getAttribute("user");
            PersonInfo info = new PersonInfo();
            info.setUserId(1);
            shop.setOwner(info);
            request.setAttribute("user",info);

            execution = shopService.addShop(shop,new ImageHolder(shopImage.getOriginalFilename(),shopImage.getInputStream()));

            //如果状态为审核，则返回正确，否则错误
            if (execution.getState().equals(ShopStateEnum.CHECK.getState())) {
                /**
                 *          用户和店铺是一对多的关系
                 *          注册店铺或将店铺信息保存到session中
                 */
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if(shopList == null || shopList.size()==0){
                    shopList = new ArrayList<>();
                }
                //把刚刚注册的店铺添加到列表中
                shopList.add(execution.getShop());
                request.getSession().setAttribute("shopList",shopList);
                return Msg.success().add("execution",execution);
            } else {
                return Msg.fail().add("msg", execution.getStateInfo());
            }
        } catch (Exception e) {
            return Msg.fail().add("msg:","服务器内部错误");
        }
    }

    @GetMapping("/getshopinitinfo/{parentId}")
    public Msg getShopInitInfo(@PathVariable("parentId") Integer parentId){
        if(parentId==null||parentId<0){
            return Msg.fail().add("msg","查询类别错误");
        }

        ShopCategory parentCategory = new ShopCategory();
        ShopCategory childCategory = new ShopCategory();
        parentCategory.setShopCategoryId(parentId);
        childCategory.setParent(parentCategory);

        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.getAllShopCategory(childCategory);
            map.put("shopCategoryList",shopCategoryList);
            return Msg.success().add("map",map);
        } catch (Exception e) {
            return Msg.fail().add("msg","服务器错误");
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
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1);
        request.getSession().setAttribute("user",personInfo);
        personInfo = (PersonInfo) request.getAttribute("user");
        try {
            Shop shop = new Shop();
            shop.setOwner(personInfo);
            ShopExecution execution = shopService.getAllShop(shop);

            if(execution.getState().equals(ShopStateEnum.SUCCESS.getState()) ){
                map.put("shops",execution.getShops());
                map.put("count",execution.getCount());
            }else {
                map.put("msg","当前账户没有开设店铺");
            }
            return Msg.success().add("map",map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","服务器内部错误");
            return Msg.fail().add("map",map);
        }
    }


}
