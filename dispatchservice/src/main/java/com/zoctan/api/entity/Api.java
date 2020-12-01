package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Api {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * DeployUnitId
     */
    private Long deployunitid;

    /**
     * 接口名
     */
    private String apiname;

    /**
     * 访问方式
     */
    private String visittype;

    /**
     * url访问路径
     */
    private String path;

    /**
     * 头参数以逗号分隔
     */
    private String headersname;

    /**
     * 参数以逗号分隔
     */
    private String paramsname;

    /**
     * 是否有数据体
     */
    private Boolean body;

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
     * 获取DeployUnitId
     *
     * @return deployunitid - DeployUnitId
     */
    public Long getDeployunitid() {
        return deployunitid;
    }

    /**
     * 设置DeployUnitId
     *
     * @param deployunitid DeployUnitId
     */
    public void setDeployunitid(Long deployunitid) {
        this.deployunitid = deployunitid;
    }

    /**
     * 获取接口名
     *
     * @return apiname - 接口名
     */
    public String getApiname() {
        return apiname;
    }

    /**
     * 设置接口名
     *
     * @param apiname 接口名
     */
    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    /**
     * 获取访问方式
     *
     * @return visittype - 访问方式
     */
    public String getVisittype() {
        return visittype;
    }

    /**
     * 设置访问方式
     *
     * @param visittype 访问方式
     */
    public void setVisittype(String visittype) {
        this.visittype = visittype;
    }

    /**
     * 获取url访问路径
     *
     * @return path - url访问路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置url访问路径
     *
     * @param path url访问路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取头参数以逗号分隔
     *
     * @return headersname - 头参数以逗号分隔
     */
    public String getHeadersname() {
        return headersname;
    }

    /**
     * 设置头参数以逗号分隔
     *
     * @param headersname 头参数以逗号分隔
     */
    public void setHeadersname(String headersname) {
        this.headersname = headersname;
    }

    /**
     * 获取参数以逗号分隔
     *
     * @return paramsname - 参数以逗号分隔
     */
    public String getParamsname() {
        return paramsname;
    }

    /**
     * 设置参数以逗号分隔
     *
     * @param paramsname 参数以逗号分隔
     */
    public void setParamsname(String paramsname) {
        this.paramsname = paramsname;
    }

    /**
     * 获取是否有数据体
     *
     * @return body - 是否有数据体
     */
    public Boolean getBody() {
        return body;
    }

    /**
     * 设置是否有数据体
     *
     * @param body 是否有数据体
     */
    public void setBody(Boolean body) {
        this.body = body;
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