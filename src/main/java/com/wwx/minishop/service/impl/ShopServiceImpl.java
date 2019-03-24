package com.wwx.minishop.service.impl;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ShopMapper;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.entity.ShopCategory;
import com.wwx.minishop.exception.ShopException;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static com.wwx.minishop.utils.InsertImageUtils.addShopImg;

@CacheConfig(cacheManager = "cacheManager")
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;

    @Cacheable(cacheNames = "shopList",key = "'own'+#shop.owner.userId",unless = "#result == 0")
    @Override
    public List<Shop> findShopListWithOwner(Shop shop) {
        //从第一行开始查询  店铺总数不允许超过10个
        List<Shop> shopList = shopMapper.selectShopList(shop,0,10);

        if(shopList.size()>0){
            return shopList;
        }
        return null;
    }

    @Caching(
            evict = {
                    //数据更新 清空shopList的缓存
                    @CacheEvict(cacheNames = "shop",key = "'shop'+#shop.shopId" ),
                    @CacheEvict(cacheNames = "shopList",key = "'own'+#shop.owner.userId"),
                    @CacheEvict(cacheNames = "shopListWithCategoryId",allEntries = true)
            }
    )
    @Override
    public int modifyShop(Shop shop, ImageHolder image) throws ShopException{
        //获取文件输入流  以及文件名
        InputStream in = null;
        String fileName = null;

        if(image!=null){
            in = image.getInputStream();
            fileName = image.getFileName();
        }

        if(shop == null || shop.getShopId() == null){
            return -1;
        }else {
            //  1.  判断是否需要更新图片
            try {
                if(in != null && fileName!= null
                        && !"".equals(fileName)){
                    Shop shopImg = shopMapper.queryShopById(shop.getShopId());
                    //Optional<Shop> shopImg = shopRepository.findById(shop.getShopId());
                    if(shopImg.getShopImg()!=null){
                        ImageUtils.deleteFileOrPath(shopImg.getShopImg());
                    }
                    //更新图片
                    addShopImg(shop,image);
                }
                // 2.  更新商铺信息
                shop.setLastEditTime(new Date());
                try {
                    shopMapper.updateShop(shop);
                } catch (Exception e) {
                    return -1;
                }
                shop = shopMapper.queryShopById(shop.getShopId());
                return 1;
            } catch (Exception e) {
                return -1;
            }
        }
    }

    @Cacheable(cacheNames = "shop",key = "'shop'+#shopId",unless = "#result==null")
    @Override
    public Shop getShopById(Integer shopId) {
        System.out.println("查询"+shopId+"号店铺");
        Shop shop = shopMapper.queryShopById(shopId);
        if(shop!=null){
            return shop;
        }
        return null;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "shopList",key = "'own'+#shop.owner.userId"),
                    @CacheEvict(cacheNames = "shopListWithCategoryId",key = "'shopCategoryId'+#shop.shopCategory.shopCategoryId")
            }
    )
    @Override
    public int addShop(Shop shop, ImageHolder image) {
        if(shop == null){
            //店铺信息为空
            return -1;
        }
        if(shop.getShopAddress() == null ){
            //地区为空
            return -1;
        }
        if (shop.getShopCategory()==null){
            //店铺类别为空
            return -1;
        }

        /**
         *   实现店铺添加逻辑
         *   1. 将店铺添加进入数据库
         *   2.
         */
        try {
            //将初始状态设为审核  给店铺写入创建时间和更新时间
            //状态标识 -1:审核 0：休息 1：营业
            shop.setEnableStatus(-1);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //插入数据库

            int number = shopMapper.insertShop(shop);
            if(number <= 0){
                throw new SecurityException("店铺添加失败");
            }else {
                //添加图片
                if(image.getInputStream()!= null){
                    try{
                        //添加图片地址
                        addShopImg(shop,image);
                    }catch (Exception e){
                        throw new ShopException("updateInputStream ERROR:" + e.getMessage());
                    }

                    //更新店铺的图片地址
                    number = shopMapper.updateShop(shop);
                    if(number<=0){
                        throw new ShopException("更新图片地址失败");
                    }else {
                        //店铺创建成功
                        return 1;
                    }
                }else {
                    throw new ShopException("店铺图片未知");
                }
            }
        } catch (Exception e) {
            throw new ShopException("addShop ERROR : "+e.getMessage());
        }

    }

    @Cacheable(cacheNames = "shopListWithCategoryId",key = "'shopCategoryId'+#shopCategory.shopCategoryId",unless = "#result == null ")
    @Override
    public List<Shop> findShopListWithShopCategory(ShopCategory shopCategory) {
        List<Shop> shops = shopMapper.queryShopsByShopCategoryId(shopCategory);
        if(shops!=null&&shops.size()>0){
            return shops;
        }
        return null;
    }
}
