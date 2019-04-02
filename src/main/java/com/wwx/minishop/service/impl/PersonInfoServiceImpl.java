package com.wwx.minishop.service.impl;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.PersonInfoMapper;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.wwx.minishop.utils.InsertImageUtils.addPersonInfoImg;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    PersonInfoMapper personInfoDao;

    @Cacheable(value = "personInfo",key = "#username",unless = "#result == null ")
    @Override
    public PersonInfo findPersonInfoWithName(String username) {
        return personInfoDao.queryPersonInfoWithName(username);
    }

    @Override
    public boolean addPersonInfo(PersonInfo personInfo, ImageHolder imageHolder) {
        if(personInfo!=null){
            personInfo.setCreateTime(new Date());
            personInfo.setEnableStatus(0);
            personInfo.setLastEditTime(new Date());
            //设置为普通用户类别
            personInfo.setUserType(0);
            if(imageHolder!=null){
                //添加图片地址
                if(imageHolder.getFileName()!=null
                        &&imageHolder.getInputStream()!=null
                        &&!"".equals(imageHolder.getFileName())){

                    addPersonInfoImg(personInfo,imageHolder);
                }
            }
            int num = personInfoDao.insertPersonInfo(personInfo);
            return num > 0;
        }
        return false;
    }
}
