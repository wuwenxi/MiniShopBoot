package com.wwx.minishop.dao;

import com.wwx.minishop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> queryAllByShopId(Integer shopId);

    int modifyProduct(Product product);

    Product queryProductById(Integer productId);

    void insertProduct(Product product);
}
