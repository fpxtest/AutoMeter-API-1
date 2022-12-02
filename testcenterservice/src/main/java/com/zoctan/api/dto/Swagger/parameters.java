package com.zoctan.api.dto.Swagger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:16
 */
@Setter
@Getter
public class parameters {
    private String name;
    private String in;
    private String description;
    private String required;
    private String type;
    private String format;
    private schema schema;
}
