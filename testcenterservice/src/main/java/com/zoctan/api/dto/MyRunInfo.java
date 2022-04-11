package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:14
 */
@Setter
@Getter
public class MyRunInfo {
    private long Funtotalcasenums;
    private long Funtotalpasscasenums;
    private long Funtotalfailcasenums;
    private float Funtotalcosttime;
    private long Pertotalcasenums;
    private long Pertotalpasscasenums;
    private long Pertotalfailcasenums;
    private float Pertotalcosttime;
}
