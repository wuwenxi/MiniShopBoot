package com.wwx.minishop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *   本地账号
 */
@Entity
@Table(name = "tb_local_auth")
public class LocalAuth implements Serializable {
    //本地账号编号
    @Id
    @GeneratedValue
    private Integer localAuthId;
    //关联用户
    @OneToOne
    @JoinColumn(name = "user_id")
    private PersonInfo PersonInfo;
    //用户名
    @Column
    private String userName;
    //用户密码
    @Column
    private String password;
    //创建时间
    @Column
    private Date createTime;
    //更新时间
    @Column
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