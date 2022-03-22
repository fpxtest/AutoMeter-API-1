package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class FunctionSuccessRateStatis {

    public Long getSuccessRate() {
        return SuccessRate;
    }

    public void setSuccessRate(Long successRate) {
        SuccessRate = successRate;
    }

    public Long getFailRate() {
        return FailRate;
    }

    public void setFailRate(Long failRate) {
        FailRate = failRate;
    }

    private Long SuccessRate;
    private Long FailRate;

}
