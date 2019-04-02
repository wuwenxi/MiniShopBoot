package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryMapper shopCategoryMapper;

    @Cacheable(cacheNames = "shopCategoryList",key = "'parentId'+#shopCategory.parent.shopCategoryId",unless = "#result == null ")
    @Override
    public List<ShopCategory> findShopCategoryWithParentId(ShopCategory shopCategory) {
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
    }

    @Cacheable(cacheNames = "allShopCategory",key = "'shopCategory'",unless = "#result == 0")
    @Override
    public List<ShopCategory> findAllShopCategory(ShopCategory shopCategory){
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
    }

    @Cacheable(cacheNames = "shopCategory",key = "'shopCategory'+#shopCategoryId",unless = "#result == null ")
    @Override
    public ShopCategory findShopCategoryById(Integer shopCategoryId) {
        return shopCategoryMapper.queryShopCategory(shopCategoryId);
    }

}
