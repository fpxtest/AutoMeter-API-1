package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Tester {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 测试人员姓名
     */
    private String testername;

    /**
     * 测试人员职务
     */
    private String testertitle;

    /**
     * 所属组织
     */
    private String testerorg;

    /**
     * 备注
     */
    private String testermemo;

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
     * 获取测试人员姓名
     *
     * @return testername - 测试人员姓名
     */
    public String getTestername() {
        return testername;
    }

    /**
     * 设置测试人员姓名
     *
     * @param testername 测试人员姓名
     */
    public void setTestername(String testername) {
        this.testername = testername;
    }

    /**
     * 获取测试人员职务
     *
     * @return testertitle - 测试人员职务
     */
    public String getTestertitle() {
        return testertitle;
    }

    /**
     * 设置测试人员职务
     *
     * @param testertitle 测试人员职务
     */
    public void setTestertitle(String testertitle) {
        this.testertitle = testertitle;
    }

    /**
     * 获取所属组织
     *
     * @return testerorg - 所属组织
     */
    public String getTesterorg() {
        return testerorg;
    }

    /**
     * 设置所属组织
     *
     * @param testerorg 所属组织
     */
    public void setTesterorg(String testerorg) {
        this.testerorg = testerorg;
    }

    /**
     * 获取备注
     *
     * @return testermemo - 备注
     */
    public String getTestermemo() {
        return testermemo;
    }

    /**
     * 设置备注
     *
     * @param testermemo 备注
     */
    public void setTestermemo(String testermemo) {
        this.testermemo = testermemo;
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