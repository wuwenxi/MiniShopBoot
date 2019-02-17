package com.wwx.minishop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *  店铺类别  如：奶茶店、超市等
 */
@Entity
@Table(name = "tb_shop_category")
public class ShopCategory {
    //类别编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopCategoryId;
    //类别名称
    @Column
    private String shopCategoryName;
    //详细情况
    @Column
    private String shopCategoryDesc;
    //权重
    @Column
    private Integer priority;
    //创建时间
    @Column
    private Date createTime;
    //更新时间
    @Column
    private Date lastEditTime;
    /**
     *    父级店铺类别id  ,由于父级店铺类别也是ShopCategory,创建类型为ShopCategory
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ShopCategory parent;

    public ShopCategory() {
    }

    public ShopCategory(Integer shopCategoryId, String shopCategoryName, String shopCategoryDesc,
                        Integer priority, Date createTime, Date lastEditTime,
                        ShopCategory parent) {
        this.shopCategoryId = shopCategoryId;
        this.shopCategoryName = shopCategoryName;
        this.shopCategoryDesc = shopCategoryDesc;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ShopCategory{" +
                "shopCategoryId=" + shopCategoryId +
                ", shopCategoryName='" + shopCategoryName + '\'' +
                ", shopCategoryDesc='" + shopCategoryDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", parent=" + parent +
                '}';
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCategoryDesc() {
        return shopCategoryDesc;
    }

    public void setShopCategoryDesc(String shopCategoryDesc) {
        this.shopCategoryDesc = shopCategoryDesc;
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

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }
}