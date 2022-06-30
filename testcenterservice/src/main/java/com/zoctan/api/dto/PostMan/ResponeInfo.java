package com.zoctan.api.dto.PostMan;

import com.zoctan.api.dto.PostMan.Header;
import com.zoctan.api.dto.PostMan.OriginalRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:15
 */
@Setter
@Getter
public class ResponeInfo {
    private String name;
    private OriginalRequest originalRequest;
    private String status;
    private String code;
    private String _postman_previewlanguage;
    private String body;
    private List<Header> header;
    private List<String> cookie;
}
