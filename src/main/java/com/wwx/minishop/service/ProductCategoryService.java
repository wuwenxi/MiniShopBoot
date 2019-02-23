package com.wwx.minishop.service;

import com.wwx.minishop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getCategoryList(Integer shopId);

    int addProductCategory(ProductCategory productCategory);

    int modifyProductCategory(ProductCategory productCategory);

    int deleteProductCategory(Integer productCategoryId);
}
