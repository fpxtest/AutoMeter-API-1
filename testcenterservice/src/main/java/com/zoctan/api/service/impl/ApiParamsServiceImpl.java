package com.zoctan.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.mapper.ApiParamsMapper;
import com.zoctan.api.service.ApiParamsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;


/**
* @author Zoctan
* @date 2020/05/20
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiParamsServiceImpl extends AbstractService<ApiParams> implements ApiParamsService {
@Resource
private ApiParamsMapper apiParamsMapper;

    @Override
    public List<ApiParams> findApiParamsWithName(Map<String, Object> params) {
        return this.apiParamsMapper.findApiParamsWithName(params);
    }

    @Override
    public List<ApiParams> getApiParamsbyname(Map<String, Object> params) {
        return this.apiParamsMapper.getApiParamsbyname(params);
    }

    @Override
    public List<ApiParams> getApiParamsbyapiid(Map<String, Object> params) {
        return this.apiParamsMapper.getApiParamsbyapiid(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public void SaveApiParams(ApiParams params) {
//        String JsonKey=params.getKeyname();
//        if(JSON.isValidObject(JsonKey))
//        {
//            String  Result= GetJsonValue(JsonKey,"","");
//            Result=Result.substring(0,Result.length()-1);
//            System.out.println("Result......................is..Result:"+Result);
//            params.setKeyname(Result);
//        }
        apiParamsMapper.SaveApiParams(params);
    }

    @Override
    public List<ApiParams> getApiParamsbypropertytype(Long apiid, String propertytype) {
        return apiParamsMapper.getApiParamsbypropertytype(apiid,propertytype);
    }

    @Override
    public List<ApiParams> findApiParamsbypropertytype(Long apiid, String propertytype) {
        return apiParamsMapper.findApiParamsbypropertytype(apiid, propertytype);
    }

    @Override
    public ApiParams getBodyNoFormbyapiid(Long apiid, String propertytype, String keydefaultvalue,String keytype) {
        return apiParamsMapper.getBodyNoFormbyapiid(apiid, propertytype, keydefaultvalue,keytype);
    }

    @Override
    public void updateApiParams(ApiParams params) {
//        String JsonKey=params.getKeyname();
//        if(JSON.isValidObject(JsonKey))
//        {
//            String  Result= GetJsonValue(JsonKey,"","");
//            Result=Result.substring(0,Result.length()-1);
//            System.out.println("Result......................is..Result:"+Result);
//            params.setKeyname(Result);
//            params.setKeynamebak(JsonKey);
//        }
        apiParamsMapper.updateApiParams(params);
    }

    @Override
    public void deletebyApiid(Long apiid) {
        apiParamsMapper.deletebyApiid(apiid);
    }

    private String GetJsonValue(String JsonValue,String Key,String Parent)
    {
        JSONObject jsonObject= JSON.parseObject(JsonValue);
        for (Map.Entry entry : jsonObject.entrySet()) {
            String Value="";
            if(entry.getValue()!=null)
            {
                Value=entry.getValue().toString();
            }
            if(JSON.isValidObject(Value))
            {
                String ChildKey="";
                if(!Parent.isEmpty())
                {
                    ChildKey=Parent+entry.getKey().toString()+".";
                }
                else
                {
                    ChildKey=entry.getKey().toString()+".";
                }
                Key=Key+GetJsonValue(Value,"",ChildKey);
            }
            else
            {
                Key=Key+Parent+entry.getKey().toString()+",";
            }
        }
        return Key;
    }
}
