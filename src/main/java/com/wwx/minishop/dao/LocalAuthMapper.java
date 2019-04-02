package com.wwx.minishop.dao;

import com.wwx.minishop.entity.LocalAuth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalAuthMapper {

    LocalAuth findByUserName(String name);

}
