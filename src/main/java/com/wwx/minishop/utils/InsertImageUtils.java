package com.wwx.minishop.utils;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.exception.ProductImgException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertImageUtils {

    //获取商品缩略图存储地址
    public static void insertProductImg(Product product, ImageHolder imageHolder){
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        String realPath = ImageUtils.generateThumbnail(imageHolder,path);
        product.setImgAddress(realPath);
    }

    //处理商品详情图
    public static List<ProductImg> insertProductImgList(Product product,List<ImageHolder> imageHolderList) throws ProductImgException {
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> list = new ArrayList<>();
        for(ImageHolder imageHolder:imageHolderList){
            String imgAddress = ImageUtils.generateNormalImage(imageHolder,path);
            ProductImg productImg = new ProductImg(null,imgAddress,
                    null,null,new Date(),product.getProductId());
            list.add(productImg);
        }
        return list;
    }

    //处理店铺图片
    public static void addShopImg(Shop shop, ImageHolder image){
        //获取相对路径  "upload/item/shop/"+ shopId + "/"
        String path = PathUtils.getShopImagePath(shop.getShopId());
        //存储图片的绝对路径
        String realPath = ImageUtils.generateThumbnail(image,path);
        //将图片地址存入数据库
        shop.setShopImg(realPath);
    }
}
