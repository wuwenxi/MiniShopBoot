package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.ProductCategoryMapper;
import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;


@CacheConfig(cacheManager = "cacheManager")
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {


    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Cacheable(cacheNames = "productCategoryList",key = "'productCategory'+#shopId",unless = "#result == null ")
    @Override
    public List<ProductCategory> getCategoryList(Integer shopId) {
        return  productCategoryMapper.queryProductCategoriesByShopId(shopId);
    }

    @CacheEvict(cacheNames = "productCategoryList",key ="'productCategory'+#productCategory.shop.shopId" )
    @Override
    public int addProductCategory(ProductCategory productCategory) {

        productCategory.setCreateTime(new Date());
        Random random = new Random();
        int priority = random.nextInt(100);
        productCategory.setPriority(priority);
        try {
            productCategoryMapper.insertProductCategory(productCategory);
            //productCategoryRepository.save(productCategory);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Caching(
            evict = {
                    //allEntries 清空productCategoryList中shopId下的缓存
                    @CacheEvict(cacheNames = "productCategory",
                            key = "'productCategory'+#productCategory.productCategoryId"),
                    @CacheEvict(cacheNames = "productCategoryList",key = "'productCategory'+#productCategory.shop.shopId"),
                    @CacheEvict(cacheNames = "productList",allEntries = true),
                    @CacheEvict(cacheNames = "product",allEntries = true)
            }
    )
    @Override
    public int modifyProductCategory(ProductCategory productCategory) {
        int num = productCategoryMapper.updateProductCategory(productCategory);
        if(num>0){
            return 1;
        }
        return -1;
    }

    @CacheEvict(cacheNames = "productCategory",key = "'productCategory'+#productCategoryId")
    @Override
    public int deleteProductCategory(Integer productCategoryId) {
        int num = productCategoryMapper.deleteByProductCategoryId(productCategoryId);
        if(num>0){
            return 1;
        }
        return -1;
    }
}
