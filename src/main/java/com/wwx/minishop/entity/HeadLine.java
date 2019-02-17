package com.wwx.minishop.entity;

import javax.persistence.*;
import java.util.Date;

/**
 *    头条展示
 */
@Entity
@Table(name = "tb_head_line")
public class HeadLine {
    //头条id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineId;
    //头条名字
    @Column(name = "line_name")
    private String lineName;
    //头条链接
    @Column(name = "line_link")
    private String lineLink;
    //头条图片
    @Column(name = "line_img")
    private String lineImg;
    //权重(权值越大，展示越靠前)
    @Column
    private Integer priority;
    //可显示：不可显示 ? 1 :0
    @Column(name = "enable_status")
    private Integer enableStatus;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;
    //更新时间
    @Column(name = "last_edit_time")
    private Date lastEditTime;

    public HeadLine() {
    }

    public HeadLine(Integer lineId, String lineName, String lineLink, String lineImg,
                    Integer priority, Integer enableStatus, Date createTime,
                    Date lastEditTime) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.lineLink = lineLink;
        this.lineImg = lineImg;
        this.priority = priority;
        this.enableStatus = enableStatus;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "HeadLine{" +
                "lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", lineLink='" + lineLink + '\'' +
                ", lineImg='" + lineImg + '\'' +
                ", priority=" + priority +
                ", enableStatus=" + enableStatus +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink == null ? null : lineLink.trim();
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg == null ? null : lineImg.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
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