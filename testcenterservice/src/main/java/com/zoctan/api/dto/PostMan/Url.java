package com.zoctan.api.dto.PostMan;

import com.zoctan.api.dto.PostMan.Query;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 12:00
 */
@Setter
@Getter
public class Url {
    private String raw;
    private String protocol;
    private ArrayList<String> host;
    private ArrayList<String> path;
    private List<Query> query;
}
