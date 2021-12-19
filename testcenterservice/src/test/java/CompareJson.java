
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;

public class CompareJson {

    private static final String SysRoot = "sys_root";
    private static final String SysType = "sys_type";
    private static final String SysObj = "sys_obj";
    private static final String SysNew = "sys_new";
    private static final String SysOld = "sys_old";
    private static final String TypeNew = "new";
    private static final String TypeDelete = "delete";
    private static final String TypeDifference = "difference";

    private String itemKey;
    private List<String> ignoreKeys = new ArrayList<>();

    public CompareJson(String itemKey) {
        this.itemKey = itemKey;
    }

    public CompareJson(String itemKey, String ignoreKeys) {
        this.itemKey = itemKey;
        this.ignoreKeys = Arrays.asList(ignoreKeys.split("\\,"));
    }

    public static void main(String[] args) {
        final String json1 = "{\n" +
                " \"legalPersonPhone\":\"\",\n" +
                " \"age\":32\n" +
                "}";
        final String json2 = "{\n" +
                " \"legalPersonPhone\":\"\",\n" +
                " \"featureCode\":\"ZXJA\",\n" +
                " \"age\":31\n" +
                "}";
        String resultStr = new CompareJson("age").compareJson(json1, json2);
        System.out.println(resultStr);
    }

    private void compareJson(JSONObject jsonObject1, JSONObject jsonObject2, Map<String, Object> objectMap) {
        Iterator<String> iterator = jsonObject1.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (ignoreKeys.contains(key)) {
                continue;
            }

            Object value1 = jsonObject1.get(key);
            Object value2 = jsonObject2.get(key);
            compareJson(key, value1, value2, objectMap);
        }
    }

    private void compareJson(JSONArray jsonArray1, JSONArray jsonArray2, List<Map<String, Object>> arrayMap) {

        JSONArray jsonArray = (JSONArray) jsonArray1.clone();
        if (jsonArray2 != null) {
            jsonArray.addAll(jsonArray2);
        }

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Object keyValue = jsonObject.get(this.itemKey);
            if (keyValue == null) {
                continue;
            }

            JSONObject jsonObject1 = null;
            for (int j = 0; j < jsonArray1.size(); j++) {
                JSONObject jsonObj = (JSONObject) jsonArray1.get(j);
                if (keyValue.equals(jsonObj.get(this.itemKey))) {
                    jsonObject1 = jsonObj;
                    break;
                }
            }

            JSONObject jsonObject2 = null;
            for (int j = 0; j < jsonArray2.size(); j++) {
                JSONObject jsonObj = (JSONObject) jsonArray2.get(j);
                if (keyValue.equals(jsonObj.get(this.itemKey))) {
                    jsonObject2 = jsonObj;
                    break;
                }
            }

            Map<String, Object> objectMap = new HashMap<>();
            if (jsonObject1 != null && jsonObject2 == null) {
                objectMap.put(this.itemKey, keyValue);
                objectMap.put(SysType, TypeNew);
                objectMap.put(SysObj, jsonObject1);
            } else if (jsonObject1 == null && jsonObject2 != null) {
                objectMap.put(this.itemKey, keyValue);
                objectMap.put(SysType, TypeDelete);
                objectMap.put(SysObj, jsonObject2);
            } else {
                Map<String, Object> differenceMap = new HashMap<>();
                compareJson(jsonObject1, jsonObject2, differenceMap);
                if (differenceMap.size() > 0) {
                    objectMap.put(this.itemKey, keyValue);
                    objectMap.put(SysType, TypeDifference);
                    objectMap.put(SysObj, differenceMap);
                }
            }

            if (objectMap.size() > 0) {

                Map<String, Object> findMap = null;
                for (Map<String, Object> map : arrayMap) {
                    if (keyValue.equals(map.get(this.itemKey))) {
                        findMap = map;
                        break;
                    }
                }

                if (findMap == null) {
                    arrayMap.add(objectMap);
                }
            }
        }
    }

    private void compareJson(String key, Object json1, Object json2, Map<String, Object> resultMap) {
        if (json1 instanceof JSONObject) {

            Map<String, Object> objectMap = new HashMap<>();
            compareJson((JSONObject) json1, (JSONObject) json2, objectMap);
            if (objectMap.size() > 0) {
                resultMap.put(key, objectMap);
            }

        } else if (json1 instanceof JSONArray) {

            JSONArray jsonArray = (JSONArray) json1;
            if (jsonArray != null && jsonArray.size() > 0) {
                if (!(jsonArray.get(0) instanceof JSONObject)) { //["1","2"],[1,2]...
                    Map<String, Object> compareMap = new HashMap<>();
                    compareMap.put(SysNew, json1);
                    compareMap.put(SysOld, json2);
                    resultMap.put(key, compareMap);
                    return;
                }
            }

            List<Map<String, Object>> arrayMap = new ArrayList<>();
            compareJson((JSONArray) json1, (JSONArray) json2, arrayMap);
            if (arrayMap.size() > 0) {
                resultMap.put(key, arrayMap);
            }

        } else if ((json1 == null && json2 != null) || (json1 != null && !json1.equals(json2))) {
            Map<String, Object> compareMap = new HashMap<>();
            compareMap.put(SysNew, json1);
            compareMap.put(SysOld, json2);
            resultMap.put(key, compareMap);
        }
    }

    public String compareJson(String json1, String json2) {
        Object jsonObj1 = JSONObject.parse(json1);
        Object jsonObj2 = JSONObject.parse(json2);
        Map<String, Object> resultMap = new HashMap<>();
        compareJson(SysRoot, jsonObj1, jsonObj2, resultMap);
        String resultStr = JSON.toJSONString(resultMap.get(SysRoot), new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
        return resultStr;
    }
}
