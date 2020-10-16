package com.zoctan.api.entity;

import java.util.Date;
import javax.persistence.*;

public class Dictionary {
    /**
     * 用户Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字典类名
     */
    private String dicname;

    /**
     * 字典编码
     */
    private String diccode;

    /**
     * 字典项名
     */
    private String dicitemname;

    /**
     * 字典项值
     */
    private String dicitmevalue;

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
     * 获取字典类名
     *
     * @return dicname - 字典类名
     */
    public String getDicname() {
        return dicname;
    }

    /**
     * 设置字典类名
     *
     * @param dicname 字典类名
     */
    public void setDicname(String dicname) {
        this.dicname = dicname;
    }

    /**
     * 获取字典编码
     *
     * @return diccode - 字典编码
     */
    public String getDiccode() {
        return diccode;
    }

    /**
     * 设置字典编码
     *
     * @param diccode 字典编码
     */
    public void setDiccode(String diccode) {
        this.diccode = diccode;
    }

    /**
     * 获取字典项名
     *
     * @return dicitemname - 字典项名
     */
    public String getDicitemname() {
        return dicitemname;
    }

    /**
     * 设置字典项名
     *
     * @param dicitemname 字典项名
     */
    public void setDicitemname(String dicitemname) {
        this.dicitemname = dicitemname;
    }

    /**
     * 获取字典项值
     *
     * @return dicitmevalue - 字典项值
     */
    public String getDicitmevalue() {
        return dicitmevalue;
    }

    /**
     * 设置字典项值
     *
     * @param dicitmevalue 字典项值
     */
    public void setDicitmevalue(String dicitmevalue) {
        this.dicitmevalue = dicitmevalue;
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