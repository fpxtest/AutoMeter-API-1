package com.zoctan.api.dto;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:04
 */

public class PostmanApiInfoDto {
    public InfoDto getInfo() {
        return info;
    }

    public void setInfo(InfoDto info) {
        this.info = info;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    private InfoDto info;
    private List<Items> items;



}
