package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "deployunit_model")
public class DeployunitModel {
    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微服务id
     */
    private Long deployunitid;

    /**
     * 模块
     */
    private String modelname;

    /**
     * 描述
     */
    private String memo;

    /**
     * 创建人
     */
    private String creator;

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
     * 获取主键Id
     *
     * @return id - 主键Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键Id
     *
     * @param id 主键Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取微服务id
     *
     * @return deployunitid - 微服务id
     */
    public Long getDeployunitid() {
        return deployunitid;
    }

    /**
     * 设置微服务id
     *
     * @param deployunitid 微服务id
     */
    public void setDeployunitid(Long deployunitid) {
        this.deployunitid = deployunitid;
    }

    /**
     * 获取模块
     *
     * @return modelname - 模块
     */
    public String getModelname() {
        return modelname;
    }

    /**
     * 设置模块
     *
     * @param modelname 模块
     */
    public void setModelname(String modelname) {
        this.modelname = modelname;
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
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
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