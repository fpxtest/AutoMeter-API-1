package com.zoctan.api.dto.PostMan;

import com.zoctan.api.dto.ResponeHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class OriginalRequest {
    private String method;
    private List<ResponeHeader> header;
    private Url url;
}
