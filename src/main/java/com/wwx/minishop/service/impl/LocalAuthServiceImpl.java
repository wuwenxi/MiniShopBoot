package com.wwx.minishop.service.impl;

import com.wwx.minishop.service.LocalAuthService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@CacheConfig(cacheManager = "localAurhCacheMananer")
@Service
public class LocalAuthServiceImpl implements LocalAuthService {
}
