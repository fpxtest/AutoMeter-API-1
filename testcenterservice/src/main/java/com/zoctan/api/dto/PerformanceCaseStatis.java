package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class PerformanceCaseStatis {

    public float getCaseNum() {
        return CaseNum;
    }

    public void setCaseNum(float caseNum) {
        CaseNum = caseNum;
    }

    public long getThreadnums() {
        return Threadnums;
    }

    public void setThreadnums(long threadnums) {
        Threadnums = threadnums;
    }

    public long getLoops() {
        return Loops;
    }

    public void setLoops(long loops) {
        Loops = loops;
    }

    public int getSlavernums() {
        return Slavernums;
    }

    public void setSlavernums(int slavernums) {
        Slavernums = slavernums;
    }

    public long getExecCaseNums() {
        return ExecCaseNums;
    }

    public void setExecCaseNums(long execCaseNums) {
        ExecCaseNums = execCaseNums;
    }

    public float getSuccessCaseNums() {
        return SuccessCaseNums;
    }

    public void setSuccessCaseNums(float successCaseNums) {
        SuccessCaseNums = successCaseNums;
    }

    public float getFailCaseNums() {
        return FailCaseNums;
    }

    public void setFailCaseNums(float failCaseNums) {
        FailCaseNums = failCaseNums;
    }

    public long getNotExecCaseNums() {
        return NotExecCaseNums;
    }

    public void setNotExecCaseNums(long notExecCaseNums) {
        NotExecCaseNums = notExecCaseNums;
    }

    public String getSuccessrate() {
        return successrate;
    }

    public void setSuccessrate(String successrate) {
        this.successrate = successrate;
    }

    public String getFailrate() {
        return failrate;
    }

    public void setFailrate(String failrate) {
        this.failrate = failrate;
    }

    public double getCosttime() {
        return costtime;
    }

    public void setCosttime(double costtime) {
        this.costtime = costtime;
    }

    private float CaseNum;
    private long Threadnums;
    private long Loops;
    private int Slavernums;

    private long ExecCaseNums;
    private float SuccessCaseNums;
    private float FailCaseNums;
    private long NotExecCaseNums;

    private String successrate;
    private String failrate;

    private double costtime;


}
