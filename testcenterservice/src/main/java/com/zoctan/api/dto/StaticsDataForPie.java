package com.zoctan.api.dto;

public class StaticsDataForPie {
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long value;
    private String name;
}
