package com.zoctan.api.dto.PostMan;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @author hacker li
 * @since 08/02/2022 11:57
 */
@Setter
@Getter
public class Auth {
    private String type;
        private ArrayList<Apikey> apikey;
}
