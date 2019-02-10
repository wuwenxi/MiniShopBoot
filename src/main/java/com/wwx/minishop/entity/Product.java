package com.wwx.minishop.entity;

import java.util.Date;
import java.util.List;

/**
 *      商品信息
 *      多表联查 不使用jpa 采用mybatis进行数据查询
 */
public class Product {
    //商品编号
    private Integer productId;
    //商品名
    private String productName;
    //商品详情
    private String productDesc;
    //商品图片地址
    private String imgAddress;
    //原价
    private String normalPrice;
    //折扣价
    private String promotionPrice;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;
    //商品状态  可用：下架 ？ 1 ：0
    private Integer enableStatus;
    // 图片列表
    private List<ProductImg> productImgList;
    //商品类别
    private ProductCategory productCategory;
    //所属店铺
    private Shop shop;

    public Product() {
    }

    public Product(Integer productId, String productName, String productDesc,
                   String imgAddress, String normalPrice, String promotionPrice,
                   Integer priority, Date createTime, Date lastEditTime, Integer enableStatus,
                   List<ProductImg> productImgList, ProductCategory productCategory, Shop shop) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.imgAddress = imgAddress;
        this.normalPrice = normalPrice;
        this.promotionPrice = promotionPrice;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.enableStatus = enableStatus;
        this.productImgList = productImgList;
        this.productCategory = productCategory;
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", imgAddress='" + imgAddress + '\'' +
                ", normalPrice='" + normalPrice + '\'' +
                ", promotionPrice='" + promotionPrice + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", enableStatus=" + enableStatus +
                ", productImgList=" + productImgList +
                ", productCategory=" + productCategory +
                ", shop=" + shop +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice == null ? null : normalPrice.trim();
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice == null ? null : promotionPrice.trim();
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

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}