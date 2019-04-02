package com.wwx.minishop.service;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.PersonInfo;

public interface PersonInfoService {

    PersonInfo findPersonInfoWithName(String username);

    boolean addPersonInfo(PersonInfo personInfo, ImageHolder imageHolder);
}
