package com.zoctan.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.*;
import com.zoctan.api.entity.Api;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.service.ApiParamsService;
import com.zoctan.api.service.ApiService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Zoctan
 * @date 2020/04/24
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @Resource
    private ApiService apiService;
    @Resource
    private ApiParamsService apiParamsService;


    @PostMapping("/exportpostman")
    public Result exportpostman(@RequestParam("file") MultipartFile multipartFile,@RequestParam("deployid") String deployid,@RequestParam("deployunitname") String deployunitname,@RequestParam("apistyle") String apistyle,@RequestParam("creator") String creator) {
        try {
            if (multipartFile.isEmpty()) {
                return ResultGenerator.genFailedResult("上传的文件为空，请检查");
            }
            Long deployunitid=Long.parseLong(deployid);
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            File file = new File(fileName);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            String jsonString = FileUtils.readFileToString(file, "UTF-8");
            if (".json".equals(suffixName) || ".txt".equals(suffixName)) {
                Gson gson = new Gson();
                Map<String, Object> jsonmap = gson.fromJson(jsonString, Map.class);
                String GroupJson = gson.toJson(jsonmap.get("item"));
                List<String> resu = GetJson(GroupJson);
                for (String apiinfojson : resu) {
                    Type apiinfoType = new TypeToken<ArrayList<ApiInfo>>() {
                    }.getType();
                    Map<String, Object> apiinfomap = gson.fromJson(apiinfojson, Map.class);
                    System.out.println("GroupName:" + apiinfomap.get("name"));
                    String apiJson = gson.toJson(apiinfomap.get("item"));
                    List<ApiInfo> apiInfoList = gson.fromJson(apiJson, apiinfoType);
                    List<Api>apiList=new ArrayList<>();
                    for (ApiInfo apiInfo : apiInfoList) {

                        String AllPath = "";
                        if(apiInfo.getRequest().getUrl()!=null)
                        {
                            ArrayList<String> UrlPath = apiInfo.getRequest().getUrl().getPath();
                            for (String path : UrlPath) {
                                AllPath = AllPath + "/" + path;
                            }
                        }
                        String VisitType= apiInfo.getRequest().getMethod();
                        //取到postman第一个接口名做为api名
                        String ApiName=apiInfo.getName();

                        System.out.println("APIName:" + ApiName);
                        Api api = new Api();
                        api.setVisittype(VisitType);
                        api.setApiname(ApiName);
                        api.setApistyle(apistyle);
                        String RequestCT="Form表单";
                        if(apiInfo.getRequest().getBody()!=null)
                        {
                            if(apiInfo.getRequest().getBody().getOptions()!=null) {
                                RequestCT=apiInfo.getRequest().getBody().getOptions().getRaw().getLanguage();
                            }
                        }


                        api.setRequestcontenttype(RequestCT);
                        api.setDeployunitid(deployunitid);
                        api.setDeployunitname(deployunitname);
                        api.setCreator(creator);
                        api.setCreateTime(new Date());
                        api.setLastmodifyTime(new Date());
                        api.setPath(AllPath);


                        Condition con = new Condition(Api.class);
                        con.createCriteria().andCondition("path = '" + api.getPath() + "'")
                                .andCondition("visittype = '" + api.getVisittype()+ "'").andCondition("deployunitid = "+api.getDeployunitid());
                        if (apiService.ifexist(con) < 0) {
                            apiService.save(api);
                            Long Apiid= api.getId();
                            //保存api参数
                            //1.保存Header参数
                            List<Header> headerList=apiInfo.getRequest().getHeader();
                            List<ApiParams>apiHeaderParamsList=new ArrayList<>();
                            for (Header header:headerList) {
                                ApiParams apiParams= GetApiParam(Apiid,ApiName,creator,deployunitid,deployunitname,header.getKey(),header.getValue(),"","Header");
                                apiHeaderParamsList.add(apiParams);
                            }
                            if(apiHeaderParamsList.size()>0)
                            {
                                apiParamsService.save(apiHeaderParamsList);
                            }
                            //2.保存Params参数
                            List<ApiParams>apiParamsList=new ArrayList<>();
                            if(apiInfo.getRequest().getUrl()!=null)
                            {
                                List<Query> queryList=apiInfo.getRequest().getUrl().getQuery();
                                for (Query query:queryList) {
                                    ApiParams apiParams= GetApiParam(Apiid,ApiName,creator,deployunitid,deployunitname,query.getKey(),query.getValue(),"String","Params");
                                    apiParamsList.add(apiParams);
                                }
                                if(apiParamsList.size()>0)
                                {
                                    apiParamsService.save(apiParamsList);
                                }
                            }
                            //3.保存Body参数
                            if(apiInfo.getRequest().getBody()!=null)
                            {
                                String mode=apiInfo.getRequest().getBody().getMode();
                                if(mode.equalsIgnoreCase("raw"))
                                {
                                    String BodyKeyValue=apiInfo.getRequest().getBody().getRaw();
                                    String KeyType=apiInfo.getRequest().getBody().getOptions().getRaw().getLanguage();
                                    ApiParams apiParams= GetApiParam(Apiid,ApiName,creator,deployunitid,deployunitname,BodyKeyValue,"NoForm",KeyType,"Body");
                                    apiParamsService.save(apiParams);
                                }
                                if(mode.equalsIgnoreCase("urlencoded"))
                                {
                                    List<ApiParams>apiBodyParamsList=new ArrayList<>();
                                    List<Header> queryList=apiInfo.getRequest().getBody().getUrlencoded();
                                    for (Header query:queryList) {
                                        ApiParams apiParams= GetApiParam(Apiid,ApiName,creator,deployunitid,deployunitname,query.getKey(),query.getValue(),"String","Body");
                                        apiBodyParamsList.add(apiParams);
                                    }
                                    if(apiBodyParamsList.size()>0)
                                    {
                                        apiParamsService.save(apiBodyParamsList);
                                    }
                                }
                                if(mode.equalsIgnoreCase(""))
                                {}
                                if(mode.equalsIgnoreCase(""))
                                {}
                            }

                            //保存用例

                        }
                        else
                        {
                            //保存用例

                        }




                        apiList.add(api);
                    }
                    apiService.save(apiList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.genOkResult();
    }

    private List<String> GetJson(String itemJson) {
        List<String> result = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(itemJson);

        JsonArray jsonArray = null;
        if (el.isJsonArray()) {
            jsonArray = el.getAsJsonArray();
        }

        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JsonElement element = (JsonElement) it.next();
            result.add(element.toString());
        }
        return result;
    }

    private ApiParams GetApiParam(Long Apiid,String ApiName,String creator,Long deployunitid,String deployunitname,String keyname,String Keydefaultvalue,String Keytype,String ParamType)
    {
        ApiParams apiParams=new ApiParams();
        apiParams.setApiid(Apiid);
        apiParams.setApiname(ApiName);
        apiParams.setCreator(creator);
        apiParams.setDeployunitid(deployunitid);
        apiParams.setDeployunitname(deployunitname);
        apiParams.setCreateTime(new Date());
        apiParams.setLastmodifyTime(new Date());
        apiParams.setKeytype(ParamType);
        apiParams.setKeyname(keyname);
        apiParams.setKeydefaultvalue(Keydefaultvalue);
        apiParams.setKeytype(Keytype);
        return apiParams;
    }


    @PostMapping
    public Result add(@RequestBody Api api) {
        Condition con = new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname().replace("'", "''") + "'");
        if (apiService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("此发布单元下已经存在此API");
        } else {
            apiService.save(api);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apiService.deleteById(id);
        apiParamsService.deletebyApiid(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Api api) {
        apiService.update(api);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Api api = apiService.getById(id);
        return ResultGenerator.genOkResult(api);
    }

    @GetMapping("/getapinum")
    public Result getapinum() {
        Integer apinum = apiService.getapinum();
        return ResultGenerator.genOkResult(apinum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Api> list = apiService.listAll();
        PageInfo<Api> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据发布单元id获取apis
     */
    @PostMapping("/getapibydeployunitid")
    public Result getapibydeployunitid(@RequestBody final Map<String, Object> param) {
        String deployunitid = param.get("sourcedeployunitid").toString();
        final List<Api> list = this.apiService.getapibydeployunitid(Long.parseLong(deployunitid));
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getstaticsdeployapi")
    public Result getstaticsdeployapi() {
        List<Api> list = apiService.getstaticsdeployapi();
        List<StaticsDataForPie> result = new ArrayList<>();
        for (Api api : list) {
            StaticsDataForPie staticsDataForPie = new StaticsDataForPie();
            staticsDataForPie.setValue(api.getId());
            staticsDataForPie.setName(api.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }


    @GetMapping("/apibydeploy")
    public Result listbydeploy(@RequestParam String deployunitname) {
        List<Api> list = apiService.listAllbydeploy(deployunitname);
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getresponetypebydeployandapiname")
    public Result getresponetypebydeployandapiname(@RequestParam String deployunitname, @RequestParam String apiname) {
        Api apilist = apiService.getresponetypebydeployandapiname(deployunitname, apiname);
        return ResultGenerator.genOkResult(apilist);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Api api) {
        Condition con = new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname().replace("'", "''") + "'")
                .andCondition("id <> " + api.getId());
        if (apiService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("此发布单元下已经存在此API");
        } else {

            this.apiService.updateApi(api);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Api> list = this.apiService.findApiWithName(param);
        final PageInfo<Api> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @PostMapping("/copyapi")
    public Result copyapi(@RequestBody final Map<String, Object> param) {
        String sourceapiid = param.get("sourceapiid").toString();
        String sourcedeployunitid = param.get("sourcedeployunitid").toString();
        String sourcedeployunitname = param.get("sourcedeployunitname").toString();
        String objectdeployunitid = param.get("objectdeployunitid").toString();
        String objectdeployunitname = param.get("objectdeployunitname").toString();
        String newapiname = param.get("newapiname").toString();

        Condition con = new Condition(Api.class);
        con.createCriteria().andCondition("deployunitid = " + objectdeployunitid)
                .andCondition("apiname = '" + newapiname + "'");
        if (apiService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult(objectdeployunitname + "已存在存在此API名");
        } else {
            //复制api
            Api api = apiService.getBy("id", Long.parseLong(sourceapiid));
            Condition apiparamscon = new Condition(ApiParams.class);
            apiparamscon.createCriteria().andCondition("apiid = " + Long.parseLong(sourceapiid));
            List<ApiParams> apiParamsList = apiParamsService.listByCondition(apiparamscon);
            api.setDeployunitid(Long.parseLong(objectdeployunitid));
            api.setDeployunitname(objectdeployunitname);
            api.setApiname(newapiname);
            api.setId(null);
            Date date = new Date();
            api.setCreateTime(date);
            api.setLastmodifyTime(date);
            apiService.save(api);
            Long ApiId = api.getId();
            //复制api参数
            for (ApiParams apiParams : apiParamsList) {
                apiParams.setApiid(ApiId);
                apiParams.setApiname(newapiname);
                apiParams.setDeployunitid(Long.parseLong(objectdeployunitid));
                apiParams.setDeployunitname(objectdeployunitname);
                apiParams.setId(null);
                apiParams.setCreateTime(date);
                apiParams.setLastmodifyTime(date);
                apiParamsService.save(apiParams);
            }
            return ResultGenerator.genOkResult();
        }
    }
}
