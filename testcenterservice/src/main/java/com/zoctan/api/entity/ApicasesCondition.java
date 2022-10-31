package com.zoctan.api.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "apicases_condition")
public class ApicasesCondition {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用例id
     */
    private Long caseid;

    public Long getConditioncaseid() {
        return conditioncaseid;
    }

    public void setConditioncaseid(Long conditioncaseid) {
        this.conditioncaseid = conditioncaseid;
    }

    private Long conditioncaseid;


    public Long getExecplanid() {
        return execplanid;
    }

    public void setExecplanid(Long execplanid) {
        this.execplanid = execplanid;
    }

    private Long execplanid;

    public Long getEnvassemid() {
        return envassemid;
    }

    public void setEnvassemid(Long envassemid) {
        this.envassemid = envassemid;
    }

    private Long envassemid;

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    private String casename;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String target;



    public String getExecplanname() {
        return execplanname;
    }

    public void setExecplanname(String execplanname) {
        this.execplanname = execplanname;
    }

    private String execplanname;


    public String getConnectstr() {
        return connectstr;
    }

    public void setConnectstr(String connectstr) {
        this.connectstr = connectstr;
    }

    private String connectstr;

    public String getCasedeployunitname() {
        return casedeployunitname;
    }

    public void setCasedeployunitname(String casedeployunitname) {
        this.casedeployunitname = casedeployunitname;
    }

    private String casedeployunitname;

    public String getCaseapiname() {
        return caseapiname;
    }

    public void setCaseapiname(String caseapiname) {
        this.caseapiname = caseapiname;
    }

    private String caseapiname;

    public String getApiname() {
        return apiname;
    }

    public void setApiname(String apiname) {
        this.apiname = apiname;
    }

    private String apiname;

    /**
     * 前置，后置
     */
    private String basetype;

    /**
     * 数据库，接口
     */
    private String conditionbasetype;

    /**
     * 数据库：mysql，oracle，sqlserver，接口：http,https,dubbo
     */
    private String conditiontype;

    /**
     * 包含调用接口的微服务
     */
    private String deployunitname;

    /**
     * 条件值，如果是数据库为sql，如果是接口为apicasename
     */
    private String conditionvalue;

    /**
     * 备注
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
     * 获取前置，后置
     *
     * @return basetype - 前置，后置
     */
    public String getBasetype() {
        return basetype;
    }

    /**
     * 设置前置，后置
     *
     * @param basetype 前置，后置
     */
    public void setBasetype(String basetype) {
        this.basetype = basetype;
    }

    /**
     * 获取数据库，接口
     *
     * @return conditionbasetype - 数据库，接口
     */
    public String getConditionbasetype() {
        return conditionbasetype;
    }

    /**
     * 设置数据库，接口
     *
     * @param conditionbasetype 数据库，接口
     */
    public void setConditionbasetype(String conditionbasetype) {
        this.conditionbasetype = conditionbasetype;
    }

    /**
     * 获取数据库：mysql，oracle，sqlserver，接口：http,https,dubbo
     *
     * @return conditiontype - 数据库：mysql，oracle，sqlserver，接口：http,https,dubbo
     */
    public String getConditiontype() {
        return conditiontype;
    }

    /**
     * 设置数据库：mysql，oracle，sqlserver，接口：http,https,dubbo
     *
     * @param conditiontype 数据库：mysql，oracle，sqlserver，接口：http,https,dubbo
     */
    public void setConditiontype(String conditiontype) {
        this.conditiontype = conditiontype;
    }

    /**
     * 获取包含调用接口的微服务
     *
     * @return deployunitname - 包含调用接口的微服务
     */
    public String getDeployunitname() {
        return deployunitname;
    }

    /**
     * 设置包含调用接口的微服务
     *
     * @param deployunitname 包含调用接口的微服务
     */
    public void setDeployunitname(String deployunitname) {
        this.deployunitname = deployunitname;
    }

    /**
     * 获取条件值，如果是数据库为sql，如果是接口为apicasename
     *
     * @return conditionvalue - 条件值，如果是数据库为sql，如果是接口为apicasename
     */
    public String getConditionvalue() {
        return conditionvalue;
    }

    /**
     * 设置条件值，如果是数据库为sql，如果是接口为apicasename
     *
     * @param conditionvalue 条件值，如果是数据库为sql，如果是接口为apicasename
     */
    public void setConditionvalue(String conditionvalue) {
        this.conditionvalue = conditionvalue;
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