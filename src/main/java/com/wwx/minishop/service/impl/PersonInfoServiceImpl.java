package com.wwx.minishop.service.impl;

import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.repository.PersonInfoRepository;
import com.wwx.minishop.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    PersonInfoRepository personInfoRepository;

    @Cacheable(cacheNames = "personInfo",key = "'personInfo'+#userId",unless = "#result == null")
    @Override
    public PersonInfo findPersonById(Integer userId) {
        if (userId!=null && userId>0){
            try {
                PersonInfo personInfo = personInfoRepository.queryByUserId(userId);
                if(personInfo!=null){
                    return personInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Caching(
            put = {
                    @CachePut(cacheNames = "personInfo",key = "'personInfo'+#personInfo.userId")},
            evict = {
                    @CacheEvict(cacheNames = "shopList",key = "'own'+#personInfo.userId")
            }
    )
    @Override
    public int modifyPersonInfo(PersonInfo personInfo) {
        return 0;
    }

    @Override
    public int insertPersonInf(PersonInfo personInfo) {
        return 0;
    }
}
