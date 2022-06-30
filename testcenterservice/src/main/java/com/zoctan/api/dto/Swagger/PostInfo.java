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
public class PostInfo {
    private String urlpath;
    private Post post;
}
