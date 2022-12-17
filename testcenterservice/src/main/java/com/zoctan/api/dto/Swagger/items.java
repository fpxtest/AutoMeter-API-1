package com.zoctan.api.dto.Swagger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:16
 */
@Setter
@Getter
public class items {
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    private String $ref;
}
