package com.zoctan.api.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "executeplan_params")
public class ExecuteplanParams {
    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 执行计划id
     */
    private Long executeplanid;

    /**
     * 参数类型
     */
    private String paramstype;

    /**
     * Key
     */
    private String keyname;

    /**
     * 值
     */
    private String keyvalue;

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
     * 获取执行计划id
     *
     * @return executeplanid - 执行计划id
     */
    public Long getExecuteplanid() {
        return executeplanid;
    }

    /**
     * 设置执行计划id
     *
     * @param executeplanid 执行计划id
     */
    public void setExecuteplanid(Long executeplanid) {
        this.executeplanid = executeplanid;
    }

    /**
     * 获取参数类型
     *
     * @return paramstype - 参数类型
     */
    public String getParamstype() {
        return paramstype;
    }

    /**
     * 设置参数类型
     *
     * @param paramstype 参数类型
     */
    public void setParamstype(String paramstype) {
        this.paramstype = paramstype;
    }

    /**
     * 获取Key
     *
     * @return keyname - Key
     */
    public String getKeyname() {
        return keyname;
    }

    /**
     * 设置Key
     *
     * @param keyname Key
     */
    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    /**
     * 获取值
     *
     * @return keyvalue - 值
     */
    public String getKeyvalue() {
        return keyvalue;
    }

    /**
     * 设置值
     *
     * @param keyvalue 值
     */
    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
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