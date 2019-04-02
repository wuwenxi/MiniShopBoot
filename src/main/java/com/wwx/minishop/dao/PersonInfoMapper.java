package com.wwx.minishop.dao;

import com.wwx.minishop.entity.PersonInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonInfoMapper {

    PersonInfo queryPersonInfoWithName(String name);

    int insertPersonInfo(PersonInfo personInfo);

    int updatePersonInfo(PersonInfo personInfo);
}
