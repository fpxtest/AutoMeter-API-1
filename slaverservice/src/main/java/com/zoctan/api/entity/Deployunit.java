package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Deployunit {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getProjectid() {
        return projectid;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    private Long projectid;

    /**
     * 微服务名
     */
    private String deployunitname;

    /**
     * 协议
     */
    private String protocal;

    /**
     * 访问端口
     */
    private String port;

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    private String baseurl;



    /**
     * 描述
     */
    private String memo;

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
     * 获取微服务名
     *
     * @return deployunitname - 微服务名
     */
    public String getDeployunitname() {
        return deployunitname;
    }

    /**
     * 设置微服务名
     *
     * @param deployunitname 微服务名
     */
    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    /**
     * 获取协议
     *
     * @return protocal - 协议
     */
    public String getProtocal() {
        return protocal;
    }

    /**
     * 设置协议
     *
     * @param protocal 协议
     */
    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    /**
     * 获取访问端口
     *
     * @return port - 访问端口
     */
    public String getPort() {
        return port;
    }

    /**
     * 设置访问端口
     *
     * @param port 访问端口
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 获取描述
     *
     * @return memo - 描述
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置描述
     *
     * @param memo 描述
     */
    public void setMemo(String memo) {
        this.memo = memo;
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