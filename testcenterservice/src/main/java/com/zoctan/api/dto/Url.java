package com.zoctan.api.dto;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 12:00
 */
public class Url {
    private String raw;
    private String protocol;
    private String host;
    private String path;
    private List<Query> query;
}
