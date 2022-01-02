package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.HttpParamers;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.core.service.TestRunHttphelp;
import com.zoctan.api.dto.RequestHead;
import com.zoctan.api.dto.ResponeGeneral;
import com.zoctan.api.dto.StaticsDataForPie;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Zoctan
 * @date 2020/09/11
 */
@RestController
@RequestMapping("/apicases")
public class ApicasesController {
    @Resource
    private ApicasesService apicasesService;
    @Autowired
    private ApiCasedataService apiCasedataService;
    @Autowired
    private ApicasesAssertService apicasesAssertService;
    @Autowired(required = false)
    private ApiService apiService;
    @Autowired(required = false)
    private DeployunitService deployunitService;
    @Autowired(required = false)
    private MacdepunitService macdepunitService;
    @Autowired(required = false)
    private MachineService machineService;
    @Autowired(required = false)
    private ApiParamsService apiParamsService;
    @Autowired(required = false)
    private TestconditionService testconditionService;
    @Value("${spring.conditionserver.serverurl}")
    private String conditionserver;


    @PostMapping
    public Result add(@RequestBody Apicases apicases) {

        Condition con = new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
                .andCondition("apiname = '" + apicases.getApiname() + "'").andCondition("casename = '" + apicases.getCasename().replace("'","''") + "'")
        ;
        //.orCondition("casejmxname = '" + apicases.getCasejmxname() + "'")
        if (apicasesService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        } else {
            apicasesService.save(apicases);
            return ResultGenerator.genOkResult();
        }
    }


