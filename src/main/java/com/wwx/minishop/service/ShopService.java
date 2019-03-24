package com.wwx.minishop.service;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;

import java.util.List;


public interface ShopService {

    List<Shop> findShopListWithOwner(Shop shop);

    int modifyShop(Shop shop, ImageHolder imageHolder);

    Shop getShopById(Integer shopId);

    int addShop(Shop shop, ImageHolder imageHolder);

    List<Shop> findShopListWithShopCategory(ShopCategory shopCategory);
}
