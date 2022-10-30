package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Testcondition {
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
     * 目标Id，计划，用例的id
     */
    private Long objectid;

    private Long apiid;

    public Long getApiid() {
        return apiid;
    }

    public void setApiid(Long apiid) {
        this.apiid = apiid;
    }

    public Long getDeployunitid() {
        return deployunitid;
    }

    public void setDeployunitid(Long deployunitid) {
        this.deployunitid = deployunitid;
    }

    private Long deployunitid;




    public String getConditionname() {
        return conditionname;
    }

    public void setConditionname(String conditionname) {
        this.conditionname = conditionname;
    }

    private String conditionname;

    /**
     * 目标名
     */
    private String objectname;

    /**
     * 目标类型
     */
    private String objecttype;

    /**
     * 条件类型，前置，后置，其他
     */
    private String conditiontype;

    /**
     * 备注
     */
    private String memo;

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    public String getDeployunitname() {
        return deployunitname;
    }

    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    private String apiname;
    private String deployunitname;



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
     * 获取目标Id，计划，用例的id
     *
     * @return objectid - 目标Id，计划，用例的id
     */
    public Long getObjectid() {
        return objectid;
    }

    /**
     * 设置目标Id，计划，用例的id
     *
     * @param objectid 目标Id，计划，用例的id
     */
    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    /**
     * 获取目标名
     *
     * @return objectname - 目标名
     */
    public String getObjectname() {
        return objectname;
    }

    /**
     * 设置目标名
     *
     * @param objectname 目标名
     */
    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    /**
     * 获取目标类型
     *
     * @return objecttype - 目标类型
     */
    public String getObjecttype() {
        return objecttype;
    }

    /**
     * 设置目标类型
     *
     * @param objecttype 目标类型
     */
    public void setObjecttype(String objecttype) {
        this.objecttype = objecttype;
    }

    /**
     * 获取条件类型，前置，后置，其他
     *
     * @return conditiontype - 条件类型，前置，后置，其他
     */
    public String getConditiontype() {
        return conditiontype;
    }

    /**
     * 设置条件类型，前置，后置，其他
     *
     * @param conditiontype 条件类型，前置，后置，其他
     */
    public void setConditiontype(String conditiontype) {
        this.conditiontype = conditiontype;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
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