package com.zoctan.api.dto.Swagger;

import com.zoctan.api.dto.PostMan.Auth;
import com.zoctan.api.dto.PostMan.Body;
import com.zoctan.api.dto.PostMan.Header;
import com.zoctan.api.dto.PostMan.Url;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class Post {
    private List<String> tags;
    private String summary;
    private String description;
    private String operationId;
    private List<String> consumes;
    private List<String> produces;
    private List<parameters> parameters;
}
