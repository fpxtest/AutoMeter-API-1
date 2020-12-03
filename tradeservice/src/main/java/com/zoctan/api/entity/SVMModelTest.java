package com.zoctan.api.entity;

import java.util.Date;

public class SVMModelTest {

    public String getPythonfile() {
        return pythonfile;
    }

    public void setPythonfile(String pythonfile) {
        this.pythonfile = pythonfile;
    }

    public String getStockids() {
        return stockids;
    }

    public void setStockids(String stockids) {
        this.stockids = stockids;
    }

    public String getModelparams() {
        return modelparams;
    }

    public void setModelparams(String modelparams) {
        this.modelparams = modelparams;
    }


    public String getAccountchange() {
        return accountchange;
    }

    public void setAccountchange(String accountchange) {
        this.accountchange = accountchange;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 模型参数
     */
    private String pythonfile;

    /**
     * 股票id
     */
    private String stockids;

    /**
     * 模型参数
     */
    private String modelparams;


    /**
     * 账户资金变动
     */
    private String accountchange;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


}