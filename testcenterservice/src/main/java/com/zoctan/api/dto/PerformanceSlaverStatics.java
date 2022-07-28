package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class PerformanceSlaverStatics {


    public long getCaseNum() {
        return CaseNum;
    }

    public void setCaseNum(long caseNum) {
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

    public long getSuccessCaseNums() {
        return SuccessCaseNums;
    }

    public void setSuccessCaseNums(long successCaseNums) {
        SuccessCaseNums = successCaseNums;
    }

    public long getFailCaseNums() {
        return FailCaseNums;
    }

    public void setFailCaseNums(long failCaseNums) {
        FailCaseNums = failCaseNums;
    }

    public long getNotExecCaseNums() {
        return NotExecCaseNums;
    }

    public void setNotExecCaseNums(long notExecCaseNums) {
        NotExecCaseNums = notExecCaseNums;
    }

    public String getSlaverName() {
        return SlaverName;
    }

    public void setSlaverName(String slaverName) {
        SlaverName = slaverName;
    }

    public double getCosttime() {
        return costtime;
    }

    public void setCosttime(double costtime) {
        this.costtime = costtime;
    }

    private long CaseNum;
    private long Threadnums;
    private long Loops;
    private int Slavernums;

    private long ExecCaseNums;
    private long SuccessCaseNums;
    private long FailCaseNums;
    private long NotExecCaseNums;

    private String SlaverName;

    private double costtime;


}
