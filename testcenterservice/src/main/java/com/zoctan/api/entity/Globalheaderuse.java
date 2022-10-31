package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Globalheaderuse {
    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * globalheaderId
     */
    private Long globalheaderid;

    /**
     * 微服务Id
     */
    public Long getExecuteplanid() {
        return executeplanid;
    }

    public void setExecuteplanid(Long executeplanid) {
        this.executeplanid = executeplanid;
    }

    /**
     * 集合Id
     */
    private Long executeplanid;


    public String getGlobalheadername() {
        return globalheadername;
    }

    public void setGlobalheadername(String globalheadername) {
        this.globalheadername = globalheadername;
    }

    private String globalheadername;



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
     * 获取globalheaderId
     *
     * @return globalheaderid - globalheaderId
     */
    public Long getGlobalheaderid() {
        return globalheaderid;
    }

    /**
     * 设置globalheaderId
     *
     * @param globalheaderid globalheaderId
     */
    public void setGlobalheaderid(Long globalheaderid) {
        this.globalheaderid = globalheaderid;
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