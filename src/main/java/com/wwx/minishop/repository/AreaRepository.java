package com.wwx.minishop.repository;

import com.wwx.minishop.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *              JpaRepository<T, ID>
 *              T: 实体类
 *              ID:实体类中的主键
 * */
public interface AreaRepository extends JpaRepository<Area,Integer> {

}
