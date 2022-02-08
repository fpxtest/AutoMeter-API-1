package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:04
 */
@Getter
@Setter
public class PostmanApiInfoDto {
    private InfoDto info;
    private List<Items> items;
}
