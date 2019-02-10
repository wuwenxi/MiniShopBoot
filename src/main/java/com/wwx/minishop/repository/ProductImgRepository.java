package com.wwx.minishop.repository;

import com.wwx.minishop.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *              JpaRepository<T, ID>
 *              T: 实体类
 *              ID:实体类中的主键
 * */
public interface ProductImgRepository extends JpaRepository<ProductImg,Integer> {
}
