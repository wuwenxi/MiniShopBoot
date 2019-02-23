package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.ProductCategoryMapper;
import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.repository.ProductCategoryRepository;
import com.wwx.minishop.service.ProductCategoryService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;


@CacheConfig(cacheManager = "productCategoryCacheManager")
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Cacheable(cacheNames = "productCategoryList",key = "'productCategory'+#shopId")
    @Override
    public List<ProductCategory> getCategoryList(Integer shopId) {
        return  productCategoryMapper.queryProductCategoriesByShopId(shopId);
    }

    @Override
    public int addProductCategory(ProductCategory productCategory) {

        productCategory.setCreateTime(new Date());
        Random random = new Random();
        int priority = random.nextInt(100);
        productCategory.setPriority(priority);
        try {
            productCategoryRepository.save(productCategory);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = "productCategory",
                            key = "'productCategory'+#productCategory.productCategoryId")},
            evict = {
                    //allEntries 清空productCategoryList中的所有缓存
                    @CacheEvict(cacheNames = "productCategoryList",allEntries = true)}
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
        int num = productCategoryRepository.deleteByProductCategoryId(productCategoryId);
        if(num>0){
            return 1;
        }
        return -1;
    }
}
