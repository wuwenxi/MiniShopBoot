package com.wwx.minishop.repository;

import com.wwx.minishop.entity.ProductImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 *              JpaRepository<T, ID>
 *              T: 实体类
 *              ID:实体类中的主键
 * */
public interface ProductImgRepository extends JpaRepository<ProductImg,Integer> {

    List<ProductImg> queryProductImgsByProductId(Integer productId);

    void deleteByProductId(Integer productId);
}
