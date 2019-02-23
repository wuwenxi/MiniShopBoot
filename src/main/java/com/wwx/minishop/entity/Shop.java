package com.wwx.minishop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *    店铺
 * */
@Entity
@Table(name = "tb_shop")
public class Shop implements Serializable {
    //店铺id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;
    //店家id
    @OneToOne
    @JoinColumn(name = "owner_id")
    private PersonInfo owner;
    //商铺类别id
    @OneToOne
    @JoinColumn(name = "shop_category_id")
    private ShopCategory shopCategory;
    //店铺名
    @Column
    private String shopName;
    //店铺描述
    @Column
    private String shopDesc;
    //店铺地址
    @Column
    private String shopAddress;
    //联系方式
    @Column
    private String phone;
    //店铺图片地址
    @Column
    private String shopImg;
    //权值
    @Column
    private Integer priority;
    //创建时间
    @Column
    private Date createTime;
    //更新时间
    @Column
    private Date lastEditTime;
    //状态   可用:1  不可用:-1  审核:0
    @Column
    private Integer enableStatus;
    //超级管理员建议
    @Column
    private String advice;

    public Shop() {
    }

    public Shop(PersonInfo owner, ShopCategory shopCategory, String shopName, String shopDesc, String shopAddress, String phone, String shopImg, Integer priority,
                Date createTime, Date lastEditTime, Integer enableStatus, String advice) {
        this.owner = owner;
        this.shopCategory = shopCategory;
        this.shopName = shopName;
        this.shopDesc = shopDesc;
        this.shopAddress = shopAddress;
        this.phone = phone;
        this.shopImg = shopImg;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.enableStatus = enableStatus;
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", owner=" + owner +
                ", shopCategory=" + shopCategory +
                ", shopName='" + shopName + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", shopImg='" + shopImg + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", enableStatus=" + enableStatus +
                ", advice='" + advice + '\'' +
                '}';
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public PersonInfo getOwner() {
        return owner;
    }

    public void setOwner(PersonInfo owner) {
        this.owner = owner;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}