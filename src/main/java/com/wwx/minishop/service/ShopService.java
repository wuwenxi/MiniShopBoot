package com.wwx.minishop.service;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.execution.ShopExecution;

import java.util.List;


public interface ShopService {

    List<Shop> findShopListWithOwner(Shop shop);

    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder);

    Shop getShopById(Integer shopId);

    ShopExecution addShop(Shop shop, ImageHolder imageHolder);

    List<Shop> findShopListWithShopCategory(Integer shopCategoryId);
}
