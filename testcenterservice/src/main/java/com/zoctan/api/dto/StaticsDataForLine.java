package com.zoctan.api.dto;

import java.util.List;

public class StaticsDataForLine {

    public List<Double> getPassPecent() {
        return PassPecent;
    }

    public void setPassPecent(List<Double> passPecent) {
        PassPecent = passPecent;
    }

    public String getExecPlanName() {
        return ExecPlanName;
    }

    public void setExecPlanName(String execPlanName) {
        ExecPlanName = execPlanName;
    }

    private List<Double> PassPecent;
    private String ExecPlanName;
}
