package com.wwx.minishop.repository;

import com.wwx.minishop.entity.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *              JpaRepository<T, ID>
 *              T: 实体类
 *              ID:实体类中的主键
 * */
public interface PersonInfoRepository extends JpaRepository<PersonInfo,Integer> {
}
