package com.wwx.minishop.dao;

import com.wwx.minishop.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {

    List<ProductCategory> queryProductCategoriesByShopId(Integer shopId);

    int updateProductCategory(ProductCategory productCategory);
}
