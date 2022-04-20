package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class FunctionCaseSandF {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    private long  value;

}
