package com.zoctan.api.dto.PostMan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class RequestInfo {
    private String method;
    private List<Header> header;
    private Body body;
    private Auth auth;
    private Url url;
    private String description;
}
