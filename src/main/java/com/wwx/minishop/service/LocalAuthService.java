package com.wwx.minishop.service;

import com.wwx.minishop.entity.LocalAuth;

public interface LocalAuthService {

    LocalAuth findLocalAuthWithName(String username);

    //void findLocalAuthWithId(Integer localAuthId);
}
