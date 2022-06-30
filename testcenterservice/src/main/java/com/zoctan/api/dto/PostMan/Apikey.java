package com.zoctan.api.dto.PostMan;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:16
 */
@Setter
@Getter
public class Apikey {
    private String key;
    private String value;
    private String type;
    private String in;
}
