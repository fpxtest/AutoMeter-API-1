package com.zoctan.api.dto.Swagger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hacker li
 * @since 08/02/2022 11:16
 */
@Setter
@Getter
public class schema {
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public items getItem() {
        return items;
    }

    public void setItem(items item) {
        this.items = item;
    }
    private String $ref;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;


    private items items;

}
