package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @author hacker li
 * @since 08/02/2022 11:14
 */
@Setter
@Getter
public class MyCreateInfo {
    private int ApiNums;
    private int ApiFunctionCaseNums;
    private int ApiPerformanceCaseNums;
    private int ExecplanFunnums;
    private int ExecplanPerformancenums;
    private int TestConditions;
}
