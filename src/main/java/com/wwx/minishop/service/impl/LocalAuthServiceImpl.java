package com.wwx.minishop.service.impl;

import com.wwx.minishop.dao.LocalAuthMapper;
import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    LocalAuthMapper localAuthMapper;

    //若返回值为空  不缓存
    @Cacheable(cacheNames = "localAuth",key = "'localAuth:'+#username",unless = "#result==null")
    @Override
    public LocalAuth findLocalAuthWithName(String username) {
        return localAuthMapper.findByUserName(username);
    }
}
