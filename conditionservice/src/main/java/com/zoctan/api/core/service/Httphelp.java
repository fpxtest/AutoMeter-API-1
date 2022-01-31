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
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Httphelp {

    /**
     * post方法
     *
     * @param url
     * @param header
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     * @postdata postdata
     */
    //HttpParamers paramers
    public static ResponeData PostWithUrlParams(String protocal, String url, String postdata, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData = new ResponeData();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = postdata;
            HttpPost httpPost = new HttpPost(url);

            //Post-url参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("idCard", "1234567890"));

            //url格式编码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(uefEntity);
            //Post-url参数

            httpPost.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpPost, header);
            }
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query, "utf-8");
                httpPost.setEntity(reqEntity);
            }
            if (protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }

            Httphelp.log.info( "..................Post请求地址 :  " + url);
            for (Header header1 : httpPost.getAllHeaders()) {
                Httphelp.log.info( "..................Post请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }
            Httphelp.log.info( "..................Post请求数据 :  " + query);


            httpResponse = httpClient.execute(httpPost);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Get Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Post 请求响应 is :" + responeData.getRespone());
        return responeData;
    }

    /**
     * post方法
     *
     * @param Url
     * @param header
     * @param connectTimeout
     * @return
     * @throws IOException
     * @postdata Postdata
     */
    //HttpParamers paramers
    public static ResponeData PostWithBody(String Protocal, String Url, String Postdata, HttpHeader header, int connectTimeout) throws Exception {
        ResponeData responeData = new ResponeData();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String Query = Postdata;
            HttpPost httpPost = new HttpPost(Url);
            httpPost.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpPost, header);
            }
            if (Query != null) {
                HttpEntity reqEntity = new StringEntity(Query, "utf-8");
                httpPost.setEntity(reqEntity);
            }
            if (Protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (Protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }
            Httphelp.log.info( "Post PostWithBody..................Post请求地址 :  " + Url);
            for (Header header1 : httpPost.getAllHeaders()) {
                Httphelp.log.info( "..................Post请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }
            Httphelp.log.info( "Post PostWithBody..................Post请求数据 :  " + Query);
            httpResponse = httpClient.execute(httpPost);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Post PostWithBody Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + Url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Post PostWithBody 请求响应 is :" + responeData.getRespone());
        return responeData;
    }


    /**
     * get方法
     *
     * @param Url
     * @param header
     * @param connectTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData GetWithBody(String Protocal, String Url, String PostData, HttpHeader header, int connectTimeout) throws Exception {
        ResponeData responeData;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = PostData;
            HttpGetWithEntity httpGetWithEntity = new HttpGetWithEntity(Url);
            HttpEntity httpEntity = new StringEntity(query, "utf-8");
            httpGetWithEntity.setEntity(httpEntity);
            httpGetWithEntity.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpGetWithEntity, header);
            }
            if (Protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (Protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }
            Httphelp.log.info( "Httphelp..................doGetWithBody请求地址 :  " + Url);
            for (Header header1 : httpGetWithEntity.getAllHeaders()) {
                Httphelp.log.info( "Httphelp..................doGetWithBody请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }
            httpResponse = httpClient.execute(httpGetWithEntity);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Httphelp doGetWithBody Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + Url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "doGetWithBody请求响应 :" + responeData.getRespone());
        return responeData;
    }

    //Get请求无参数
    public static ResponeData GetWithNoParams(String protocal, String url, HttpHeader header, int connectTimeout) throws Exception {
        ResponeData responeData;
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
            if (protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }

            Httphelp.log.info( "Httphelp..................GetWithNoParams请求地址 :  " + url);
            for (Header header1 : httpGet.getAllHeaders()) {
                Httphelp.log.info( "Httphelp..................GetWithNoParams请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }

            httpResponse = httpClient.execute(httpGet);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Httphelp GetWithNoParams Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Httphelp GetWithNoParams请求响应 :" + responeData.getRespone());
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
    public static ResponeData doGet(String protocal, String url, String PostData, String requestcontenttype, HttpHeader header, int connectTimeout, int readTimeout) throws Exception {
        ResponeData responeData = null;
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
            if (protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }

            Httphelp.log.info( "Httphelp..................Get请求地址 :  " + url);
            for (Header header1 : httpGet.getAllHeaders()) {
                Httphelp.log.info( "Httphelp..................Get请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }

            httpResponse = httpClient.execute(httpGet);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Httphelp Get Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Httphelp Get请求响应 :" + responeData.getRespone());
        return responeData;
    }


    /**
     * put方法
     *
     * @param url
     * @param header
     * @param connectTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData doPut(String protocal, String url, String PostData, HttpHeader header, int connectTimeout) throws Exception {
        ResponeData responeData = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = PostData;
            HttpPut httpPut = new HttpPut(url);
            httpPut.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpPut, header);
            }
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query, "utf-8");
                httpPut.setEntity(reqEntity);
            }
            if (protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }

            Httphelp.log.info( "Httphelp..................Put请求地址 :  " + url);
            for (Header header1 : httpPut.getAllHeaders()) {
                Httphelp.log.info( "..................Put请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }
            Httphelp.log.info( "Httphelp..................Put请求数据 :  " + query);

            httpResponse = httpClient.execute(httpPut);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Httphelp Put Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Httphelp Put responseData is :" + responeData.getRespone());
        return responeData;
    }

    /**
     * delete
     *
     * @param url
     * @param header
     * @param connectTimeout
     * @return
     * @throws IOException
     */
    public static ResponeData doDelete(String protocal, String url, String PostData, HttpHeader header, int connectTimeout) throws Exception {
        ResponeData responeData = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
                    .setSocketTimeout(connectTimeout).build();
            String query = PostData;
            HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
            httpDelete.setConfig(requestConfig);
            if (header.getParams().size() > 0) {
                setHeader(httpDelete, header);
            }
            if (query != null) {
                HttpEntity reqEntity = new StringEntity(query, "utf-8");
                httpDelete.setEntity(reqEntity);
            }
            if (protocal.equalsIgnoreCase("http")) {
                httpClient = HttpClients.createDefault();
            }
            if (protocal.equalsIgnoreCase("https")) {
                httpClient = new SSLClient();
            }

            Httphelp.log.info( "Httphelp..................Delete请求地址 :  " + url);
            for (Header header1 : httpDelete.getAllHeaders()) {
                Httphelp.log.info( "Httphelp..................Delete请求Header名 :  " + header1.getName() + "  Header值：" + header1.getValue());
            }
            Httphelp.log.info( "Httphelp..................Delete请求数据 :  " + query);
            httpResponse = httpClient.execute(httpDelete);
            responeData = GetResponeData(httpResponse);
        } catch (Exception e) {
            Httphelp.log.info( "Httphelp Delete Exception is :" + e.getMessage());
            throw new Exception("请求地址:" + url + " 发生异常，原因：" + e.getMessage());
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        Httphelp.log.info( "Delete响应 is :" + responeData.getRespone());
        return responeData;
    }

    private static void setHeader(HttpRequestBase httpRequestBase, HttpHeader header) {
        if (header != null) {
            //httpRequestBase.setHeader("Content-Length",  String.valueOf(Postdata.getBytes(StandardCharsets.UTF_8).length));
            Map<String, Object> headerMap = header.getParams();
            if (headerMap != null && !headerMap.isEmpty()) {
                Set<Map.Entry<String, Object>> entries = headerMap.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    String name = entry.getKey();
                    String value = entry.getValue().toString();
                    Httphelp.log.info( "Httphelp header name is :  " + name + " value is :  " + value);
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
        Httphelp.log.info( "buildGetUrl url is :  " + newUrl.toString());
        return newUrl.toString();
    }

    public static String GetRequestUrl(String url, String apistyle, HttpParamers paramsob) throws Exception {
        String requestUrl = "";
        String params="";// 编码之后的参数
        StringBuffer sb = new StringBuffer();// 存储参数
        requestUrl = url;
        Map<String, Object> parameters = paramsob.getParams();
        if (apistyle.equalsIgnoreCase("restful")) {
            if (!(url.contains("{") && url.contains("}"))) {
                throw new Exception("restfulapi-url:" + url + " 未包含{}参数");
            }
        }
        if (parameters.size() < 1) {
            if (apistyle.equalsIgnoreCase("restful")) {
                throw new Exception("restfulapi-未设置url:" + url + " 中的对应的参数和用例数据");
            } else {
                requestUrl = url;
            }
        } else if (parameters.size() == 1) {
            if (apistyle.equalsIgnoreCase("restful")) {
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
                        throw new Exception("Api的参数:" + name + "编码异常:" + e.getMessage());
                    }
                }
                params = sb.toString();
                requestUrl = url + "?" + params;
            }
        } else {
            if (apistyle.equalsIgnoreCase("restful")) {
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
                        sb.append(name).append("=").append(parameters.get(name)).append("&");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String tempParams = sb.toString();
                if(tempParams.length()>1)
                {
                    params = tempParams.substring(0, tempParams.length() - 1);
                }
                requestUrl = url + "?" + params;
            }
        }
        return requestUrl;
    }

    private static ResponeData GetResponeData(CloseableHttpResponse closeableHttpResponse) throws IOException {
        String ActualResult = "";
        String Content = "";
        int Code = 0;
        if (closeableHttpResponse != null) {
            Code = closeableHttpResponse.getStatusLine().getStatusCode();
            HttpEntity resEntity = closeableHttpResponse.getEntity();
            if (resEntity != null) {
                ActualResult = EntityUtils.toString(resEntity);
                Content = ActualResult;
            }
        }
        ResponeData responeData = new ResponeData();
        responeData.setRespone(ActualResult);
        responeData.setContent(Content);
        responeData.setCode(Code);
        return responeData;
    }

    private static String inputStreamToString(HttpEntity resEntity) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader inputStreamReader;
        InputStream inputStream = resEntity.getContent();
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

    public static void main(String[] args) {

    }

}

