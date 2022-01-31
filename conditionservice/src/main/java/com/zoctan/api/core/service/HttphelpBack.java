package com.zoctan.api.core.service;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/

import com.zoctan.api.dto.TestResponeData;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttphelpBack {
    //public static //logger //logger=null;

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String JSON_CONTENT_FORM = "application/json;charset=UTF-8";
    public static final String XML_CONTENT_FORM = "application/xml;charset=UTF-8";
    public static final String CONTENT_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

    public static TestResponeData doService(String protocal, String url, String method, String apistyle, HttpParamers paramers, String postdata, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        switch (method) {
            case "get":
                String getrequesturl= getrequesturl(url, apistyle, paramers);
                return doGet(protocal, getrequesturl,  header, connectTimeout, readTimeout);
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
    public static TestResponeData doPost(String protocal,String url, String postdata, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        String responseData = "";
        TestResponeData testResponeData=new TestResponeData();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = postdata;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            if (header.getParams().size() > 0) {
                setHeader(httpPost, header);
            }
            if (requestcontenttype.equals(new String("json"))) {
                //json数据
                httpPost.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
            }
            if (requestcontenttype.equals(new String("xml"))) {
                //xml数据
                httpPost.setHeader(HTTP.CONTENT_TYPE, XML_CONTENT_FORM);
                HttphelpBack.log.info("Post xml datas is :  " + query);
            }
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query,"utf-8");
                HttphelpBack.log.info("Post last datas is :  " + query);
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
            httpResponse = httpClient.execute(httpPost);
            List<Cookie> cookies = cookieStore.getCookies();
            testResponeData.setCookies(cookies);
            testResponeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            HttphelpBack.log.info("Post Exception is :" + e.getMessage());
            throw new Exception("请求"+url+"发生异常，异常原因："+e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        HttphelpBack.log.info("Post responseData is :" + responseData);
        return testResponeData;
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
    public static TestResponeData doGet(String protocal, String url,  HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        String responseData = "";
        TestResponeData testResponeData=new TestResponeData();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            HttphelpBack.log.info("Get datas is :  " + url);
            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if(protocal.equals(new String("http")))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals(new String("https")))
            {
                httpClient = new SSLClient();
            }
            httpResponse = httpClient.execute(httpGet);

            List<Cookie> cookies = cookieStore.getCookies();
            testResponeData.setCookies(cookies);

            testResponeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            responseData = e.getMessage();
            HttphelpBack.log.info("Exception is :" + e.getMessage());
            throw new Exception("请求"+url+"发生异常，异常原因："+responseData);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        HttphelpBack.log.info("Get responseData is :" + responseData);
        return testResponeData;
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
    public static TestResponeData doPut(String protocal,String url, HttpParamers params, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        String responseData = "";
        TestResponeData testResponeData=new TestResponeData();
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

            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if (requestcontenttype.equals(new String("json"))) {
                //json数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
                query = params.getJsonParamer();
                HttphelpBack.log.info("Put json datas is :  " + query);
            } else {
                //表单数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, CONTENT_FORM);
                query = params.getQueryString(DEFAULT_CHARSET);
                HttphelpBack.log.info("Put json datas is :  " + query);
            }
            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }
            httpResponse = httpClient.execute(httpGet);

            List<Cookie> cookies = cookieStore.getCookies();
            testResponeData.setCookies(cookies);
            testResponeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            HttphelpBack.log.info("Put Exception is :" + e.getMessage());
            throw new Exception("请求"+url+"发生异常，异常原因："+responseData);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        HttphelpBack.log.info("Put responseData is :" + responseData);
        return testResponeData;
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
    public static TestResponeData doDelete(String protocal,String url, HttpParamers params, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        String responseData = "";
        TestResponeData testResponeData=new TestResponeData();
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

            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

            if (header.getParams().size() > 0) {
                setHeader(httpGet, header);
            }
            if (requestcontenttype.equals(new String("json"))) {
                //json数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_FORM);
                query = params.getJsonParamer();
                HttphelpBack.log.info("Delete json datas is :  " + query);
            } else {
                //表单数据
                httpGet.setHeader(HTTP.CONTENT_TYPE, CONTENT_FORM);
                query = params.getQueryString(DEFAULT_CHARSET);
                HttphelpBack.log.info("Delete json datas is :  " + query);
            }

            if(protocal.equals("http"))
            {
                httpClient = HttpClients.createDefault();
            }
            if(protocal.equals("https"))
            {
                httpClient = new SSLClient();
            }
            httpResponse = httpClient.execute(httpGet);

            List<Cookie> cookies = cookieStore.getCookies();
            testResponeData.setCookies(cookies);
            testResponeData=GetResponeData(httpResponse);
        } catch (Exception e) {
            HttphelpBack.log.info(" Delete Exception is :" + e.getMessage());
            throw new Exception("请求"+url+"发生异常，异常原因："+responseData);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        HttphelpBack.log.info("Delete responseData is :" + responseData);
        return testResponeData;
    }

    private static void setHeader(HttpRequestBase httpRequestBase, HttpHeader header) {
        if (header != null) {
            Map<String, Object> headerMap = header.getParams();
            if (headerMap != null && !headerMap.isEmpty()) {
                Set<Map.Entry<String, Object>> entries = headerMap.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    String name = entry.getKey();
                    String value = entry.getValue().toString();
                    HttphelpBack.log.info("header name is :  " + name+" value is :  "+value);
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
        HttphelpBack.log.info("buildGetUrl url is :  " + newUrl.toString());
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

    private static TestResponeData GetResponeData(CloseableHttpResponse closeableHttpResponse) throws IOException {
        String ActualResult="";
        TestResponeData responeData=new TestResponeData();
        int Code=0;
        if(closeableHttpResponse!=null)
        {
            Code =closeableHttpResponse.getStatusLine().getStatusCode();
            HttpEntity resEntity = closeableHttpResponse.getEntity();
            if(resEntity!=null)
            {
                ActualResult = EntityUtils.toString(resEntity);
                responeData.setSize(resEntity.getContentLength());
            }
        }
        responeData.setResponeContent(ActualResult);
        responeData.setResponeCode(Code);
        responeData.setHeaderList(Arrays.asList(closeableHttpResponse.getAllHeaders()));
        return  responeData;
    }
}
