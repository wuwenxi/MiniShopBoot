package com.wwx.minishop.service.impl;

import com.wwx.minishop.entity.ProductCategory;
import com.wwx.minishop.repository.ProductCategoryRepository;
import com.wwx.minishop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> getCategoryList(Integer shopId) {
        return  productCategoryRepository.queryProductCategoriesByShopId(shopId);
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
}
