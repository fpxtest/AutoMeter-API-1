package com.zoctan.api.core.service;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttphelpB1 {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String JSON_CONTENT_FORM = "application/json;charset=UTF-8";
    public static final String CONTENT_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

    public static String doService(String url,String method, String paramers, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        switch (method) {
            case "get":
                //return doGet(url, paramers, header, connectTimeout, readTimeout);
            case "post":
                return doPost(url, paramers, header, connectTimeout, readTimeout);
        }
        return null;
    }

    /**
     * post方法
     *
     * @param url
     * @param paramers
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String paramers, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        String responseData = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = null;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if(header.getParams().size()>0)
            {
                setHeader(httpPost, header);
            }
            //json数据
            httpPost.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            query = paramers;
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query,"utf-8");
                httpPost.setEntity(reqEntity);
            }
            httpClient = HttpClients.custom().build();// .createDefault();
            HttphelpB1.log.info("start......................................................");
            httpResponse = httpClient.execute(httpPost);
            HttphelpB1.log.info("end......................................................");
            HttpEntity resEntity = httpResponse.getEntity();
            responseData = EntityUtils.toString(resEntity);
        } catch (Exception e) {
            System.out.println("post Exception is :"+e.getMessage());
            responseData=e.getMessage();
            throw new Exception(e.getMessage());
            //e.printStackTrace();
        } finally {
            if(httpResponse!=null)
            {
                httpResponse.close();
            }
            if(httpClient!=null) {
                httpClient.close();
            }
        }
        System.out.println("post responseData is :"+responseData);
        return responseData;
    }


    /**
     * get方法
     *
     * @param url
     * @param params
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doGet(String url, HttpParamers params, HttpHeader header, int connectTimeout, int readTimeout) throws IOException {
        String responseData = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            String query ="";// params.getQueryString(DEFAULT_CHARSET);
            url = buildGetUrl(url, query);
            HttpGet httpGet = new HttpGet(url);
            if(header.getParams().size()>0)
            {
                setHeader(httpGet, header);
            }
            httpClient = HttpClients.createDefault();
            httpResponse = httpClient.execute(httpGet);
            HttpEntity resEntity = httpResponse.getEntity();
            responseData = EntityUtils.toString(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(httpResponse!=null)
            {
                httpResponse.close();
            }
            if(httpClient!=null) {
                httpClient.close();
            };
        }
        return responseData;
    }

    private static void setHeader(HttpRequestBase httpRequestBase, HttpHeader header) {
        if (header != null) {
            Map<String, Object> headerMap = header.getParams();
            if (headerMap != null && !headerMap.isEmpty()) {
                Set<Map.Entry<String, Object>> entries = headerMap.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    String name = entry.getKey();
                    Object value = entry.getValue();
                    //httpRequestBase.setHeader(name, value);
                }
            }
        }
    }

    private static String buildGetUrl(String url, String query) throws IOException {
        if (query == null || query.equals("")) {
            return url;
        }
        StringBuilder newUrl = new StringBuilder(url);
        boolean hasQuery = url.contains("?");
        boolean hasPrepend = (url.endsWith("?")) || (url.endsWith("&"));
        if (!hasPrepend) {
            if (hasQuery) {
                newUrl.append("&");
            } else {
                newUrl.append("?");
                hasQuery = true;
            }
        }
        newUrl.append(query);
        hasPrepend = false;
        return newUrl.toString();
    }

    public static String getrequesturl(String url, String apistyle, HttpParamers paramsob) throws Exception {
        String requestUrl = "";
        String params;// 编码之后的参数
        StringBuffer sb = new StringBuffer();// 存储参数
        requestUrl = url;
        Map<String, Object> parameters = paramsob.getParams();
        if (apistyle.equals(new String("restful"))) {
            if (!(url.contains("{") && url.contains("}"))) {
                throw new Exception("restfulapi-url:" + url + " 未包含{}参数");
            }
        }
        if (parameters.size() < 1) {
            if (apistyle.equals(new String("restful"))) {
                throw new Exception("restfulapi-未设置url:" + url + " 中的对应的参数和用例数据");
            } else {
                requestUrl = url;
            }
        } else if (parameters.size() == 1) {
            if (apistyle.equals(new String("restful"))) {
                for (String name : parameters.keySet()) {
                    if (!url.contains("{" + name + "}")) {
                        throw new Exception("Api的参数:" + name + "和url:" + url + "中的参数不匹配");
                    } else {
                        requestUrl = requestUrl.replace("{" + name + "}", parameters.get(name).toString());
                    }
                }
            } else {
                for (String name : parameters.keySet()) {
                    try {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name).toString(),
                                        "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                params = sb.toString();
                requestUrl = url + "?" + params;
            }
        } else {
            if (apistyle.equals(new String("restful"))) {
                for (String name : parameters.keySet()) {
                    if (!url.contains("{" + name + "}")) {
                        throw new Exception("Api的参数:" + name + "和url:" + url + "中的参数不匹配");
                    } else {
                        requestUrl = requestUrl.replace("{" + name + "}", parameters.get(name).toString());
                    }
                }
            } else {
                for (String name : parameters.keySet()) {
                    try {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name).toString(),
                                        "UTF-8")).append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                String tempParams = sb.toString();
                params = tempParams.substring(0, tempParams.length() - 1);
                requestUrl = url + "?" + params;
            }
        }
        return requestUrl;
    }
}
