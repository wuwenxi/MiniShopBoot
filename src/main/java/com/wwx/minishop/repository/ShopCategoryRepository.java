package com.wwx.minishop.repository;

import com.wwx.minishop.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *              JpaRepository<T, ID>
 *              T: 实体类
 *              ID:实体类中的主键
 * */
public interface ShopCategoryRepository extends JpaRepository<ShopCategory,Integer> {

    List<ShopCategory> queryAllByParentIsNotNull();
}
