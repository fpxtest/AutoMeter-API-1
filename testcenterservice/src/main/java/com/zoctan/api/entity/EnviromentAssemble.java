package com.zoctan.api.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "enviroment_assemble")
public class EnviromentAssemble {
    /**
     * 环境Id
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

    public String getAssemblename() {
        return assemblename;
    }

    public void setAssemblename(String assemblename) {
        this.assemblename = assemblename;
    }

    private String assemblename;


    /**
     * mysql，oracle，redis
     */
    private String assembletype;

    /**
     * 连接字
     */
    private String connectstr;

    /**
     * 环境描述
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
     * 获取环境Id
     *
     * @return id - 环境Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置环境Id
     *
     * @param id 环境Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取mysql，oracle，redis
     *
     * @return assembletype - mysql，oracle，redis
     */
    public String getAssembletype() {
        return assembletype;
    }

    /**
     * 设置mysql，oracle，redis
     *
     * @param assembletype mysql，oracle，redis
     */
    public void setAssembletype(String assembletype) {
        this.assembletype = assembletype;
    }

    /**
     * 获取连接字
     *
     * @return connectstr - 连接字
     */
    public String getConnectstr() {
        return connectstr;
    }

    /**
     * 设置连接字
     *
     * @param connectstr 连接字
     */
    public void setConnectstr(String connectstr) {
        this.connectstr = connectstr;
    }

    /**
     * 获取环境描述
     *
     * @return memo - 环境描述
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置环境描述
     *
     * @param memo 环境描述
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