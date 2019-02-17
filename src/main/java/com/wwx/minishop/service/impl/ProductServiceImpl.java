package com.wwx.minishop.service.impl;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishop.exception.ProductImgException;
import com.wwx.minishop.repository.ProductImgRepository;
import com.wwx.minishop.repository.ProductRepository;
import com.wwx.minishop.service.ProductService;
import com.wwx.minishop.utils.ImageUtils;
import com.wwx.minishop.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImgRepository productImgRepository;

    @Override
    public List<Product> getProductList(Integer shopId) {
        return productRepository.findAllById(Collections.singleton(shopId));
    }

    @Override
    public int addProduct(Product product, ImageHolder image, List<ImageHolder> productImgList) {
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){

            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());

            //状态 0：下架 1：上架
            product.setEnableStatus(0);
            //保存图片  设置图片路径
            if(image!=null){
                insertProductImg(product,image);
            }else {
                return -1;
            }
            //保存商品
            try {
                productRepository.save(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //保存图片列表
            if(productImgList!=null&&productImgList.size()>0) {
                try {
                    insertProductImgList(product, productImgList);
                } catch (ProductImgException e) {
                    e.printStackTrace();
                }
            }else {
                return -1;
            }
            return 1;
        }else {
            return 0;
        }
    }

    //获取商品缩略图存储地址
    private void insertProductImg(Product product,ImageHolder imageHolder){
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        String realPath = ImageUtils.generateThumbnail(imageHolder,path);
        product.setImgAddress(realPath);
    }

    //处理商品详情图
    private void insertProductImgList(Product product,List<ImageHolder> imageHolderList) throws ProductImgException {
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> list = new ArrayList<>();
        for(ImageHolder imageHolder:imageHolderList){
            String imgAddress = ImageUtils.generateNormalImage(imageHolder,path);
            ProductImg productImg = new ProductImg(null,imgAddress,
                    null,null,new Date(),product.getProductId());
            list.add(productImg);
        }

        if(list.size()>0){
            try {
                productImgRepository.saveAll(list);
            } catch (Exception e) {
                throw new ProductImgException("添加商品详情图发生错误");
            }
        }
    }
}
