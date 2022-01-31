import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.util.*;

public class testfun {
    public static void main(final String[] args) throws Exception {

        System.out.println(new Date());

        HashMap<String,Object>map=new HashMap<>();
        map.put("name","$season");
        map.put("age",123);

        String result="123";
        String[] test=result.split(",");

        Object[] strarr=test;
        map.put("agexx",strarr);
        String str= JSON.toJSON(map).toString();
        System.out.println(str);

        //int num= str.indexOf("$season");

        str=str.replace("$season","123");
        System.out.println(str);

        String Json="{\n" +
                " \"bukrs\":[12,3,4,5],\n" +
                " \"lines\":[\n" +
                "  {\n" +
                "   \"zamount\":\"10.0000\",\n" +
                "   \"ebelp\":\"10\",\n" +
                "   \"zspd\":\"WGAWS0077202101070016\"\n" +
                "  },\n" +
                "   {\n" +
                "   \"zamount\":\"10.0000\",\n" +
                "   \"ebelp\":\"10\",\n" +
                "   \"zspd\":\"WGAWS0077202101070016\"\n" +
                "  }\n" +
                " ],\n" +
                " \"lifnr\":\"GAWS0077\",\n" +
                " \"ekgrp\":\"901\"\n" +
                "}        ";
        JSON.isValid("");
        JSONObject jsonObject = JSON.parseObject(Json);
        JSONObject jsonObject1= jsonLoop(jsonObject);//        JSONObject jsonObject= JSONObject.parseObject(Json);
//
//        Iterator iter = jsonObject.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//            String Value=entry.getValue().toString();
//            System.out.println(Value);
//
//        }
        System.out.println("1111111111s");

//        try
//        {
//            String Result= JsonPath.read("{}","$.data");
//        }
//        catch (Exception ex)
//        {
//            System.out.println(ex.getMessage());
//            throw new Exception("exceptuon");
//        }
//        finally {
//            System.out.println("finally");
//
//        }


//        Calendar cal = Calendar.getInstance();
//
//        String CurrentTime = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + " " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":00";
//
//        //String CurrentTime = String.valueOf(cal.get(Calendar.YEAR)) + "-" + String.valueOf(cal.get(Calendar.MONTH)) + "-" + String.valueOf(cal.get(Calendar.DATE)) + " " + String.valueOf(cal.get(Calendar.HOUR)) + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":00";
//        if(CurrentTime.equals("2021-11-26 7:51:00"))
//        {
//            System.out.println(100000000);
//        }


//        final String json1 = "{\n" +
//                "\t\"code111\":200,\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"endRow\":1,\n" +
//                "\t\t\"hasNextPage\":false,\n" +
//                "\t\t\"list\":[\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t}\n" +
//                "\t\t],\n" +
//                "\t\t\"navigateFirstPage\":1,\n" +
//                "\t\t\"total\":1\n" +
//                "\t}\n" +
//                "}";
//        final String json2 = "{\n" +
//                "\t\"code\":200,\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"endRow\":1,\n" +
//                "\t\t\"hasNextPage\":false,\n" +
//                "\t\t\"list\":[\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"machinename\":\"测试服务器\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t},\n" +
//                "\t\t\t{\n" +
//                "\t\t\t\t\"yyyyyyyyyyyyy\":\"ooooooooo\",\n" +
//                "\t\t\t\t\"mem\":\"8G\"\n" +
//                "\t\t\t}\n" +
//                "\t\t],\n" +
//                "\t\t\"navigateFirstPage\":1,\n" +
//                "\t\t\"total\":1\n" +
//                "\t}\n" +
//                "}";
//        CustomComparator customComparator = new CustomComparator(JSONCompareMode.NON_EXTENSIBLE);
//        try {
//            JSONCompareResult result = JSONCompare.compareJSON(json1, json2, customComparator);
//
//            result.getFieldFailures().forEach(fieldComparisonFailure -> {
//                System.out.println(String.format("变更字段：%s，变更前值：%s，变更后值：%s",fieldComparisonFailure.getField(),fieldComparisonFailure.getExpected(),fieldComparisonFailure.getActual()));
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//


    }


    public static JSONObject jsonLoop(Object object) {
        JSONObject jsonObject=null;
        if (object instanceof JSONObject) {
             jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object o = entry.getValue();
                if (o instanceof String) {
                    System.out.println("key:" + entry.getKey() + "，value:" + entry.getValue());
                    if(entry.getKey().equals("ekgrp"))
                    {
                        jsonObject.put(entry.getKey(),"00000000");
                    }
                }

                if (o instanceof JSONArray) {
                    if(!o.toString().contains("{"))
                    {
                        System.out.println("key:" + entry.getKey() + "，value:" + entry.getValue());
                    }
                    else
                    {
                        jsonLoop(o);
                    }
                }
            }
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonLoop(jsonArray.get(i));
            }
        }
        return jsonObject;
    }



    public static void digui(String jsonO)
    {
        if(getJSONType(jsonO))
        {
            Map jsonObject= JSONObject.parseObject(jsonO);
            Iterator iter = jsonObject.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String Value=entry.getValue().toString();
                System.out.println(Value);
                digui(Value);

            }
        }

    }

    public static boolean getJSONType(String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            }
        }
        return result;
    }




}
