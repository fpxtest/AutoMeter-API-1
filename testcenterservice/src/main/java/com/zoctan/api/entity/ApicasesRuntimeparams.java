package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "apicases_runtimeparams")
public class ApicasesRuntimeparams {
    /**
     * 接口运行时变量Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用例id
     */
    private Long caseid;

    /**
     * 运行时变量名
     */
    private String paramsname;

    /**
     * 运行时变量名描述
     */
    private String paramsnamedes;

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
     * 获取接口运行时变量Id
     *
     * @return id - 接口运行时变量Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置接口运行时变量Id
     *
     * @param id 接口运行时变量Id
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
     * 获取运行时变量名
     *
     * @return paramsname - 运行时变量名
     */
    public String getParamsname() {
        return paramsname;
    }

    /**
     * 设置运行时变量名
     *
     * @param paramsname 运行时变量名
     */
    public void setParamsname(String paramsname) {
        this.paramsname = paramsname;
    }

    /**
     * 获取运行时变量名描述
     *
     * @return paramsnamedes - 运行时变量名描述
     */
    public String getParamsnamedes() {
        return paramsnamedes;
    }

    /**
     * 设置运行时变量名描述
     *
     * @param paramsnamedes 运行时变量名描述
     */
    public void setParamsnamedes(String paramsnamedes) {
        this.paramsnamedes = paramsnamedes;
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