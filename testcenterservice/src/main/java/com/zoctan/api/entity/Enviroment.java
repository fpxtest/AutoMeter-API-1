package com.zoctan.api.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Enviroment {
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
    /**
     * 环境名
     */
    private String enviromentname;

    /**
     * 环境描述
     */
    private String memo;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    private String creator;



    public String getEnvtype() {
        return envtype;
    }

    public void setEnvtype(String envtype) {
        this.envtype = envtype;
    }

    private String envtype;

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
     * 获取环境名
     *
     * @return enviromentname - 环境名
     */
    public String getEnviromentname() {
        return enviromentname;
    }

    /**
     * 设置环境名
     *
     * @param enviromentname 环境名
     */
    public void setEnviromentname(String enviromentname) {
        this.enviromentname = enviromentname;
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
}