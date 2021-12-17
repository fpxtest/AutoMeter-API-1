package com.zoctan.api.controller;

import com.alibaba.fastjson.JSON;
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
import com.zoctan.api.dto.ResponeGeneral;
import com.zoctan.api.dto.StaticsDataForPie;
import com.zoctan.api.dto.TestResponeData;
import com.zoctan.api.entity.*;
import com.zoctan.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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



    @PostMapping
    public Result add(@RequestBody Apicases apicases) {

        Condition con=new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
                .andCondition("apiname = '" + apicases.getApiname() + "'").andCondition("casename = '" + apicases.getCasename() + "'")
                ;
        //.orCondition("casejmxname = '" + apicases.getCasejmxname() + "'")
        if(apicasesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            apicasesService.save(apicases);
            return ResultGenerator.genOkResult();
        }
    }


    @PostMapping("/copycases")
    public Result Copycases(@RequestBody final Map<String, Object> param) {
        String sourcecaseid=param.get("sourcecaseid").toString();
        String sourcedeployunitid=param.get("sourcedeployunitid").toString();
        String sourcedeployunitname=param.get("sourcedeployunitname").toString();
        String newcasename=param.get("newcasename").toString();

        Condition con=new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitid = " + sourcedeployunitid)
                .andCondition("casename = '" + newcasename + "'");
        if(apicasesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult(sourcedeployunitname+"已存在存在此用例名");
        }
        else
        {
            Apicases Sourcecase =apicasesService.getBy("id",Long.parseLong(sourcecaseid));
            Condition apcasedatacon=new Condition(Apicases.class);
            apcasedatacon.createCriteria().andCondition("caseid = " + Long.parseLong(sourcecaseid));
            List<ApiCasedata> SourceApicasedataList= apiCasedataService.listByCondition(apcasedatacon);
            if(SourceApicasedataList.size()>0)
            {
                //复制用例
                Sourcecase.setDeployunitid(Long.parseLong(sourcedeployunitid));
                Sourcecase.setDeployunitname(sourcedeployunitname);
                Sourcecase.setCasename(newcasename);
                Sourcecase.setId(null);
                apicasesService.save(Sourcecase);
                Long NewCaseId= Sourcecase.getId();
                //复制用例数据
                for (ApiCasedata apiCasedata:SourceApicasedataList)
                {
                    apiCasedata.setCaseid(NewCaseId);
                    apiCasedata.setId(null);
                    apiCasedataService.save(apiCasedata);
                }
                //复制断言
                Condition AssertDataCondition=new Condition(ApicasesAssert.class);
                AssertDataCondition.createCriteria().andCondition("caseid = " + Long.parseLong(sourcecaseid));
                List<ApicasesAssert> SourceAssertdataList= apicasesAssertService.listByCondition(AssertDataCondition);
                for(ApicasesAssert apicasesAssert :SourceAssertdataList)
                {
                    apicasesAssert.setCaseid(NewCaseId);
                    apicasesAssert.setId(null);
                    apicasesAssertService.save(apicasesAssert);
                }
                return ResultGenerator.genOkResult();
            }
            else
            {
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
        List<StaticsDataForPie> result=new ArrayList<>();
        for (Apicases ac: list) {
            StaticsDataForPie staticsDataForPie =new StaticsDataForPie();
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
//        Condition con=new Condition(Apicases.class);
//        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
//                .andCondition("apiname = '" + apicases.getApiname() + "'")
//                .andCondition("id <> " + apicases.getId())
//                .andCondition("casename = '" + apicases.getCasename() + "'")
//                .orCondition("casejmxname = '" + apicases.getCasejmxname() + "'");
        if(apicasesService.forupdateifexist(apicases).size() >0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            this.apicasesService.updateApicase(apicases);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Apicases> list = this.apicasesService.findApiCaseWithName(param);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 根据发布单元id获取用例
     */
    @PostMapping("/getcasebydeployunitid")
    public Result getcasebydeployunitid(@RequestBody final Map<String, Object> param) {
        String deployunitid=param.get("sourcedeployunitid").toString();
        final List<Apicases> list = this.apicasesService.getcasebydeployunitid(Long.parseLong(deployunitid));
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchbyname")
    public Result searchbyname(@RequestBody final Map<String, Object> param) {
        String deployunitname=(String)param.get("casedeployunitname");
        String apiname=(String)param.get("caseapiname");
        final List<Apicases> list = this.apicasesService.getapicasebyName(deployunitname,apiname);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 运行测试
     */
    @PostMapping("/runtest")
    public Result runtest(@RequestBody final Map<String, Object> param) {
        String enviromentid=param.get("enviromentid").toString();
        Long Caseid=Long.parseLong(param.get("caseid").toString());
        Apicases apicases=apicasesService.getBy("id",Caseid);
        Long Apiid=apicases.getApiid();
        Api api = apiService.getBy("id", Apiid);
        if(api==null)
        {
            return ResultGenerator.genFailedResult("当前用例的API不存在，请检查是否被删除！");
        }
        String Method=api.getVisittype();
        String ApiStyle=api.getApistyle();
        String RequestContentType=api.getRequestcontenttype();
        Deployunit deployunit = deployunitService.getBy("id", api.getDeployunitid());
        if(deployunit==null)
        {
            return ResultGenerator.genFailedResult("当前用例的API所在的发布单元不存在，请检查是否被删除！");
        }
        String Protocal=deployunit.getProtocal();
        Macdepunit macdepunit = macdepunitService.getmacdepbyenvidanddepid(Long.parseLong(enviromentid), deployunit.getId());
        if (macdepunit != null) {
            String testserver = "";
            String resource = "";
            if (macdepunit.getVisittype().equals("ip")) {
                Long MachineID = macdepunit.getMachineid();
                Machine machine = machineService.getBy("id", MachineID);
                if(machine==null)
                {
                    return ResultGenerator.genFailedResult("当前环境中的服务器不存在，请检查是否被删除！");
                }
                testserver = machine.getIp();
                resource = deployunit.getProtocal() + "://" + testserver + ":" + deployunit.getPort() + api.getPath();
            } else {
                testserver = macdepunit.getDomain();
                resource = deployunit.getProtocal() + "://" + testserver + api.getPath();
            }

            //获取api参数类型，根据类型获取用例数据，并且检查类型对应是否完善了用例数据
            List<ApiParams> HeaderApiParams=apiParamsService.getApiParamsbypropertytype(api.getId(),"Header");
            List<ApiParams> ParamsApiParams =apiParamsService.getApiParamsbypropertytype(api.getId(),"Params");
            List<ApiParams> BodyApiParams=apiParamsService.getApiParamsbypropertytype(api.getId(),"Body");

            List<ApiCasedata>  HeaderApiCasedataList=apiCasedataService.getparamvaluebycaseidandtype(Caseid,"Header");
            List<ApiCasedata>  ParamsApiCasedataList=apiCasedataService.getparamvaluebycaseidandtype(Caseid,"Params");
            List<ApiCasedata>  BodyApiCasedataList=apiCasedataService.getparamvaluebycaseidandtype(Caseid,"Body");

            HttpHeader header = new HttpHeader();
            if(HeaderApiParams.size()>0)
            {
                if(HeaderApiCasedataList.size()==0)
                {
                    return ResultGenerator.genFailedResult("当前用例API的Header参数还未设计用例数据，请先完善后运行测试！");
                }
                else
                {
                    for (ApiCasedata Headdata:HeaderApiCasedataList) {
                        header.addParam(Headdata.getApiparam(),Headdata.getApiparamvalue());
                    }
                }
            }
            String PostData="";
            HttpParamers paramers=HttpParamers.httpPostParamers();
            if(ParamsApiParams.size()>0)
            {
                if(ParamsApiCasedataList.size()==0)
                {
                    return ResultGenerator.genFailedResult("当前用例API的Params参数还未设计用例数据，请先完善后运行测试！");
                }
                else
                {
                    for (ApiCasedata Paramdata:ParamsApiCasedataList) {
                        paramers.addParam(Paramdata.getApiparam(),Paramdata.getApiparamvalue());
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
            if(BodyApiParams.size()>0)
            {
                if (BodyApiCasedataList.size() > 0) {
                    //根据api请求数据类型处理，json，xml
                    String RequestDataType = api.getRequestcontenttype();
                    //获取冗余完整json,xml字段值
                    List<ApiParams> paramsList = apiParamsService.getApiParamsbypropertytype(api.getId(), "Body");
                    if(paramsList.size()==0)
                    {
                        return ResultGenerator.genFailedResult("当前用例API无Body参数，请检查是否被删除！");
                    }
                    try
                    {
                        if (RequestDataType.equals("json")) {
                            String JsonCompelete = paramsList.get(0).getKeynamebak();
                            DocumentContext documentContext = JsonPath.parse(JsonCompelete);
                            //获取参数的数据值
                            for (ApiCasedata apiCasedata : BodyApiCasedataList) {
                                String JsonPathStr = "$." + apiCasedata.getApiparam();
                                documentContext.set(JsonPathStr, apiCasedata.getApiparamvalue());
                            }
                            String ResultJson = documentContext.jsonString();
                            PostData=ResultJson;
                        }
                    }
                    catch (Exception ex)
                    {
                        return ResultGenerator.genFailedResult("当前用例API请求数据格式为Json，但是的Body参数类型不是Json格式，请修改后运行测试："+ex.getMessage());
                    }
                    if (RequestDataType.equals("xml")) {
                    }
                }
                else
                {
                    return ResultGenerator.genFailedResult("当前用例API的Body参数还未设计用例数据，请先完善后运行测试！");
                }
            }
            try {
                long Start = new Date().getTime();
                TestResponeData respon= TestRunHttphelp.doService(Protocal,resource,Method,ApiStyle,paramers, PostData, RequestContentType,header, 5000, 5000);
                long End = new Date().getTime();
                long CostTime=End-Start;
                respon.setResponeTime(CostTime);
                ResponeGeneral responeGeneral=new ResponeGeneral();
                responeGeneral.setApistyle(ApiStyle);
                responeGeneral.setMethod(Method);
                responeGeneral.setProtocal(Protocal);
                responeGeneral.setUrl(resource);
                respon.setResponeGeneral(responeGeneral);
                return ResultGenerator.genOkResult(respon);

            } catch (Exception exception) {
                return ResultGenerator.genOkResult(exception.getMessage());
            }
        }
        else
        {
            return ResultGenerator.genFailedResult("当前环境未部署此用例API所在的发布单元，请先完成环境下的部署！");
        }
    }
}
