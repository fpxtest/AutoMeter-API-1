package com.zoctan.api.dto.PostMan;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 12:02
 */
@Setter
@Getter
public class Query {
    private String key;
    private String value;
    private String description;
}
