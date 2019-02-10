package com.wwx.minishop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *    商品类别
 * */
@Entity
@Table(name = "tb_product_category")
public class ProductCategory {
    //商品Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCategoryId;
    //商品名
    @Column(name = "product_category_name")
    private String productCategoryName;
    //权值
    @Column
    private Integer priority;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;
    //商铺id
    @Column(name = "shop_id")
    private Integer shopId;

    public ProductCategory() {
    }

    public ProductCategory(Integer productCategoryId, String productCategoryName,
                           Integer priority, Date createTime, Integer shopId) {
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
        this.priority = priority;
        this.createTime = createTime;
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", productCategoryName='" + productCategoryName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", shopId=" + shopId +
                '}';
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}