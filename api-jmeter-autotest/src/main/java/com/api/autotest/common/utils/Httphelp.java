package com.api.autotest.common.utils;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/

import com.api.autotest.dto.ResponeData;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log.Logger;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;
import java.util.Set;

public class Httphelp {
    public static Logger logger=null;

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String JSON_CONTENT_FORM = "application/json;charset=UTF-8";
    public static final String XML_CONTENT_FORM = "application/xml;charset=UTF-8";
    public static final String CONTENT_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

    public static ResponeData doService(String protocal,String url, String method,String apistyle, HttpParamers paramers,String postdata, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        switch (method) {
            case "get":
                String getrequesturl= getrequesturl(url, apistyle, paramers);
                return doGet(protocal, getrequesturl,requestcontenttype, header, connectTimeout, readTimeout);
            case "post":
                return doPost(protocal,url, postdata, requestcontenttype, header, connectTimeout, readTimeout);
            case "put":
                return doPut(protocal,url, paramers, requestcontenttype, header, connectTimeout, readTimeout);
            case "delete":
                return doDelete(protocal,url, paramers, requestcontenttype, header, connectTimeout, readTimeout);
        }
        return null;
    }

    /**
     * post方法
     *
     * @param url
     * @postdata postdata
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    //HttpParamers paramers
    public static ResponeData doPost(String protocal,String url, String postdata, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData=new ResponeData();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = postdata;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpPost, header);
            }
            if (requestcontenttype.equals("json")) {
                //json数据
                httpPost.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            }
            if (requestcontenttype.equals("xml")) {
                //xml数据
                httpPost.setHeader(HTTP.CONTENT_TYPE, XML_CONTENT_FORM);
                logger.info("Post xml datas is :  " + query);
            }
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query,"utf-8");
                httpPost.setEntity(reqEntity);
            }
            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }

            logger.info("..................Post请求地址 :  " + url);
            for (Header header1 :httpPost.getAllHeaders()) {
                logger.info("..................Post请求Header名 :  " + header1.getName()+"  Header值："+header1.getValue());
            }
            logger.info("..................Post请求数据 :  " + query);


            httpResponse = httpClient.execute(httpPost);
            responeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            logger.info("Get Exception is :" + e.getMessage());
            throw new Exception("请求地址:"+url+" 发生异常，原因："+e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        logger.info("Post 请求响应 is :" + responeData.getRespone());
        return responeData;
    }


    /**
     * get方法
     *
     * @param url
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData doGet(String protocal, String url, String requestcontenttype,  HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData=null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if (requestcontenttype.equals("json")) {
                //json数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            }
            if (requestcontenttype.equals("xml")) {
                //xml数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, XML_CONTENT_FORM);
            }
            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }

            logger.info("..................Get请求地址 :  " + url);
            for (Header header1 :httpGet.getAllHeaders()) {
                logger.info("..................Get请求Header名 :  " + header1.getName()+"  Header值："+header1.getValue());
            }

            httpResponse = httpClient.execute(httpGet);
            responeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            logger.info("Post Exception is :" + e.getMessage());
            throw new Exception("请求地址:"+url+" 发生异常，原因："+e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        logger.info("Get请求响应 :" + responeData.getRespone());
        return responeData;
    }


    /**
     * put方法
     *
     * @param url
     * @param params
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData doPut(String protocal,String url, HttpParamers params, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData=null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = null;
            url = buildGetUrl(url, query);
            HttpPut httpGet = new HttpPut(url);
            httpGet.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if (requestcontenttype.equals("json")) {
                //json数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            }
            if (requestcontenttype.equals("xml")) {
                //xml数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, XML_CONTENT_FORM);
            }
            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }

            logger.info("..................Put请求地址 :  " + url);
            for (Header header1 :httpGet.getAllHeaders()) {
                logger.info("..................Put请求Header名 :  " + header1.getName()+"  Header值："+header1.getValue());
            }
            logger.info("..................Put请求数据 :  " + query);


            httpResponse = httpClient.execute(httpGet);
            responeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            logger.info("Put Exception is :" + e.getMessage());
            throw new Exception("请求地址:"+url+" 发生异常，原因："+e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        logger.info("Put responseData is :" + responeData.getRespone());
        return responeData;
    }

    /**
     * delete
     *
     * @param url
     * @param params
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData doDelete(String protocal,String url, HttpParamers params, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData=null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = null;
            url = buildGetUrl(url, query);
            HttpDelete httpGet = new HttpDelete(url);
            httpGet.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if (requestcontenttype.equals("json")) {
                //json数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            }
            if (requestcontenttype.equals("xml")) {
                //xml数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, XML_CONTENT_FORM);
            }
            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }

            logger.info("..................Delete请求地址 :  " + url);
            for (Header header1 :httpGet.getAllHeaders()) {
                logger.info("..................Delete请求Header名 :  " + header1.getName()+"  Header值："+header1.getValue());
            }
            logger.info("..................Delete请求数据 :  " + query);
            httpResponse = httpClient.execute(httpGet);
            responeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            logger.info("Delete Exception is :" + e.getMessage());
            throw new Exception("请求地址:"+url+" 发生异常，原因："+e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        logger.info("Delete响应 is :" + responeData.getRespone());
        return responeData;
    }

    private static void setHeader(HttpRequestBase httpRequestBase, HttpHeader header) {
        if (header != null) {
            Map<String, String> headerMap = header.getParams();
            if (headerMap != null && !headerMap.isEmpty()) {
                Set<Map.Entry<String, String>> entries = headerMap.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    logger.info("header name is :  " + name+" value is :  "+value);
                    httpRequestBase.setHeader(name, value);
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
        logger.info("buildGetUrl url is :  " + newUrl.toString());
        return newUrl.toString();
    }

    public static String getrequesturl(String url, String apistyle, HttpParamers paramsob) throws Exception {
        String requestUrl = "";
        String params;// 编码之后的参数
        StringBuffer sb = new StringBuffer();// 存储参数
        requestUrl = url;
        Map<String, String> parameters = paramsob.getParams();
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
                        requestUrl = requestUrl.replace("{" + name + "}", parameters.get(name));
                    }
                }
            } else {
                for (String name : parameters.keySet()) {
                    try {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name),
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
                        requestUrl = requestUrl.replace("{" + name + "}", parameters.get(name));
                    }
                }
            } else {
                for (String name : parameters.keySet()) {
                    try {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name),
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


    private static ResponeData GetResponeData(CloseableHttpResponse closeableHttpResponse) throws IOException {
        String ActualResult="";
        String Content="";
        int Code=0;
        if(closeableHttpResponse!=null)
        {
            Code =closeableHttpResponse.getStatusLine().getStatusCode();
            HttpEntity resEntity = closeableHttpResponse.getEntity();
            if(resEntity!=null)
            {
                ActualResult = EntityUtils.toString(resEntity);
                Content=ActualResult;
            }
        }
        ResponeData responeData=new ResponeData();
        responeData.setRespone(ActualResult);
        responeData.setContent(Content);
        responeData.setCode(Code);
        return  responeData;
    }

    private static String inputStreamToString(HttpEntity resEntity) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader inputStreamReader;
        InputStream inputStream= resEntity.getContent();
        inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return buffer.toString();
    }
}

