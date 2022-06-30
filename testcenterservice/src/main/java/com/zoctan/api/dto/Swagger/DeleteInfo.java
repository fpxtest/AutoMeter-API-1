package com.zoctan.api.dto.Swagger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class DeleteInfo {
    private String urlpath;
    private Delete delete;
}
