package com.wwx.minishop.dao;

import com.wwx.minishop.entity.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {

    List<Shop> selectShopList(@Param("shop") Shop shop,@Param("rowIndex") Integer rowIndex,@Param("pageSize") Integer pageSize);

    int updateShop(Shop shop);

    int insertShop(Shop shop);

    Shop queryShopById(Integer shopId);
}
