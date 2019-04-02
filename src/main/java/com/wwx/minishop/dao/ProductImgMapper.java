package com.wwx.minishop.dao;

import com.wwx.minishop.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductImgMapper {

    List<ProductImg> queryProductImgsByProductId(Integer id);

    void insertProductImgs(@Param("list") List<ProductImg> products);

    void deleteByProductId(Integer productId);
}
