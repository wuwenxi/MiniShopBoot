package com.wwx.minishop.service;

import com.wwx.minishop.entity.PersonInfo;

public interface PersonInfoService {

    PersonInfo findPersonById(Integer userId);

    int modifyPersonInfo(PersonInfo personInfo);

    int insertPersonInf(PersonInfo personInfo);
}
