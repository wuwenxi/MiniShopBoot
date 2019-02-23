package com.wwx.minishop.service.impl;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.service.PersonInfoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@CacheConfig(cacheManager = "personInfoCacheManager")
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
}
