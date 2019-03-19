package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.repository.ShopCategoryRepository;
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

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Cacheable(cacheNames = "shopCategoryList",key = "'parentId'+#shopCategory.parent.shopCategoryId")
    @Override
    public List<ShopCategory> findShopCategoryWithParentId(ShopCategory shopCategory) {
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
    }

    @Cacheable(cacheNames = "shopCategory",key = "'shopCategory'+#shopCategoryId")
    @Override
    public ShopCategory findShopCategoryById(Integer shopCategoryId) {
        return shopCategoryMapper.queryShopCategory(shopCategoryId);
    }

    @Override
    public int addShopCategory(ShopCategory shopCategory) {
        if (shopCategory!=null){
            try {
                shopCategoryRepository.save(shopCategory);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
            return 1;
        }
        return 0;
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = "shopCategory",key = "'shopCategory'+#shopCategory.shopCategoryId")},
            evict = {
                    //是店铺类别更新，  清空店铺列表缓存及店铺类别类别缓存
                    @CacheEvict(cacheNames = "shopCategoryList",key = "'parentId'+#shopCategory.parent.shopCategoryId"),
                    @CacheEvict(cacheNames = "shopList",allEntries = true)
            }
    )
    @Override
    public int updateShopCategory(ShopCategory shopCategory) {
        if (shopCategory!=null&&shopCategory.getShopCategoryId()!=null){
            try {
                shopCategoryMapper.updateShopCategory(shopCategory);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    @CacheEvict(cacheNames = "shopCategory",key = "'shopCategory'+#shopCategoryId")
    @Override
    public int deleteShopCategoryById(Integer shopCategoryId) {
        return 0;
    }
}
