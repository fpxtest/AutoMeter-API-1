package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "condition_report")
public class ConditionReport {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 条件id
     */
    private Long conditionid;

    /**
     * 批次id
     */
    private Long batchid;

    /**
     * 条件执行结果，成功，失败
     */
    private Long result;

    /**
     * 结果详情
     */
    private Long resultdesc;

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
     * 获取批次id
     *
     * @return batchid - 批次id
     */
    public Long getBatchid() {
        return batchid;
    }

    /**
     * 设置批次id
     *
     * @param batchid 批次id
     */
    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    /**
     * 获取条件执行结果，成功，失败
     *
     * @return result - 条件执行结果，成功，失败
     */
    public Long getResult() {
        return result;
    }

    /**
     * 设置条件执行结果，成功，失败
     *
     * @param result 条件执行结果，成功，失败
     */
    public void setResult(Long result) {
        this.result = result;
    }

    /**
     * 获取结果详情
     *
     * @return resultdesc - 结果详情
     */
    public Long getResultdesc() {
        return resultdesc;
    }

    /**
     * 设置结果详情
     *
     * @param resultdesc 结果详情
     */
    public void setResultdesc(Long resultdesc) {
        this.resultdesc = resultdesc;
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