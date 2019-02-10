package com.wwx.minishop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *        显示当前区域
 */
@Entity  ///告诉jpa这是一个实体类
@Table(name = "tb_area")   //与数据表映射  默认是实体类名小写
public class Area {
    //区域编号
    @Id//标记主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer areaId;
    //区域名称
    @Column(name = "area_name")
    private String areaName;
    //区域详情
    @Column(name = "area_desc")
    private String areaDesc;
    //权重，权值越大，显示位置越靠前
    @Column
    private Integer priority;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;
    //更新时间
    @Column(name = "last_edit_time")
    private Date lastEditTime;

    public Area() {
    }

    public Area(Integer areaId, String areaName, String areaDesc,
                Integer priority, Date createTime, Date lastEditTime) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaDesc = areaDesc;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", areaDesc='" + areaDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc == null ? null : areaDesc.trim();
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
}