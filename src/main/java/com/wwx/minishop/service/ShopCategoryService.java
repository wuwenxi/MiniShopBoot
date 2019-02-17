package com.wwx.minishop.service;

import com.wwx.minishop.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> getAllShopCategory(ShopCategory shopCategory);

    int addShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);

    int deleteShopCategoryById(Integer shopCategoryId);

}
