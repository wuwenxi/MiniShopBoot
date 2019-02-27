package com.wwx.minishop.service.impl;

import com.wwx.minishop.entity.LocalAuth;
import com.wwx.minishop.repository.LocalAuthRepository;
import com.wwx.minishop.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    LocalAuthRepository localAuthRepository;

    @Cacheable(cacheNames = "localAuth",key = "'localAuth:'+#username")
    @Override
    public LocalAuth findLocalAuthWithName(String username) {
        return localAuthRepository.queryByUserName(username);
    }
}
