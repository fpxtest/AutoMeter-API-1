package com.zoctan.api.dto.Swagger;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class Delete {
    private List<String> tags;
    private String summary;
    private String description;
    private String operationId;
    private List<String> consumes;
    private List<String> produces;
    private List<parameters> parameters;
}
