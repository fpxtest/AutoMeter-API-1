package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Slaver {
    /**
     * 执行机Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 执行机器名
     */
    private String slavername;

    /**
     * ip
     */
    private String ip;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    /**
     * port
     */
    private String port;

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    /**
     * stype
     */
    private String stype;

    /**
     * 状态，idle，running
     */
    private String status;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 内存
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
     * 获取执行机Id
     *
     * @return id - 执行机Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置执行机Id
     *
     * @param id 执行机Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取执行机器名
     *
     * @return slavername - 执行机器名
     */
    public String getSlavername() {
        return slavername;
    }

    /**
     * 设置执行机器名
     *
     * @param slavername 执行机器名
     */
    public void setSlavername(String slavername) {
        this.slavername = slavername;
    }

    /**
     * 获取ip
     *
     * @return ip - ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip
     *
     * @param ip ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取状态，idle，running
     *
     * @return status - 状态，idle，running
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态，idle，running
     *
     * @param status 状态，idle，running
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