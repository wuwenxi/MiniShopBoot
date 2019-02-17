package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.ShopCategoryMapper;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.repository.ShopCategoryRepository;
import com.wwx.minishop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryMapper shopCategoryMapper;

    @Autowired
    ShopCategoryRepository shopCategoryRepository;

    @Override
    public List<ShopCategory> getAllShopCategory(ShopCategory shopCategory) {
        return shopCategoryMapper.queryForListShopCategory(shopCategory);
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

    @Override
    public int deleteShopCategoryById(Integer shopCategoryId) {
        return 0;
    }
}
