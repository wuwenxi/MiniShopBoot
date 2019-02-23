package com.wwx.minishop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *    用户信息，包括普通用户、店家、超级管理员的信息
 */
@Entity
@Table(name = "tb_person_info")
public class PersonInfo implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    //用户
    @Column
    private String name;
    //性别
    @Column
    private String gender;
    //电子邮箱
    @Column
    private String email;
    //用户头像地址
    @Column(name = "profile_img")
    private String profileImg;
    //用户类型  普通用户  店家  超级管理员
    @Column(name = "user_type")
    private Integer userType;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;
    //更新时间
    @Column(name = "last_edit_time")
    private Date lastEditTime;
    //用户状态 禁止访问：可以访问？0:1
    @Column(name = "enable_status")
    private Integer enableStatus;

    public PersonInfo() {
    }

    public PersonInfo(Integer userId, String name, String gender, String email,
                      String profileImg, Integer userType, Date createTime, Date lastEditTime, Integer enableStatus) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.profileImg = profileImg;
        this.userType = userType;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", userType=" + userType +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", enableStatus=" + enableStatus +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg == null ? null : profileImg.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
}