package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Runtimeparamsrecord {
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

    /**
     * 执行计划id
     */
    private Long testplanid;

    /**
     * 批次id
     */
    private Long bantchid;

    /**
     * 用例运行时变量id
     */
    @Column(name = "apicases_runtimeparamsid")
    private Long apicasesRuntimeparamsid;

    /**
     * 运行时变量值
     */
    private String paramsvalue;

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
     * 获取执行计划id
     *
     * @return testplanid - 执行计划id
     */
    public Long getTestplanid() {
        return testplanid;
    }

    /**
     * 设置执行计划id
     *
     * @param testplanid 执行计划id
     */
    public void setTestplanid(Long testplanid) {
        this.testplanid = testplanid;
    }

    /**
     * 获取批次id
     *
     * @return bantchid - 批次id
     */
    public Long getBantchid() {
        return bantchid;
    }

    /**
     * 设置批次id
     *
     * @param bantchid 批次id
     */
    public void setBantchid(Long bantchid) {
        this.bantchid = bantchid;
    }

    /**
     * 获取用例运行时变量id
     *
     * @return apicases_runtimeparamsid - 用例运行时变量id
     */
    public Long getApicasesRuntimeparamsid() {
        return apicasesRuntimeparamsid;
    }

    /**
     * 设置用例运行时变量id
     *
     * @param apicasesRuntimeparamsid 用例运行时变量id
     */
    public void setApicasesRuntimeparamsid(Long apicasesRuntimeparamsid) {
        this.apicasesRuntimeparamsid = apicasesRuntimeparamsid;
    }

    /**
     * 获取运行时变量值
     *
     * @return paramsvalue - 运行时变量值
     */
    public String getParamsvalue() {
        return paramsvalue;
    }

    /**
     * 设置运行时变量值
     *
     * @param paramsvalue 运行时变量值
     */
    public void setParamsvalue(String paramsvalue) {
        this.paramsvalue = paramsvalue;
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