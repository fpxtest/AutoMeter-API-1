package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:57
 */
@Setter
@Getter
public class Body {
    private String mode;
    private String raw;
    private  options options;
    private List<Formdata> formdata;
    private List<Urlencoder> urlencoded;
}
