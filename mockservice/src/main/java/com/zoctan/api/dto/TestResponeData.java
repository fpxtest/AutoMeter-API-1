package com.zoctan.api.dto;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.List;

public class TestResponeData {

    public List<Header> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<Header> headerList) {
        this.headerList = headerList;
    }

    public String getResponeContent() {
        return ResponeContent;
    }

    public void setResponeContent(String responeContent) {
        ResponeContent = responeContent;
    }

    private List<Header> headerList;

    public List<RequestHead> getRequestHeadList() {
        return requestHeadList;
    }

    public void setRequestHeadList(List<RequestHead> requestHeadList) {
        this.requestHeadList = requestHeadList;
    }

    private List<RequestHead> requestHeadList;


    private String ResponeContent;

    public int getResponeCode() {
        return ResponeCode;
    }

    public void setResponeCode(int responeCode) {
        ResponeCode = responeCode;
    }

    private int ResponeCode;

    public long getSize() {
        return Size;
    }

    public void setSize(long size) {
        Size = size;
    }

    public long getResponeTime() {
        return ResponeTime;
    }

    public void setResponeTime(long responeTime) {
        ResponeTime = responeTime;
    }

    private long Size;

    private long ResponeTime;

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    List<Cookie> cookies;


    public ResponeGeneral getResponeGeneral() {
        return responeGeneral;
    }

    public void setResponeGeneral(ResponeGeneral responeGeneral) {
        this.responeGeneral = responeGeneral;
    }

    private ResponeGeneral responeGeneral;

}


