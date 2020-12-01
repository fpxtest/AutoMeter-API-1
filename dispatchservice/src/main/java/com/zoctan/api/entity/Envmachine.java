package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Envmachine {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 环境Id
     */
    private Long envid;

    /**
     * 服务器Id
     */
    private Long machineid;

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
     * 获取环境Id
     *
     * @return envid - 环境Id
     */
    public Long getEnvid() {
        return envid;
    }

    /**
     * 设置环境Id
     *
     * @param envid 环境Id
     */
    public void setEnvid(Long envid) {
        this.envid = envid;
    }

    /**
     * 获取服务器Id
     *
     * @return machineid - 服务器Id
     */
    public Long getMachineid() {
        return machineid;
    }

    /**
     * 设置服务器Id
     *
     * @param machineid 服务器Id
     */
    public void setMachineid(Long machineid) {
        this.machineid = machineid;
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