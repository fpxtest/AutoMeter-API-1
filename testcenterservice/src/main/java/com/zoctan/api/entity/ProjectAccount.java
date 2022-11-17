package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "project_account")
public class ProjectAccount {
    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 客户名
     */
    private Long projectid;

    /**
     * 手机号
     */
    private Long accountid;

    /**
     * 项目名
     */
    private String projectname;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 创建人
     */
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
     * 获取客户名
     *
     * @return projectid - 客户名
     */
    public Long getProjectid() {
        return projectid;
    }

    /**
     * 设置客户名
     *
     * @param projectid 客户名
     */
    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    /**
     * 获取手机号
     *
     * @return accountid - 手机号
     */
    public Long getAccountid() {
        return accountid;
    }

    /**
     * 设置手机号
     *
     * @param accountid 手机号
     */
    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    /**
     * 获取项目名
     *
     * @return projectname - 项目名
     */
    public String getProjectname() {
        return projectname;
    }

    /**
     * 设置项目名
     *
     * @param projectname 项目名
     */
    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
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