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
public class ApiInfo {
    private String name;
    private RequestInfo request;
    private ArrayList<String> response;
}
