package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Mockapirespone {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 接口Id
     */
    private Long apiid;

    /**
     * 接口
     */
    private String responecode;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 上一次修改时间
     */
    @Column(name = "lastmodify_time")
    private Date lastmodifyTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 响应内容
     */
    private String responecontent;

    /**
     * 获取Id
     *
     * @return id - Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置Id
     *
     * @param id Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取接口Id
     *
     * @return apiid - 接口Id
     */
    public Long getApiid() {
        return apiid;
    }

    /**
     * 设置接口Id
     *
     * @param apiid 接口Id
     */
    public void setApiid(Long apiid) {
        this.apiid = apiid;
    }

    /**
     * 获取接口
     *
     * @return responecode - 接口
     */
    public String getResponecode() {
        return responecode;
    }

    /**
     * 设置接口
     *
     * @param responecode 接口
     */
    public void setResponecode(String responecode) {
        this.responecode = responecode;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取上一次修改时间
     *
     * @return lastmodify_time - 上一次修改时间
     */
    public Date getLastmodifyTime() {
        return lastmodifyTime;
    }

    /**
     * 设置上一次修改时间
     *
     * @param lastmodifyTime 上一次修改时间
     */
    public void setLastmodifyTime(Date lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }

    /**
     * 获取创建者
     *
     * @return creator - 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取响应内容
     *
     * @return responecontent - 响应内容
     */
    public String getResponecontent() {
        return responecontent;
    }

    /**
     * 设置响应内容
     *
     * @param responecontent 响应内容
     */
    public void setResponecontent(String responecontent) {
        this.responecontent = responecontent;
    }
}