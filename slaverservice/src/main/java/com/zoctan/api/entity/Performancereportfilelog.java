package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Performancereportfilelog {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long execplanid;
    private Long batchid;
    private Long caseid;

    public Long getExecplanid() {
        return execplanid;
    }

    public void setExecplanid(Long execplanid) {
        this.execplanid = execplanid;
    }

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    public Long getCaseid() {
        return caseid;
    }

    public void setCaseid(Long caseid) {
        this.caseid = caseid;
    }

    public Long getSlaverid() {
        return slaverid;
    }

    public void setSlaverid(Long slaverid) {
        this.slaverid = slaverid;
    }

    private Long slaverid;


    /**
     * 文件名，planid+batchid+slaverid
     */
    private String filename;

    /**
     * 状态
     */
    private String status;

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
     * 获取文件名，planid+batchid+slaverid
     *
     * @return filename - 文件名，planid+batchid+slaverid
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置文件名，planid+batchid+slaverid
     *
     * @param filename 文件名，planid+batchid+slaverid
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
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