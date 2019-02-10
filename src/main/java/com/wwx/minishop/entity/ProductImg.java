package com.wwx.minishop.entity;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;

/**
 *   商品详情图
 * */
@Entity
@Table(name = "tn_product_img")
public class  ProductImg {
    //商品图片id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productImgId;
    //图片地址
    @Column(name = "img_address")
    private String imgAddress;
    //图片详情
    @Column(name = "img_desc")
    private String imgDesc;
    //权值
    @Column
    private Integer priority;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;
    //商品id
    @Column(name = "product_id")
    private Integer productId;

    public ProductImg() {
    }

    public ProductImg(Integer productImgId, String imgAddress,
                      String imgDesc, Integer priority, Date createTime, Integer productId) {
        this.productImgId = productImgId;
        this.imgAddress = imgAddress;
        this.imgDesc = imgDesc;
        this.priority = priority;
        this.createTime = createTime;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductImg{" +
                "productImgId=" + productImgId +
                ", imgAddress='" + imgAddress + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", productId=" + productId +
                '}';
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public Integer getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Integer productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc == null ? null : imgDesc.trim();
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}