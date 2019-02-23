package com.wwx.minishop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *    商品类别
 * */
@Entity
@Table(name = "tb_product_category")
public class ProductCategory implements Serializable {
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
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public ProductCategory() {
    }

    public ProductCategory(String productCategoryName, Integer priority, Date createTime, Shop shop) {
        this.productCategoryName = productCategoryName;
        this.priority = priority;
        this.createTime = createTime;
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", productCategoryName='" + productCategoryName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", shop=" + shop +
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}