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


    private float CaseNum;
    private long Threadnums;
    private long Loops;
    private int Slavernums;

    private long ExecCaseNums;
    private float SuccessCaseNums;
    private float FailCaseNums;
    private long NotExecCaseNums;

    private String SlaverName;

    private double costtime;


}
