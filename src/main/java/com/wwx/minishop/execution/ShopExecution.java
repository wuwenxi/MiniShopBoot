package com.wwx.minishop.execution;

import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.enums.ShopStateEnum;
import com.wwx.minishop.exception.ShopException;

import java.util.List;

/**
 *     店铺返回类型
 */
public class ShopExecution {
    //状态
    private Integer state;
    //状态信息
    private String stateInfo;
    //在线店铺
    private Integer count;
    //操作店铺时用（增删改店铺）
    private Shop shop;
    //店铺列表（查询店铺）
    private List<Shop> shops;

    public ShopExecution() {
    }

    //店铺操作失败
    public ShopExecution(ShopStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        throw new ShopException(stateEnum.getStateInfo());
    }

    //店铺操作成功  返回单个店铺
    public ShopExecution(ShopStateEnum shopStateEnum, Shop shop){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }

    //店铺操作成功  返回列表店铺
    public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shops){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shops = shops;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
