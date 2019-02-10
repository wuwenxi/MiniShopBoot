package com.wwx.minishop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *   本地账号
 */
public class LocalAuth {
    //本地账号编号
    private Integer localAuthId;
    //关联用户
    private PersonInfo PersonInfo;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;

    public LocalAuth() {
    }

    public LocalAuth(Integer localAuthId, PersonInfo personInfo,
                     String userName, String password, Date createTime, Date lastEditTime) {
        this.localAuthId = localAuthId;
        PersonInfo = personInfo;
        this.userName = userName;
        this.password = password;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "LocalAuth{" +
                "localAuthId=" + localAuthId +
                ", PersonInfo=" + PersonInfo +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Integer getLocalAuthId() {
        return localAuthId;
    }

    public void setLocalAuthId(Integer localAuthId) {
        this.localAuthId = localAuthId;
    }

    public PersonInfo getPersonInfo() {
        return PersonInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        PersonInfo = personInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
}