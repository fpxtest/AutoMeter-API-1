package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Routeperformancereport {
    /**
     * 路由Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 集合id
     */
    private Long executeplanid;

    /**
     * 表名
     */
    private String tablename;

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
     * 获取路由Id
     *
     * @return id - 路由Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置路由Id
     *
     * @param id 路由Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取集合id
     *
     * @return executeplanid - 集合id
     */
    public Long getExecuteplanid() {
        return executeplanid;
    }

    /**
     * 设置集合id
     *
     * @param executeplanid 集合id
     */
    public void setExecuteplanid(Long executeplanid) {
        this.executeplanid = executeplanid;
    }

    /**
     * 获取表名
     *
     * @return tablename - 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 设置表名
     *
     * @param tablename 表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
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
}