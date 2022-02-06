package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.*;
import com.zoctan.api.dto.*;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private EnviromentService enviromentService;
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

    @Autowired(required = false)
    private ConditionApiService conditionApiService;
    @Autowired(required = false)
    private ConditionDbService conditionDbService;
    @Autowired(required = false)
    private ConditionScriptService conditionScriptService;
    @Autowired(required = false)
    private ExecuteplanTestcaseService executeplanTestcaseService;
    @Resource
    private ConditionOrderService conditionOrderService;
    @Value("${spring.conditionserver.serverurl}")
    private String conditionserver;


    @PostMapping
    public Result add(@RequestBody Apicases apicases) {
        Condition con = new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
                .andCondition("apiname = '" + apicases.getApiname() + "'").andCondition("casename = '" + apicases.getCasename().replace("'", "''") + "'");
        if (apicasesService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("用例名已存在");
        } else {
            apicasesService.save(apicases);
            //增加初始化参数值
            Long apiid=apicases.getApiid();
            Api api=apiService.getById(apiid);
            String RequestContentType=api.getRequestcontenttype();
            Map<String, Object> params=new HashMap<>();
            params.put("apiid",apiid);
            List<ApiParams> apiParamsList= apiParamsService.getApiParamsbyapiid(params);
            List<ApiCasedata>apiCasedataList=new ArrayList<>();
            for (ApiParams apiParams :apiParamsList) {
                if(apiParams.getPropertytype().equalsIgnoreCase("Header")||apiParams.getPropertytype().equalsIgnoreCase("Params"))
                {
                    ApiCasedata apiCasedata=GetApiCaseData(apicases,apiParams);
                    apiCasedataList.add(apiCasedata);
                }
                else
                {
                    if(RequestContentType.equalsIgnoreCase("Form表单"))
                    {
                        ApiCasedata apiCasedata=GetApiCaseData(apicases,apiParams);
                        apiCasedataList.add(apiCasedata);
                    }
                    else
                    {
                        if(apiParams.getKeytype().equalsIgnoreCase(RequestContentType))
                        {
                            ApiCasedata apiCasedata=GetApiCaseData(apicases,apiParams);
                            apiCasedataList.add(apiCasedata);
                        }
                    }
                }
//                ApiCasedata apiCasedata=new ApiCasedata();
//                apiCasedata.setCaseid(apicases.getId());
//                apiCasedata.setCasename(apicases.getCasename());
//                apiCasedata.setPropertytype(apiParams.getPropertytype());
//                if(apiParams.getKeydefaultvalue().equalsIgnoreCase("NoForm"))
//                {
//                    apiCasedata.setApiparam("Body");
//                    apiCasedata.setApiparamvalue(apiParams.getKeyname());
//                }
//                else
//                {
//                    apiCasedata.setApiparam(apiParams.getKeyname());
//                    apiCasedata.setApiparamvalue(apiParams.getKeydefaultvalue());
//                }
//                apiCasedata.setParamstype(apiParams.getKeytype());
//                apiCasedata.setMemo("");
//                apiCasedataList.add(apiCasedata);
            }
            if(apiCasedataList.size()>0)
            {
                apiCasedataService.save(apiCasedataList);
            }
            return ResultGenerator.genOkResult();
        }
    }

    private ApiCasedata GetApiCaseData(Apicases apicases,ApiParams apiParams)
    {
        ApiCasedata apiCasedata=new ApiCasedata();
        apiCasedata.setCaseid(apicases.getId());
        apiCasedata.setCasename(apicases.getCasename());
        apiCasedata.setPropertytype(apiParams.getPropertytype());
        if(apiParams.getKeydefaultvalue().equalsIgnoreCase("NoForm"))
        {
            apiCasedata.setApiparam("Body");
            apiCasedata.setApiparamvalue(apiParams.getKeyname());
        }
        else
        {
            apiCasedata.setApiparam(apiParams.getKeyname());
            apiCasedata.setApiparamvalue(apiParams.getKeydefaultvalue());
        }
        apiCasedata.setParamstype(apiParams.getKeytype());
        apiCasedata.setMemo("");
        apiCasedata.setCreateTime(new Date());
        apiCasedata.setLastmodifyTime(new Date());
        return apiCasedata;
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
                Sourcecase.setCreateTime(new Date());
                Sourcecase.setLastmodifyTime(new Date());
                Sourcecase.setId(null);
                apicasesService.save(Sourcecase);
                Long NewCaseId = Sourcecase.getId();
                //复制用例数据
                for (ApiCasedata apiCasedata : SourceApicasedataList) {
                    apiCasedata.setCaseid(NewCaseId);
                    apiCasedata.setId(null);
                    apiCasedata.setCasename(newcasename);
                    apiCasedata.setCreateTime(new Date());
                    apiCasedata.setLastmodifyTime(new Date());
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
        //删除用例值数据
        apiCasedataService.deletcasedatabyid(id);
        //删除用例断言
        Condition caseassertcon = new Condition(ApicasesAssert.class);
        caseassertcon.createCriteria().andCondition("caseid = " + id);
        apicasesAssertService.deleteByCondition(caseassertcon);
        //删除用例条件，子条件
        Condition con = new Condition(Testcondition.class);
        con.createCriteria().andCondition("objecttype = '测试用例'").andCondition("objectid = " + id).andCondition("conditiontype = '"  + "前置条件'");
        List<Testcondition> testconditionList= testconditionService.listByCondition(con);
        if (testconditionList.size() > 0) {
            Long ConditionID=testconditionList.get(0).getId();
            conditionApiService.deleteBy("conditionid",ConditionID);
            conditionDbService.deleteBy("conditionid",ConditionID);
            conditionScriptService.deleteBy("conditionid",ConditionID);
            testconditionService.deleteByCondition(con);
        }
        //删除测试集合中的用例
        executeplanTestcaseService.removetestcase(id);
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

    private String getSubConditionRespone(String Url, String params, HttpHeader header) throws Exception {
        //请求API条件
        TestHttp testHttp=new TestHttp();
        header.addParam("Content-Type", "application/json;charset=utf-8");
        TestResponeData testResponeData=testHttp.doService("http","",Url,header,new HttpParamers(),params,"POST","",30000);
        String Respone=testResponeData.getResponeContent();
        //String Respone = HttphelpB1.doPost(Url, params, header, 30000, 30000);
        if (Respone.contains("条件执行异常")) {
            JSONObject object = JSON.parseObject(Respone);
            throw new Exception(object.getString("message"));
        }
        return Respone;
    }
    /**
     * 运行测试
     */
    @PostMapping("/runtest")
    public Result runtest(@RequestBody final Map<String, Object> param) {
        String enviromentid = param.get("enviromentid").toString();
        Long Caseid = Long.parseLong(param.get("caseid").toString());
        boolean prixflag = Boolean.parseBoolean(param.get("prixflag").toString());

        HashMap<String, Object> ParamsValuesMap = new HashMap<>();
        if (prixflag) {
            //请求条件服务处理前置条件
            List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Caseid, "前置条件", "测试用例");
            String APIRespone = "";
            if (testconditionList.size() > 0) {
                String ScriptConditionServerurl = conditionserver + "/testcondition/execcasecondition/script";
                String DBConditionServerurl = conditionserver + "/testcondition/execcasecondition/db";
                String APIConditionServerurl = conditionserver + "/testcondition/execcasecondition/api";

                Long ConditionID = testconditionList.get(0).getId();
                Map<String, Object> conditionmap = new HashMap<>();
                conditionmap.put("conditionid", ConditionID);
                List<ConditionOrder> conditionOrderList = conditionOrderService.findconditionorderWithid(conditionmap);
                param.put("ConditionID", ConditionID);
                HttpHeader header = new HttpHeader();
                String params = JSON.toJSONString(param);
                try {
                    if (conditionOrderList.size() > 0) {
                        for (ConditionOrder conditionOrder : conditionOrderList) {
                            if (conditionOrder.getSubconditiontype().equals("接口")) {
                                APIRespone = getSubConditionRespone(APIConditionServerurl, params, header);
                            }
                            if (conditionOrder.getSubconditiontype().equals("数据库")) {
                                getSubConditionRespone(DBConditionServerurl, params, header);
                            }
                            if (conditionOrder.getSubconditiontype().equals("脚本")) {
                                getSubConditionRespone(ScriptConditionServerurl, params, header);
                            }
                        }
                    } else {
                        APIRespone = getSubConditionRespone(APIConditionServerurl, params, header);
                        getSubConditionRespone(ScriptConditionServerurl, params, header);
                        getSubConditionRespone(DBConditionServerurl, params, header);
                    }
                } catch (Exception ex) {
                    if (ex.getMessage().contains("Connection refused")) {
                        return ResultGenerator.genFailedResult("无法连接条件服务器，请检查ConditionService是否正常启动！");
                    } else {
                        return ResultGenerator.genFailedResult("执行前置条件异常：" + ex.getMessage());
                    }
                }
            }
            if (APIRespone != "") {
                JSONObject jsonObject = JSON.parseObject(APIRespone);
                for (Map.Entry<String, Object> objectEntry : jsonObject.getJSONObject("data").entrySet()) {
                    String key = objectEntry.getKey();
                    Object value = objectEntry.getValue().toString();
                    ParamsValuesMap.put(key, value);
                }
            }
        }

        Apicases apicases = apicasesService.getBy("id", Caseid);
        if (apicases == null) {
            return ResultGenerator.genFailedResult("当前用例不存在，请检查是否被删除！");
        }
        Long Apiid = apicases.getApiid();
        Api api = apiService.getBy("id", Apiid);
        if (api == null) {
            return ResultGenerator.genFailedResult("当前用例的API不存在，请检查是否被删除！");
        }
        String Method = api.getVisittype();
        String ApiStyle = api.getApistyle();
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
                Enviroment enviroment = enviromentService.getBy("id", Long.parseLong(enviromentid));
                if (enviroment == null) {
                    return ResultGenerator.genFailedResult("当前用例调试的环境不存在，请检查是否被删除！");
                }
                testserver = machine.getIp();
                resource = deployunit.getProtocal() + "://" + testserver + ":" + deployunit.getPort() + api.getPath();
            } else {
                testserver = macdepunit.getDomain();
                resource = deployunit.getProtocal() + "://" + testserver + api.getPath();
            }

            List<ApiCasedata> HeaderApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Header");
            List<ApiCasedata> ParamsApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Params");
            List<ApiCasedata> BodyApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Body");
            String requestcontenttype = api.getRequestcontenttype();
            //Header用例值
            HttpHeader header = new HttpHeader();
            header = AddHeaderByRequestContentType(header, requestcontenttype);
            for (ApiCasedata Headdata : HeaderApiCasedataList) {
                String HeaderName = Headdata.getApiparam();
                String HeaderValue = Headdata.getApiparamvalue();
                if (ParamsValuesMap.size() > 0) {
                    Object Value = GetVariablesObjectValues(HeaderValue, ParamsValuesMap);
                    header.addParam(HeaderName, Value);
                }
                else
                {
                    header.addParam(HeaderName, HeaderValue);
                }
            }

            //参数用例值
            HttpParamers paramers = new HttpParamers();
            for (ApiCasedata Paramdata : ParamsApiCasedataList) {
                String ParamName = Paramdata.getApiparam();
                String ParamValue = Paramdata.getApiparamvalue();
                String DataType=Paramdata.getParamstype();
                if(ParamValue.contains("$"))
                {
                    Object Value = GetVariablesObjectValues(ParamValue, ParamsValuesMap);
                    paramers.addParam(ParamName, Value);
                }
                else
                {
                    Object ParseData= null;
                    try {
                        ParseData = GetDataByType(ParamValue,DataType);
                    } catch (Exception exception) {
                        return ResultGenerator.genFailedResult("用例Params参数值异常："+exception.getMessage());
                    }
                    paramers.addParam(ParamName, ParseData);
                }
            }

            //Body用例值
            String PostData = "";
            HttpParamers Bodyparamers = new HttpParamers();
            if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                //值支持变量
                for (ApiCasedata Paramdata : BodyApiCasedataList) {
                    String ParamName = Paramdata.getApiparam();
                    String ParamValue = Paramdata.getApiparamvalue();
                    String DataType = Paramdata.getParamstype();
                    if(ParamValue.contains("$"))
                    {
                        Object Value = GetVariablesObjectValues(ParamValue, ParamsValuesMap);
                        Bodyparamers.addParam(ParamName, Value);
                    }
                    else
                    {
                        Object ParseData= null;
                        try {
                            ParseData = GetDataByType(ParamValue,DataType);
                        } catch (Exception exception) {
                            return ResultGenerator.genFailedResult("用例Body参数值异常："+exception.getMessage());
                        }
                        Bodyparamers.addParam(ParamName, ParseData);
                    }
                }
                if (Bodyparamers.getParams().size() > 0) {
                    PostData = Bodyparamers.getQueryString();
                }
            } else {
                for (ApiCasedata Paramdata : BodyApiCasedataList) {
                    PostData=Paramdata.getApiparamvalue();
                }
            }
            try {
                long Start = new Date().getTime();
                TestHttp testHttp=new TestHttp();
                String VisitType = api.getVisittype();
                TestResponeData respon = testHttp.doService(Protocal, ApiStyle, resource, header, paramers, PostData, VisitType, requestcontenttype, 300);
                long End = new Date().getTime();
                long CostTime = End - Start;
                respon.setResponeTime(CostTime);
                ResponeGeneral responeGeneral = new ResponeGeneral();
                responeGeneral.setApistyle(ApiStyle);
                responeGeneral.setPostData(PostData);
                responeGeneral.setMethod(Method);
                responeGeneral.setProtocal(Protocal);
                responeGeneral.setUrl(respon.getRequestUrl());
                List<RequestHead> requestHeadList = new ArrayList<>();
                for (String Key : header.getParams().keySet()) {
                    RequestHead requestHead = new RequestHead();
                    requestHead.setKeyName(Key);
                    requestHead.setKeyValue(header.getParams().get(Key).toString());
                    requestHeadList.add(requestHead);
                }
                List<RequestParams> requestParamsList = new ArrayList<>();
                for (String Key : paramers.getParams().keySet()) {
                    RequestParams requestParams = new RequestParams();
                    requestParams.setKeyName(Key);
                    requestParams.setKeyValue(paramers.getParams().get(Key).toString());
                    requestParamsList.add(requestParams);
                }
                respon.setRequestHeadList(requestHeadList);
                respon.setRequestParamsList(requestParamsList);
                respon.setResponeGeneral(responeGeneral);
                return ResultGenerator.genOkResult(respon);

            } catch (Exception exception) {
                return ResultGenerator.genFailedResult(exception.getMessage());
            }
        } else {
            return ResultGenerator.genFailedResult("当前环境未部署此用例API所在的发布单元，请先完成环境下的部署！");
        }
    }


    //获取参数值的具体内容，支持$变量
    private Object GetVariablesObjectValues(String Variables, HashMap<String, Object> NameValueMap) {
        Object Result = "";
        if (Variables.trim().contains("$")) {
            if (Variables.trim().length() == 1) {
                Result = Variables;
            } else {
                Variables = Variables.substring(1);
                if (NameValueMap.containsKey(Variables)) {
                    Result = NameValueMap.get(Variables);
                }
            }
        } else {
            Result = Variables;
        }
        return Result;
    }

    //根据数据类型转换
    private Object GetDataByType(String Data,String ValueType) throws Exception {
        Object Result=new Object();
        if (ValueType.equalsIgnoreCase("Number")) {
            try {
                Result = Long.parseLong(Data);
            } catch (Exception ex) {
                Result = "参数值  " + Data + " 不是数字Number类型，请检查修改！";
                throw new Exception(Result.toString());
            }
        }
        if (ValueType.equalsIgnoreCase("Json")) {
            try {
                Result = JSON.parse(Data);
            } catch (Exception ex) {
                Result = "参数值  " + Data + " 不是Json类型，请检查修改！";
                throw new Exception(Result.toString());
            }
        }
        if (ValueType.equalsIgnoreCase("String")||ValueType.isEmpty()) {
            Result = Data;
        }
        if (ValueType.equalsIgnoreCase("Array")) {
            String[] Array = Data.split(",");
            Result = Array;
        }
        if (ValueType.equalsIgnoreCase("Bool")) {
            try {
                Result = Boolean.parseBoolean(Data);
            } catch (Exception ex) {
                Result = "参数值  " + Data + " 不是布尔Bool类型，请检查修改！";
                throw new Exception(Result.toString());
            }
        }
        return Result;
    }

    //根据请求数据类型增加header
    private HttpHeader AddHeaderByRequestContentType(HttpHeader httpHeader, String RequestContentType) {
        if (RequestContentType.equalsIgnoreCase("json")) {
            httpHeader.addParam("Content-Type", "application/json;charset=utf-8");
        }
        if (RequestContentType.equalsIgnoreCase("xml")) {
            httpHeader.addParam("Content-Type", "application/xml;charset=utf-8");
        }
        if (RequestContentType.equalsIgnoreCase("Form表单")) {
            httpHeader.addParam("Content-Type", "application/x-www-form-urlencoded");
        }
        return httpHeader;
    }
}
