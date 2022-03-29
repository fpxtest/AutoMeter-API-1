package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "apicases_variables")
public class ApicasesVariables {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用例Id
     */
    private Long caseid;

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

    /**
     * 用例名
     */
    private String casename;

    public String getDeployunitname() {
        return deployunitname;
    }

    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    private String deployunitname;
    private String apiname;


    /**
     * 变量Id
     */
    private Long variablesid;

    /**
     * 变量名
     */
    private String variablesname;

    /**
     * 备注
     */
    private String memo;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

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
     * 获取用例Id
     *
     * @return caseid - 用例Id
     */
    public Long getCaseid() {
        return caseid;
    }

    /**
     * 设置用例Id
     *
     * @param caseid 用例Id
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
     * 获取变量Id
     *
     * @return variablesid - 变量Id
     */
    public Long getVariablesid() {
        return variablesid;
    }

    /**
     * 设置变量Id
     *
     * @param variablesid 变量Id
     */
    public void setVariablesid(Long variablesid) {
        this.variablesid = variablesid;
    }

    /**
     * 获取变量名
     *
     * @return variablesname - 变量名
     */
    public String getVariablesname() {
        return variablesname;
    }

    /**
     * 设置变量名
     *
     * @param variablesname 变量名
     */
    public void setVariablesname(String variablesname) {
        this.variablesname = variablesname;
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
}