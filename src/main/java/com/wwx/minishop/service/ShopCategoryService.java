package com.wwx.minishop.service;

import com.wwx.minishop.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    List<ShopCategory> findShopCategoryWithParentId(ShopCategory shopCategory);

    ShopCategory findShopCategoryById(Integer shopCategoryId);

    int addShopCategory(ShopCategory shopCategory);

    int updateShopCategory(ShopCategory shopCategory);

    int deleteShopCategoryById(Integer shopCategoryId);

}