    @PostMapping("/copycases")
    public Result Copycases(@RequestBody final Map<String, Object> param) {
        String sourcecaseid = param.get("sourcecaseid").toString();
        String sourcedeployunitid = param.get("sourcedeployunitid").toString();
        String sourcedeployunitname = param.get("sourcedeployunitname").toString();
        String newcasename = param.get("newcasename").toString();

        Condition con = new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitid = " + sourcedeployunitid)
                .andCondition("casename = '" + newcasename + "'");
        if (apicasesService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult(sourcedeployunitname + "已存在存在此用例名");
        } else {
            Apicases Sourcecase = apicasesService.getBy("id", Long.parseLong(sourcecaseid));
            Condition apcasedatacon = new Condition(Apicases.class);
            apcasedatacon.createCriteria().andCondition("caseid = " + Long.parseLong(sourcecaseid));
            List<ApiCasedata> SourceApicasedataList = apiCasedataService.listByCondition(apcasedatacon);
            if (SourceApicasedataList.size() > 0) {
                //复制用例
                Sourcecase.setDeployunitid(Long.parseLong(sourcedeployunitid));
                Sourcecase.setDeployunitname(sourcedeployunitname);
                Sourcecase.setCasename(newcasename);
                Sourcecase.setId(null);
                apicasesService.save(Sourcecase);
                Long NewCaseId = Sourcecase.getId();
                //复制用例数据
                for (ApiCasedata apiCasedata : SourceApicasedataList) {
                    apiCasedata.setCaseid(NewCaseId);
                    apiCasedata.setId(null);
                    apiCasedataService.save(apiCasedata);
                }
                //复制断言
                Condition AssertDataCondition = new Condition(ApicasesAssert.class);
                AssertDataCondition.createCriteria().andCondition("caseid = " + Long.parseLong(sourcecaseid));
                List<ApicasesAssert> SourceAssertdataList = apicasesAssertService.listByCondition(AssertDataCondition);
                for (ApicasesAssert apicasesAssert : SourceAssertdataList) {
                    apicasesAssert.setCaseid(NewCaseId);
                    apicasesAssert.setId(null);
                    apicasesAssertService.save(apicasesAssert);
                }
                return ResultGenerator.genOkResult();
            } else {
                return ResultGenerator.genFailedResult("当前选择的源用例还未完成用例数据，请完成后再进行复制操作");
            }
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesService.deleteById(id);
        apiCasedataService.deletcasedatabyid(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Apicases apicases) {
        apicasesService.update(apicases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Apicases apicases = apicasesService.getById(id);
        return ResultGenerator.genOkResult(apicases);
    }

    @GetMapping("/getcasenum")
    public Result getcasenum(@RequestParam String casetype) {
        Integer apicasesnum = apicasesService.getcasenum(casetype);
        return ResultGenerator.genOkResult(apicasesnum);
    }

    @GetMapping("/getperformancecasenum")
    public Result getperformancecasenum(@RequestParam String casetype) {
        Integer apicasesnum = apicasesService.getcasenum(casetype);
        return ResultGenerator.genOkResult(apicasesnum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Apicases> list = apicasesService.listAll();
        PageInfo<Apicases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/getstaticsdeployunitcases")
    public Result getstaticsdeployunitcases() {
        List<Apicases> list = apicasesService.getstaticsdeployunitcases();
        List<StaticsDataForPie> result = new ArrayList<>();
        for (Apicases ac : list) {
            StaticsDataForPie staticsDataForPie = new StaticsDataForPie();
            staticsDataForPie.setValue(ac.getApiid());
            staticsDataForPie.setName(ac.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Apicases apicases) {
        if (apicasesService.forupdateifexist(apicases).size() > 0) {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        } else {
            this.apicasesService.updateApicase(apicases);
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
        final List<Apicases> list = this.apicasesService.findApiCaseWithName(param);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchleftcase")
    public Result searchleftcase(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Apicases> list = this.apicasesService.findApiCaseleft(param);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 根据发布单元id获取用例
     */
    @PostMapping("/getcasebydeployunitid")
    public Result getcasebydeployunitid(@RequestBody final Map<String, Object> param) {
        String deployunitid = param.get("sourcedeployunitid").toString();
        final List<Apicases> list = this.apicasesService.getcasebydeployunitid(Long.parseLong(deployunitid));
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchbyname")
    public Result searchbyname(@RequestBody final Map<String, Object> param) {
        String deployunitname = (String) param.get("casedeployunitname");
        String apiname = (String) param.get("caseapiname");
        final List<Apicases> list = this.apicasesService.getapicasebyName(deployunitname, apiname);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 运行测试
     */
    @PostMapping("/runtest")
    public Result runtest(@RequestBody final Map<String, Object> param) {
        String enviromentid = param.get("enviromentid").toString();
        Long Caseid = Long.parseLong(param.get("caseid").toString());
        boolean prixflag = Boolean.parseBoolean(param.get("prixflag").toString());

        HashMap<String, String> ParamsValuesMap = new HashMap<>();
        if(prixflag)
        {
            //请求条件服务处理前置条件
            List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Caseid, "前置条件", "测试用例");
            String APIRespone = "";
            if (testconditionList.size() > 0) {
                String ScriptConditionServerurl = conditionserver + "/testcondition/execcasecondition/script";
                String DBConditionServerurl = conditionserver + "/testcondition/execcasecondition/db";
                String APIConditionServerurl = conditionserver + "/testcondition/execcasecondition/api";

                Long ConditionID = testconditionList.get(0).getId();
                param.put("ConditionID", ConditionID);
                HttpHeader header = new HttpHeader();
                String params = JSON.toJSONString(param);
                try {
                    //请求API条件
                    APIRespone = Httphelp.doPost(APIConditionServerurl, params, header, 30000, 30000);
                    if(APIRespone.contains("接口子条件执行异常"))
                    {
                        JSONObject object= JSON.parseObject(APIRespone);
                        throw new Exception(object.getString("message"));
                    }
                    //请求脚本条件
                    String ScriptRespone =Httphelp.doPost(ScriptConditionServerurl, params, header, 30000, 30000);
                    if(ScriptRespone.contains("脚本条件执行异常"))
                    {
                        JSONObject object= JSON.parseObject(ScriptRespone);
                        throw new Exception(object.getString("message"));
                    }
                    //请求DB条件
                    String DBRespone =Httphelp.doPost(DBConditionServerurl, params, header, 30000, 30000);
                    if(DBRespone.contains("数据库子条件执行异常"))
                    {
                        JSONObject object= JSON.parseObject(DBRespone);
                        throw new Exception(object.getString("message"));
                    }
                } catch (Exception ex) {
                    if(ex.getMessage().contains("Connection refused"))
                    {
                        return ResultGenerator.genFailedResult("无法连接条件服务器，请检查ConditionService是否正常启动！");
                    }
                    else
                    {
                        return ResultGenerator.genFailedResult("执行前置条件异常：" + ex.getMessage());
                    }
                }
            }
            if (APIRespone != "") {
                JSONObject jsonObject = JSON.parseObject(APIRespone);
                for (Map.Entry<String, Object> stringObjectEntry : jsonObject.getJSONObject("data").entrySet()) {
                    String key = stringObjectEntry.getKey();
                    String value = stringObjectEntry.getValue().toString();
                    ParamsValuesMap.put(key, value);
                }
            }
        }

        Apicases apicases = apicasesService.getBy("id", Caseid);
        Long Apiid = apicases.getApiid();
        Api api = apiService.getBy("id", Apiid);
        if (api == null) {
            return ResultGenerator.genFailedResult("当前用例的API不存在，请检查是否被删除！");
        }
        String Method = api.getVisittype();
        String ApiStyle = api.getApistyle();
        String RequestContentType = api.getRequestcontenttype();
        Deployunit deployunit = deployunitService.getBy("id", api.getDeployunitid());
        if (deployunit == null) {
            return ResultGenerator.genFailedResult("当前用例的API所在的发布单元不存在，请检查是否被删除！");
        }
        String Protocal = deployunit.getProtocal();
        Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(Long.parseLong(enviromentid), deployunit.getId());
        if (macdepunit != null) {
            String testserver = "";
            String resource = "";
            if (macdepunit.getVisittype().equals("ip")) {
                Long MachineID = macdepunit.getMachineid();
                Machine machine = machineService.getBy("id", MachineID);
                if (machine == null) {
                    return ResultGenerator.genFailedResult("当前环境中的服务器不存在，请检查是否被删除！");
                }
                testserver = machine.getIp();
                resource = deployunit.getProtocal() + "://" + testserver + ":" + deployunit.getPort() + api.getPath();
            } else {
                testserver = macdepunit.getDomain();
                resource = deployunit.getProtocal() + "://" + testserver + api.getPath();
            }

            //获取api参数类型，根据类型获取用例数据，并且检查类型对应是否完善了用例数据
            List<ApiParams> HeaderApiParams = apiParamsService.getApiParamsbypropertytype(api.getId(), "Header");
            List<ApiParams> ParamsApiParams = apiParamsService.getApiParamsbypropertytype(api.getId(), "Params");
            List<ApiParams> BodyApiParams = apiParamsService.getApiParamsbypropertytype(api.getId(), "Body");

            List<ApiCasedata> HeaderApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Header");
            List<ApiCasedata> ParamsApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Params");
            List<ApiCasedata> BodyApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Body");

            HttpHeader header = new HttpHeader();
            if (HeaderApiParams.size() > 0) {
                if (HeaderApiCasedataList.size() == 0) {
                    return ResultGenerator.genFailedResult("当前用例API的Header参数还未设计用例数据，请先完善后运行测试！");
                } else {
                    for (ApiCasedata Headdata : HeaderApiCasedataList) {
                        String HeaderName=Headdata.getApiparam();
                        String HeaderValue=Headdata.getApiparamvalue();
                        if (ParamsValuesMap.size() > 0) {
                            HeaderValue=GetVariablesValues(HeaderValue,ParamsValuesMap);
                        }
                        header.addParam(HeaderName, HeaderValue);
                    }
                }
            }
            String PostData = "";
            HttpParamers paramers = HttpParamers.httpPostParamers();
            if (ParamsApiParams.size() > 0) {
                if (ParamsApiCasedataList.size() == 0) {
                    return ResultGenerator.genFailedResult("当前用例API的Params参数还未设计用例数据，请先完善后运行测试！");
                } else {
                    for (ApiCasedata Paramdata : ParamsApiCasedataList) {
                        String ParamName=Paramdata.getApiparam();
                        String ParamValue=Paramdata.getApiparamvalue();
                        if (ParamsValuesMap.size() > 0) {
                            ParamValue=GetVariablesValues(ParamValue,ParamsValuesMap);
                        }
                        paramers.addParam(ParamName, ParamValue);
                    }
                    if (RequestContentType.equals("json")) {
                        paramers.setJsonParamer();
                        PostData = paramers.getJsonParamer();
                    }
                    if (RequestContentType.equals("form表单")) {
                        try {
                            PostData = paramers.getQueryString("UTF-8");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (BodyApiParams.size() > 0) {
                if (BodyApiCasedataList.size() > 0) {
                    //根据api请求数据类型处理，json，xml
                    String RequestDataType = api.getRequestcontenttype();
                    //获取冗余完整json,xml字段值
                    List<ApiParams> paramsList = apiParamsService.getApiParamsbypropertytype(api.getId(), "Body");
                    if (paramsList.size() == 0) {
                        return ResultGenerator.genFailedResult("当前用例API无Body参数，请检查是否被删除！");
                    }
                    PostData = BodyApiCasedataList.get(0).getApiparamvalue();
                } else {
                    return ResultGenerator.genFailedResult("当前用例API的Body参数还未设计用例数据，请先完善后运行测试！");
                }
            }
            try {
                long Start = new Date().getTime();
                TestResponeData respon = TestRunHttphelp.doService(Protocal, resource, Method, ApiStyle, paramers, PostData, RequestContentType, header, 5000, 5000);
                long End = new Date().getTime();
                long CostTime = End - Start;
                respon.setResponeTime(CostTime);
                ResponeGeneral responeGeneral = new ResponeGeneral();
                responeGeneral.setApistyle(ApiStyle);
                responeGeneral.setPostData(PostData);
                responeGeneral.setMethod(Method);
                responeGeneral.setProtocal(Protocal);
                responeGeneral.setUrl(resource);
                List<RequestHead>requestHeadList=new ArrayList<>();
                for (String Key:header.getParams().keySet()) {
                    RequestHead requestHead=new RequestHead();
                    requestHead.setKeyName(Key);
                    requestHead.setKeyValue(header.getParams().get(Key));
                    requestHeadList.add(requestHead);
                }
                respon.setRequestHeadList(requestHeadList);
                respon.setResponeGeneral(responeGeneral);
                return ResultGenerator.genOkResult(respon);

            } catch (Exception exception) {
                return ResultGenerator.genFailedResult(exception.getMessage());
            }
        } else {
            return ResultGenerator.genFailedResult("当前环境未部署此用例API所在的发布单元，请先完成环境下的部署！");
        }
    }

    //获取参数值的具体内容，支持$变量，以及$变量和字符串拼接
    private String GetVariablesValues(String Value, HashMap<String,String> NameValueMap) {
        String Result = "";
        if (Value.contains("+")) {
            String[] Array = Value.split("\\+");
            for (String str : Array) {
                if (str.contains("$")) {
                    String VariablesName = str.substring(1);
                    //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                    String VariablesNameValue="";
                    if(NameValueMap.containsKey(VariablesName))
                    {
                         VariablesNameValue = NameValueMap.get(VariablesName);
                    }
                    Result = Result + VariablesNameValue;
                } else {
                    Result = Result + str;
                }
            }
        } else {
            if (Value.contains("$")) {
                String VariablesName = Value.substring(1);
                //根据用例参数值是否以$开头，如果是则认为是变量通过变量表取到变量值
                String VariablesNameValue="";
                if(NameValueMap.containsKey(VariablesName))
                {
                    VariablesNameValue = NameValueMap.get(VariablesName);
                }
                Result = VariablesNameValue;
            } else {
                Result = Value;
            }
        }
        return Result;
    }

}
