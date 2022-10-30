package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Machine {
    /**
     * 用户Id
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
     * 机器名
     */
    private String machinename;

    /**
     * ip
     */
    private String ip;

    /**
     * cpu
     */
    private String cpu;

    /**
     * disk
     */
    private String disk;

    /**
     * 内存
     */
    private String mem;

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
     * 获取用户Id
     *
     * @return id - 用户Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户Id
     *
     * @param id 用户Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取机器名
     *
     * @return machinename - 机器名
     */
    public String getMachinename() {
        return machinename;
    }

    /**
     * 设置机器名
     *
     * @param machinename 机器名
     */
    public void setMachinename(String machinename) {
        this.machinename = machinename;
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
     * 获取cpu
     *
     * @return cpu - cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * 设置cpu
     *
     * @param cpu cpu
     */
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    /**
     * 获取disk
     *
     * @return disk - disk
     */
    public String getDisk() {
        return disk;
    }

    /**
     * 设置disk
     *
     * @param disk disk
     */
    public void setDisk(String disk) {
        this.disk = disk;
    }

    /**
     * 获取内存
     *
     * @return mem - 内存
     */
    public String getMem() {
        return mem;
    }

    /**
     * 设置内存
     *
     * @param mem 内存
     */
    public void setMem(String mem) {
        this.mem = mem;
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