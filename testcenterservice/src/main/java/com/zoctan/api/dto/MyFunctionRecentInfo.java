package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:14
 */
@Setter
@Getter
public class MyFunctionRecentInfo {
    private String Execplanname;
    private String Batchname;
    private long Totalcasenums;
    private long Totalsuccessnums;
    private long Totalfailnums;
    private long Totalunexecnums;
}
