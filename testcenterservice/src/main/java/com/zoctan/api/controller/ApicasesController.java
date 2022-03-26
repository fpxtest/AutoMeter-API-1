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
import com.zoctan.api.util.RadomVariables;
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
    @Autowired(required = false)
    private VariablesService variablesService;
    @Autowired(required = false)
    private TestvariablesService testvariablesService;

    @Autowired(required = false)
    private DbvariablesService dbvariablesService;


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
            Long apiid = apicases.getApiid();
            Api api = apiService.getById(apiid);
            String RequestContentType = api.getRequestcontenttype();
            Map<String, Object> params = new HashMap<>();
            params.put("apiid", apiid);
            List<ApiParams> apiParamsList = apiParamsService.getApiParamsbyapiid(params);
            List<ApiCasedata> apiCasedataList = new ArrayList<>();
            for (ApiParams apiParams : apiParamsList) {
                if (apiParams.getPropertytype().equalsIgnoreCase("Header") || apiParams.getPropertytype().equalsIgnoreCase("Params")) {
                    ApiCasedata apiCasedata = GetApiCaseData(apicases, apiParams);
                    apiCasedataList.add(apiCasedata);
                } else {
                    if (RequestContentType.equalsIgnoreCase("Form表单")) {
                        ApiCasedata apiCasedata = GetApiCaseData(apicases, apiParams);
                        apiCasedataList.add(apiCasedata);
                    } else {
                        if (apiParams.getKeytype().equalsIgnoreCase(RequestContentType)) {
                            ApiCasedata apiCasedata = GetApiCaseData(apicases, apiParams);
                            apiCasedataList.add(apiCasedata);
                        }
                    }
                }
            }
            if (apiCasedataList.size() > 0) {
                apiCasedataService.save(apiCasedataList);
            }
            return ResultGenerator.genOkResult();
        }
    }

    private ApiCasedata GetApiCaseData(Apicases apicases, ApiParams apiParams) {
        ApiCasedata apiCasedata = new ApiCasedata();
        apiCasedata.setCaseid(apicases.getId());
        apiCasedata.setCasename(apicases.getCasename());
        apiCasedata.setPropertytype(apiParams.getPropertytype());
        if (apiParams.getKeydefaultvalue().equalsIgnoreCase("NoForm")) {
            apiCasedata.setApiparam("Body");
            apiCasedata.setApiparamvalue(apiParams.getKeyname());
        } else {
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
        con.createCriteria().andCondition("objecttype = '测试用例'").andCondition("objectid = " + id).andCondition("conditiontype = '" + "前置条件'");
        List<Testcondition> testconditionList = testconditionService.listByCondition(con);
        if (testconditionList.size() > 0) {
            Long ConditionID = testconditionList.get(0).getId();
            conditionApiService.deleteBy("conditionid", ConditionID);
            conditionDbService.deleteBy("conditionid", ConditionID);
            conditionScriptService.deleteBy("conditionid", ConditionID);
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
            return ResultGenerator.genFailedResult("用例名已存在");
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
        TestHttp testHttp = new TestHttp();
        header.addParam("Content-Type", "application/json;charset=utf-8");
        TestResponeData testResponeData = testHttp.doService("http", "", Url, header, new HttpParamers(), params, "POST", "", 30000);
        String Respone = testResponeData.getResponeContent();
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

        HashMap<String, String> ParamsValuesMap = new HashMap<>();
        HashMap<String, String> DBParamsValuesMap = new HashMap<>();

        if (prixflag) {
            //请求条件服务处理前置条件
            List<Testcondition> testconditionList = testconditionService.GetConditionByPlanIDAndConditionType(Caseid, "前置条件", "测试用例");
            String APIRespone = "";
            String DBRespone="";
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
                                DBRespone=getSubConditionRespone(DBConditionServerurl, params, header);
                            }
                            if (conditionOrder.getSubconditiontype().equals("脚本")) {
                                getSubConditionRespone(ScriptConditionServerurl, params, header);
                            }
                        }
                    } else {
                        APIRespone = getSubConditionRespone(APIConditionServerurl, params, header);
                        getSubConditionRespone(ScriptConditionServerurl, params, header);
                        DBRespone=getSubConditionRespone(DBConditionServerurl, params, header);
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
                try {
                    JSONObject jsonObject = JSON.parseObject(APIRespone);
                    for (Map.Entry<String, Object> objectEntry : jsonObject.getJSONObject("data").entrySet()) {
                        String key = objectEntry.getKey();
                        String value = objectEntry.getValue().toString();
                        ParamsValuesMap.put(key, value);
                    }
                } catch (Exception ex) {
                    return ResultGenerator.genFailedResult("执行前置接口条件结果异常：" + APIRespone);
                }
            }
            if (DBRespone != "") {
                try {
                    JSONObject jsonObject = JSON.parseObject(DBRespone);
                    for (Map.Entry<String, Object> objectEntry : jsonObject.getJSONObject("data").entrySet()) {
                        String key = objectEntry.getKey();
                        String value = objectEntry.getValue().toString();
                        DBParamsValuesMap.put(key, value);
                    }
                } catch (Exception ex) {
                    return ResultGenerator.genFailedResult("执行前置数据库条件结果异常：" + DBRespone);
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

            List<Variables> variablesList = variablesService.listAll();
            HashMap<String, String> RadomHashMap = new HashMap<>();
            for (Variables va : variablesList) {
                RadomHashMap.put(va.getVariablesname(), va.getVariablestype());
            }

            //url中的变量替换
            //1.随机变量替换
            for (Variables variables : variablesList) {
                String VariableName = "[" + variables.getVariablesname() + "]";
                if (resource.contains(VariableName)) {
                    Object VariableValue = GetRadomValue(variables.getVariablesname());
                    resource = resource.replace(VariableName, VariableValue.toString());
                }
            }
            //2.接口变量替换
            for (String Interfacevariables : ParamsValuesMap.keySet()) {
                String UseInterfacevariables = "<" + Interfacevariables + ">";
                if (resource.contains(UseInterfacevariables)) {
                    Object VariableValue = ParamsValuesMap.get(Interfacevariables);// GetVariablesObjectValues("$" +Interfacevariables, ParamsValuesMap);
                    resource = resource.replace(UseInterfacevariables, VariableValue.toString());
                }
            }

            //3.数据库变量替换
            for (String DBvariables : DBParamsValuesMap.keySet()) {
                String UseDBvariables = "<<" + DBvariables + ">>";
                if (resource.contains(UseDBvariables)) {
                    Object VariableValue = DBParamsValuesMap.get(DBvariables);
                    resource = resource.replace(UseDBvariables, VariableValue.toString());
                }
            }

            List<ApiCasedata> HeaderApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Header");
            List<ApiCasedata> ParamsApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Params");
            List<ApiCasedata> BodyApiCasedataList = apiCasedataService.getparamvaluebycaseidandtype(Caseid, "Body");
            String requestcontenttype = api.getRequestcontenttype();

            //Header用例值
            HttpHeader header = new HttpHeader();
            try {
                header = GetHttpHeader(HeaderApiCasedataList, ParamsValuesMap, RadomHashMap,DBParamsValuesMap);
            } catch (Exception exception) {
                return ResultGenerator.genFailedResult(exception.getMessage());
            }
            header = AddHeaderByRequestContentType(header, requestcontenttype);

            //参数用例值
            HttpParamers paramers = new HttpParamers();
            try {
                paramers = GetHttpParamers(ParamsApiCasedataList, ParamsValuesMap, RadomHashMap,DBParamsValuesMap);
            } catch (Exception exception) {
                return ResultGenerator.genFailedResult(exception.getMessage());
            }

            //Body用例值
            String PostData = "";
            HttpParamers Bodyparamers = new HttpParamers();
            if (requestcontenttype.equalsIgnoreCase("Form表单")) {
                try {
                    Bodyparamers = GetHttpParamers(BodyApiCasedataList, ParamsValuesMap, RadomHashMap,DBParamsValuesMap);
                } catch (Exception exception) {
                    return ResultGenerator.genFailedResult(exception.getMessage());
                }
                if (Bodyparamers.getParams().size() > 0) {
                    PostData = Bodyparamers.getQueryString();
                }
            } else {
                for (ApiCasedata Paramdata : BodyApiCasedataList) {
                    //Body文本内容变量替换
                    PostData = Paramdata.getApiparamvalue();
                    //1.替换随机变量
                    for (Variables variables : variablesList) {
                        String VariableName = "[" + variables.getVariablesname() + "]";
                        if (PostData.contains(VariableName)) {
                            Object VariableValue = GetRadomValue(variables.getVariablesname());
                            PostData = PostData.replace(VariableName, VariableValue.toString());
                        }
                    }
                    //2.替换接口变量
                    for (String Interfacevariables : ParamsValuesMap.keySet()) {
                        String UseInterfacevariables = "<" + Interfacevariables + ">";
                        if (PostData.contains(UseInterfacevariables)) {
                            Object VariableValue = ParamsValuesMap.get(Interfacevariables);// GetVariablesObjectValues("$" +Interfacevariables, ParamsValuesMap);
                            PostData = PostData.replace(UseInterfacevariables, VariableValue.toString());
                        }
                    }
                    //3.替换数据库变量
                    for (String DBvariables : DBParamsValuesMap.keySet()) {
                        String UseDBvariables = "<<" + DBvariables + ">>";
                        if (PostData.contains(UseDBvariables)) {
                            Object VariableValue = DBParamsValuesMap.get(DBvariables);
                            PostData = PostData.replace(UseDBvariables, VariableValue.toString());
                        }
                    }
                }
            }
            try {
                long Start = new Date().getTime();
                TestHttp testHttp = new TestHttp();
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
                String ExceptionMess=exception.getMessage();
                if(ExceptionMess.contains("Illegal character in path at"))
                {
                    ExceptionMess="Url不合法，请检查是否有无法替换的变量，或者有相关非法字符："+exception.getMessage();
                }
                return ResultGenerator.genFailedResult(ExceptionMess);
            }
        } else {
            return ResultGenerator.genFailedResult("当前环境未部署此用例API所在的发布单元，请先完成环境下的部署！");
        }
    }


    //获取HttpHeader
    private HttpHeader GetHttpHeader(List<ApiCasedata> HeaderApiCasedataList, HashMap<String, String> ParamsValuesMap, HashMap<String, String> RadomMap, HashMap<String, String> DBMap) throws Exception {
        HttpHeader header = new HttpHeader();
        for (ApiCasedata Headdata : HeaderApiCasedataList) {
            String HeaderName = Headdata.getApiparam();
            String HeaderValue = Headdata.getApiparamvalue();
            Object Result = GetVaraibaleValue(HeaderValue, RadomMap, ParamsValuesMap,DBMap);
            header.addParam(HeaderName, Result);
        }
        return header;
    }

    //获取HttpParams
    private HttpParamers GetHttpParamers(List<ApiCasedata> ParamsApiCasedataList, HashMap<String, String> ParamsValuesMap, HashMap<String, String> RadomMap, HashMap<String, String> DBMap) throws Exception {
        HttpParamers paramers = new HttpParamers();
        for (ApiCasedata Paramdata : ParamsApiCasedataList) {
            String ParamName = Paramdata.getApiparam();
            String ParamValue = Paramdata.getApiparamvalue();
            String DataType = Paramdata.getParamstype();
            Object ObjectResult = GetVaraibaleValue(ParamValue, RadomMap, ParamsValuesMap,DBMap);
            Object Result = GetDataByType(ObjectResult.toString(), DataType);
            paramers.addParam(ParamName, Result);
        }
        return paramers;
    }

    private Object GetVaraibaleValue(String Value, HashMap<String, String> RadomMap, HashMap<String, String> InterfaceMap, HashMap<String, String> DBMap) throws Exception {
        Object ObjectValue = Value;
        //参数值替换接口变量
        for (String interfacevariablesName : InterfaceMap.keySet()) {
            boolean flag = GetSubOrNot(InterfaceMap, Value, "<", ">");
            if (Value.contains("<" + interfacevariablesName + ">")) {
                String ActualValue = InterfaceMap.get(interfacevariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<" + interfacevariablesName + ">", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    Testvariables testvariables = testvariablesService.getBy("testvariablesname", interfacevariablesName);//  testMysqlHelp.GetVariablesDataType(interfacevariablesName);
                    if (testvariables == null) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, testvariables.getValuetype());
                    }
                }
            }
        }
        //参数值替换数据库变量
        for (String DBvariablesName : DBMap.keySet()) {
            boolean flag = GetSubOrNot(DBMap, Value, "<<", ">>");
            if (Value.contains("<<" + DBvariablesName + ">>")) {
                String ActualValue = DBMap.get(DBvariablesName);
                if (flag) {
                    //有拼接认为是字符串
                    Value = Value.replace("<<" + DBvariablesName + ">>", ActualValue);
                    ObjectValue = Value;
                } else {
                    //无拼接则转换成具体类型,根据变量名获取变量类型
                    Dbvariables dbvariables = dbvariablesService.getBy("dbvariablesname", DBvariablesName);//  testMysqlHelp.GetVariablesDataType(interfacevariablesName);
                    if (dbvariables == null) {
                        ObjectValue = "未找到变量：" + Value + "绑定的接口用例，请检查变量管理-用例变量中是否存在此变量绑定的接口用例";
                    } else {
                        ObjectValue = GetDataByType(ActualValue, dbvariables.getValuetype());
                    }
                }
            }
        }
        //参数值替换随机变量
        for (String variables : RadomMap.keySet()) {
            boolean flag = GetSubOrNot(RadomMap, Value, "[", "]");
            if (Value.contains("[" + variables + "]")) {
                if (flag) {
                    Object RadomValue = GetRadomValue(variables);
                    Value = Value.replace("[" + variables + "]", RadomValue.toString());
                    ObjectValue = Value;
                } else {
                    ObjectValue = GetRadomValue(variables);
                }
            }
        }
        return ObjectValue;
    }

    //判断是否有拼接
    private boolean GetSubOrNot(HashMap<String, String> VariablesMap, String Value, String prefix, String profix) {
        boolean flag = false;
        for (String Key : VariablesMap.keySet()) {
            String ActualValue = prefix + Key + profix;
            if (Value.contains(ActualValue)) {
                String LeftValue = Value.replace(ActualValue, "");
                if (LeftValue.length() > 0) {
                    //表示有拼接
                    return true;
                } else {
                    return false;
                }
            }
        }
        return flag;
    }

    //获取随机变量值
    private Object GetRadomValue(String Value) {
        Object Result = Value;
        String FunctionName = Value;
        List<Variables> variablesList = variablesService.listAll();
        for (Variables variables : variablesList) {
            if (variables.getVariablesname().equalsIgnoreCase(FunctionName)) {
                String Params = variables.getVariablecondition();
                String Variablestype = variables.getVariablestype();
                RadomVariables radomVariables = new RadomVariables();
                if (Variablestype.equalsIgnoreCase("随机字符串")) {
                    try {
                        Integer length = Integer.parseInt(Params);
                        Result = radomVariables.GetRadmomStr(length);
                    } catch (Exception ex) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写参数为数字类型表示字符串长度";
                    }
                }
                if (Variablestype.equalsIgnoreCase("随机整数")) {
                    String ParamsArray[] = Params.split(",");
                    if (ParamsArray.length < 2) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写需要的字符串长度";
                    } else {
                        try {
                            Long Start = Long.parseLong(ParamsArray[0]);
                            Long End = Long.parseLong(ParamsArray[1]);
                            Result = radomVariables.GetRadmomNum(Start, End);
                        } catch (Exception exception) {
                            Result = "随机变量GetRadmomNum输入参数不合法，请填写最小和最大值数字范围";
                        }
                    }
                }
                if (Variablestype.equalsIgnoreCase("随机小数")) {
                    String ParamsArray[] = Params.split(",");
                    if (ParamsArray.length < 2) {
                        Result = "随机变量GetRadmomStr输入参数不合法，请填写需要的字符串长度";
                    } else {
                        try {
                            Long Start = Long.parseLong(ParamsArray[0]);
                            Long End = Long.parseLong(ParamsArray[1]);
                            Result = radomVariables.GetRadmomDouble(Start, End);
                        } catch (Exception exception) {
                            Result = "随机变量GetRadmomNum输入参数不合法，请填写最小和最大值数字范围";
                        }
                    }
                }
                if (Variablestype.equalsIgnoreCase("Guid")) {
                    Result = radomVariables.GetGuid();
                }
                if (Variablestype.equalsIgnoreCase("随机IP")) {
                    Result = radomVariables.GetRadmonIP();
                }
                if (Variablestype.equalsIgnoreCase("当前时间")) {
                    Result = radomVariables.GetCurrentTime();
                }
                if (Variablestype.equalsIgnoreCase("当前日期")) {
                    Result = radomVariables.GetCurrentDate(Params);
                }
                if (Variablestype.equalsIgnoreCase("当前时间戳")) {
                    Result = radomVariables.GetCurrentTimeMillis();
                }
            }
        }
        return Result;
    }

    //根据数据类型转换
    private Object GetDataByType(String Data, String ValueType) throws Exception {
        Object Result = new Object();
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
        if (ValueType.equalsIgnoreCase("String") || ValueType.isEmpty()) {
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
