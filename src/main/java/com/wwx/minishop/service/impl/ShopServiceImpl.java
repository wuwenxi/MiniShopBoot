package com.wwx.minishop.service.impl;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.dao.ShopMapper;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.exception.ShopException;
import com.wwx.minishop.execution.ShopExecution;
import com.wwx.minishop.repository.ShopRepository;
import com.wwx.minishop.service.ShopService;
import com.wwx.minishop.utils.ImageUtils;
import com.wwx.minishop.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopRepository shopRepository;

    @Override
    public ShopExecution getAllShop(Shop shop) {
        ShopExecution execution = new ShopExecution();
        //从第一行开始查询  店铺总数不允许超过10个
        List<Shop> shopList = shopMapper.selectShopList(shop,0,10);
        if(shopList.size()>0){
            execution.setShops(shopList);
            execution.setCount(shopList.size());
            execution.setState(ShopStateEnum.SUCCESS.getState());
        }else {
            execution.setState(ShopStateEnum.NULL_SHOP.getState());
        }
        return execution;
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder image) throws ShopException{
        //获取文件输入流  以及文件名
        InputStream in = image.getInputStream();
        String fileName = image.getFileName();

        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            //  1.  判断是否需要更新图片
            try {
                if(in != null && fileName!= null
                        && !"".equals(fileName)){
                    //Shop shopImg = mapper.queryShopById(shop.getShopId());
                    Optional<Shop> shopImg = shopRepository.findById(shop.getShopId());
                    if(shopImg.get().getShopImg()!=null){
                        ImageUtils.deleteFileOrPath(shopImg.get().getShopImg());
                    }
                    //更新图片
                    addShopImg(shop,image);
                }
                // 2.  更新商铺信息
                shop.setLastEditTime(new Date());
                try {
                    shopMapper.updateShop(shop);
                } catch (Exception e) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }
                //shop = mapper.queryShopById(shop.getShopId());
                shop = shopRepository.getOne(shop.getShopId());
                return new ShopExecution(ShopStateEnum.SUCCESS,shop);
            } catch (Exception e) {
                return new ShopExecution(ShopStateEnum.INNER_ERROR);
            }
        }
    }

    @Override
    public Shop getShopById(Integer shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);

        return shop.get();
    }

    @Override
    public ShopExecution addShop(Shop shop, ImageHolder image) {
        if(shop == null){
            //店铺信息为空
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        if(shop.getShopAddress() == null ){
            //地区为空
            return new ShopExecution(ShopStateEnum.NULL_AREA_ID);
        }
        if (shop.getShopCategory()==null){
            //店铺类别为空
            return new ShopExecution(ShopStateEnum.NULL_SHOP_CATEGORY_ID);
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
                        return new ShopExecution(ShopStateEnum.CHECK,shop);
                    }
                }else {
                    throw new ShopException("店铺图片未知");
                }
            }
        } catch (Exception e) {
            throw new ShopException("addShop ERROR : "+e.getMessage());
        }

    }

    private void addShopImg(Shop shop,ImageHolder image){
        //获取相对路径  "upload/item/shop/"+ shopId + "/"
        String path = PathUtils.getShopImagePath(shop.getShopId());
        //存储图片的绝对路径
        String realPath = ImageUtils.generateThumbnail(image,path);
        //将图片地址存入数据库
        shop.setShopImg(realPath);
    }
}
