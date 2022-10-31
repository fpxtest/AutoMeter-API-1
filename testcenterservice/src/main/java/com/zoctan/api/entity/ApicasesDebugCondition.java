package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "apicases_debug_condition")
public class ApicasesDebugCondition {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微服务Id
     */
    private Long deployunitid;

    /**
     * 微服务
     */
    private String deployunitname;

    /**
     * 用例id
     */
    private Long caseid;

    /**
     * 用例名
     */
    private String casename;

    /**
     * 条件名
     */
    private String conditionname;

    /**
     * 条件id
     */
    private Long conditionid;

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
     * 获取微服务Id
     *
     * @return deployunitid - 微服务Id
     */
    public Long getDeployunitid() {
        return deployunitid;
    }

    /**
     * 设置微服务Id
     *
     * @param deployunitid 微服务Id
     */
    public void setDeployunitid(Long deployunitid) {
        this.deployunitid = deployunitid;
    }

    /**
     * 获取微服务
     *
     * @return deployunitname - 微服务
     */
    public String getDeployunitname() {
        return deployunitname;
    }

    /**
     * 设置微服务
     *
     * @param deployunitname 微服务
     */
    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    /**
     * 获取用例id
     *
     * @return caseid - 用例id
     */
    public Long getCaseid() {
        return caseid;
    }

    /**
     * 设置用例id
     *
     * @param caseid 用例id
     */
    public void setCaseid(Long caseid) {
        this.caseid = caseid;
    }

    /**
     * 获取用例名
     *
     * @return casename - 用例名
     */
    public String getCasename() {
        return casename;
    }

    /**
     * 设置用例名
     *
     * @param casename 用例名
     */
    public void setCasename(String casename) {
        this.casename = casename;
    }

    /**
     * 获取条件名
     *
     * @return conditionname - 条件名
     */
    public String getConditionname() {
        return conditionname;
    }

    /**
     * 设置条件名
     *
     * @param conditionname 条件名
     */
    public void setConditionname(String conditionname) {
        this.conditionname = conditionname;
    }

    /**
     * 获取条件id
     *
     * @return conditionid - 条件id
     */
    public Long getConditionid() {
        return conditionid;
    }

    /**
     * 设置条件id
     *
     * @param conditionid 条件id
     */
    public void setConditionid(Long conditionid) {
        this.conditionid = conditionid;
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
}