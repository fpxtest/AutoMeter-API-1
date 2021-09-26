package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.*;
import com.zoctan.api.service.*;
import com.zoctan.api.service.TestPlanCaseService;
import com.zoctan.api.service.impl.TestPlanCaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Zoctan
 * @date 2020/04/17
 */
@Slf4j
@RestController
@RequestMapping("/exectestplancase")
public class TestPlanCaseController {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Autowired
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;
    @Autowired
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private DeployunitService deployunitService;
    @Autowired(required = false)
    private ApicasesMapper apicasesMapper;
    @Autowired(required = false)
    private TestPlanCaseService tpcservice;
    @Autowired(required = false)
    private ApicasesReportService apicasereportservice;
    @Autowired(required = false)
    private ApicasesService apicasesService;
    @Autowired(required = false)
    private ApiCasedataService apiCasedataService;
    @Autowired(required = false)
    private ApiParamsService apiParamsService;
    @Autowired(required = false)
    private ApiService apiService;
    @Autowired(required = false)
    private ExecuteplanService epservice;
    @Autowired(required = false)
    private  MacdepunitService macdepunitService;
    @Autowired(required = false)
    private MachineService machineService;
    @Autowired(required = false)
    private ApicasesReportPerformanceMapper apicasesReportPerformanceMapper;


    @PostMapping("/exec")
    //    public Result exec(@RequestBody List<TestplanCase> plancaseList) {
    public Result exec(@RequestBody Testplanandbatch planbatch) throws Exception {
        // 调用testcenter需要模拟下admin登录，调用Request URL: http://localhost:8080/account/token  {name: "admin", password: "admin123"}
        // 在请求头里面加上Authorization = token
        Long execplanid = planbatch.getPlanid();
        String batchname = planbatch.getBatchname();
        Executeplanbatch epb = executeplanbatchMapper.getbatchidbyplanidandbatchname(execplanid, batchname);
        // 检查plan当前的状态，如果状态为new，stop，finish继续执行
        Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
        List<ExecuteplanTestcase> caselist = executeplanTestcaseMapper.findcasebytestplanid(execplanid);
        TestPlanCaseController.log.info("计划id" + execplanid+" 批次为："+batchname+" 获取用例数："+caselist.size());
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        TestPlanCaseController.log.info("当前机器的IP为：" + ip);
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        List<Dispatch> dispatchList=new ArrayList<>();
        if (slaverlist.size() == 0) {
            TestPlanCaseController.log.info("当前执行机的IP为：" + ip + " 还未注册，请先完成注册");
            throw new Exception("当前执行机的IP为：" + ip + " 还未注册，请先完成注册");
        } else {
            Long slaverid = slaverlist.get(0).getId();
            String slavername = slaverlist.get(0).getSlavername();
            TestPlanCaseController.log.info("slaverid：" + slaverid + " slavername ："+slavername);
            for (ExecuteplanTestcase testcase : caselist) {
                //需要执行的用例，先进入调度，由调度定时器统一执行
                Dispatch dis = new Dispatch();
                dis.setExpect(testcase.getExpect());
                dis.setExecplanid(execplanid);
                dis.setTestcaseid(testcase.getTestcaseid());
                dis.setDeployunitname(testcase.getDeployunitname());
                dis.setStatus("待分配");
                dis.setBatchname(batchname);
                dis.setBatchid(epb.getId());
                dis.setCasejmxname(testcase.getCasejmxname());
                dis.setExecplanname(ep.getExecuteplanname());
                dis.setSlaverid(slaverid);
                dis.setSlavername(slavername);
                dis.setTestcasename(testcase.getCasename());
                dis.setPlantype(ep.getUsetype());
                dis.setThreadnum(testcase.getThreadnum());
                dis.setLoops(testcase.getLoops());
                dispatchList.add(dis);
                //dispatchService.save(dis);
            }
        }
        dispatchMapper.insertBatchDispatch(dispatchList);
        TestPlanCaseController.log.info("完成保存调度用例条数：" + dispatchList.size());
        return ResultGenerator.genOkResult();
    }

    public boolean jmeterclassexistornot(String jarpath, String jmeterclassname) {
        boolean flag = false;
        try {
            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
            File f = new File(jarpath);
            URL url1 = f.toURI().toURL();
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(jarpath);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while (enumFiles.hasMoreElements()) {
                entry = (JarEntry) enumFiles.nextElement();
                if (entry.getName().indexOf("META-INF") < 0) {
                    String classFullName = entry.getName();
                    if (classFullName.indexOf(".class") > 0) {
                        //去掉后缀.class
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        if (className.equals(jmeterclassname)) {
                            flag = true;
                        }
                        //打印类名
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~打印类名:~~~~~~~~~~~~~~~~~~~~~~~~~" + className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/test")
    public Result gettest(@RequestBody ExecuteplanTestcase plancase) {
        //tpcservice.executeplancase(plancase.getExecuteplanid(),plancase.getTestcaseid());
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/execperformancetest")
    public Result execperformancetest(@RequestBody Dispatch dispatch) throws Exception {

        String ip = null;
        InetAddress address = null;
        address = InetAddress.getLocalHost();
        ip = address.getHostAddress();
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        if (slaverlist.size() == 0) {
            TestPlanCaseController.log.error("性能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
            return ResultGenerator.genFailedResult("execperformancetest 未找到ip为：" + ip + "的slaver");
        }
        Long SlaverId = slaverlist.get(0).getId();
        slaverMapper.updateSlaverStaus(SlaverId, "运行中");

        String ProjectPath = System.getProperty("user.dir");
        String JmeterPath ="";
        String JmxPath="";
        String JmeterPerformanceReportPath="";
        if(ProjectPath.contains("slaverservice"))
        {
            JmeterPath = ProjectPath + "/apache-jmeter-5.3/bin";
            JmxPath = ProjectPath + "/servicejmxcase";
            JmeterPerformanceReportPath = ProjectPath + "/performancereport";
        }
        else
        {
            JmeterPath = ProjectPath + "/slaverservice/apache-jmeter-5.3/bin";
            JmxPath = ProjectPath + "/slaverservice/servicejmxcase";
            JmeterPerformanceReportPath = ProjectPath + "/slaverservice/performancereport";
        }

        File dir = new File(JmeterPerformanceReportPath);
        if (!dir.exists()) {// 判断目录是否存在
            dir.mkdir();
            TestPlanCaseController.log.info("创建性能报告目录performancereport完成 :" + JmeterPerformanceReportPath);
        }
        String JmxCaseName = dispatch.getCasejmxname();
        String DeployUnitName=dispatch.getDeployunitname();
        String CaseName = dispatch.getTestcasename();
        TestPlanCaseController.log.info("性能任务-执行多机并行性能用例名 is......." + CaseName);
        Deployunit Deployunit = deployunitService.findDeployNameValueWithCode(DeployUnitName);
        String Protocal = Deployunit.getProtocal();
        //如果是http,https，直接使用httpapitestcase下的functionhttpapi或者performancehttpapi来执行测试
        String JmeterClassName = "";
        String ClassName="";
        String DeployUnitNameForJmeter = "";
        if (Protocal.equals(new String("http")) || Protocal.equals(new String("https"))) {
            DeployUnitNameForJmeter = "httpapitestcase";
            JmeterClassName = "HttpApiPerformance";
            ClassName="com.api.autotest.test."+DeployUnitNameForJmeter+"."+JmeterClassName;
        }
        if (Protocal.equals(new String("rpc"))){
            DeployUnitNameForJmeter = dispatch.getDeployunitname();
            JmeterClassName = DeployUnitName ;
            ClassName="com.api.autotest.test."+DeployUnitName+"."+JmxCaseName;
        }
        Apicases apicases = apicasesMapper.getjmetername(dispatch.getTestcaseid());
        TestPlanCaseController.log.info("性能任务-DeployUnitNameForJmeter is......." + DeployUnitNameForJmeter+" JmeterClassName is........"+JmeterClassName);

        if (!JmeterClassExist(ClassName, JmeterPath)) {
            JmeterClassNotExist(dispatch, ClassName, CaseName);
            String memo=CaseName+"未开发对应的JmeterClass："+ClassName;
            dispatchMapper.updatedispatchstatusandmemo("调度异常",memo, dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
        } else {
            JmeterPerformanceObject jmeterPerformanceObject=null;
            try {
                 jmeterPerformanceObject= GetJmeterPerformance(dispatch);
            }
            catch (Exception ex)
            {
                dispatchMapper.updatedispatchstatusandmemo("调度异常",ex.getMessage(), dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
                TestPlanCaseController.log.info("性能任务-调度异常。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。获取 JmeterPerformanceObject对象异常报错..。。。。。。。。。。。。。。。。。。。。。。。。。" + ex.getMessage());
            }
            if(jmeterPerformanceObject!=null)
            {
                // 更新调度表对应用例状态为已分配
                dispatchMapper.updatedispatchstatus("已分配", dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
                TestPlanCaseController.log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。更新dispatch状态为已分配.....开始调用jmeter..。。。。。。。。。。。。。。。。。。。。。。。。。" + dispatch.getId());
                // 增加逻辑 获取计划的当前状态，如果为stop，放弃整个循环执行,return 掉
                tpcservice.ExecuteHttpPerformancePlanCase(jmeterPerformanceObject, DeployUnitNameForJmeter, JmeterPath, JmxPath, JmeterClassName, JmeterPerformanceReportPath,apicases.getThreadnum(), apicases.getLoops());
                //tpcservice.ExecuteHttpPerformancePlanCase(apicases.getCasetype(), dispatch.getSlaverid(), dispatch.getBatchid(), dispatch.getExecplanid(), dispatch.getTestcaseid(), apicases.getThreadnum(), apicases.getLoops(), DeployUnitNameForJmeter, JmeterPath, JmxPath, JmeterClassName, dispatch.getBatchname(), JmeterPerformanceReportPath, url, username, password);
                TestPlanCaseController.log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。调用jmeter完成..。。。。。。。。。。。。。。。。。。。。。。。。。"+ dispatch.getId());
            }
        }
        return ResultGenerator.genOkResult();
    }


    @PostMapping("/execfunctiontest")
    public Result execfunctiontest(@RequestBody List<Dispatch> dispatchList) throws Exception {
        String ip = null;
        InetAddress address = null;
        address = InetAddress.getLocalHost();
        ip = address.getHostAddress();
        List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
        if (slaverlist.size() == 0) {
            TestPlanCaseController.log.error("功能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
            return ResultGenerator.genFailedResult("execfunctiontest 未找到ip为：" + ip + "的slaver");
        }
        Long SlaverId = slaverlist.get(0).getId();
        try {
            slaverMapper.updateSlaverStaus(SlaverId, "运行中");
            String ProjectPath = System.getProperty("user.dir");
            String JmeterPath ="";
            String JmxPath="";
            if(ProjectPath.contains("slaverservice"))
            {
                 JmeterPath = ProjectPath + "/apache-jmeter-5.3/bin";
                 JmxPath = ProjectPath + "/servicejmxcase";
            }
            else
            {
                 JmeterPath = ProjectPath + "/slaverservice/apache-jmeter-5.3/bin";
                 JmxPath = ProjectPath + "/slaverservice/servicejmxcase";
            }
            TestPlanCaseController.log.info("功能任务-获取待执行的功能用例数：。。。。。。。。。。。。。。。。。。。。。。。。" + dispatchList.size());
            int FunctionJmeter = GetJmeterProcess("FunctionJmeterProcess", "功能");

            HashMap<String, List<Dispatch>> ProtocolDispatchRun = GetProtocolDispatch(dispatchList);
            for (String Protocol : ProtocolDispatchRun.keySet()) {
                int ProtocolJmeterNum = 0;
                if (FunctionJmeter == 1) {
                    ProtocolJmeterNum = 1;
                } else {
                    ProtocolJmeterNum = FunctionJmeter / ProtocolDispatchRun.size();
                }
                TestPlanCaseController.log.info("功能任务-ProtocolJmeterNum：。。。。。。。。" + ProtocolJmeterNum);
                List<List<Dispatch>> last = FunctionDispatch(ProtocolJmeterNum, ProtocolDispatchRun.get(Protocol));
                if (Protocol.equals(new String("http"))) {
                    for (int i=0;i< last.size();i++) {
                        List<Dispatch> JmeterList=last.get(i);
                        String DispatchIDs = "";
                        for (Dispatch dis : JmeterList) {
                            DispatchIDs = dis.getId() + "," + DispatchIDs;
                            dispatchMapper.updatedispatchstatus("已分配", dis.getSlaverid(), dis.getExecplanid(), dis.getBatchid(), dis.getTestcaseid());
                            TestPlanCaseController.log.info("功能任务-更新调度状态为已分配：。。。。。。。。" + dis.getId());
                        }
                        if (!DispatchIDs.isEmpty()) {
                            DispatchIDs = DispatchIDs.substring(0, DispatchIDs.length() - 1);
                            TestPlanCaseController.log.info("功能任务-DispatchIDs:=======================" + DispatchIDs);
                            tpcservice.ExecuteHttpPlanFunctionCase( SlaverId,JmeterPath, JmxPath, DispatchIDs, url, username, password,i);
                        }
                    }
//                        for (List<Dispatch> JmeterList : last) {
//                        String DispatchIDs = "";
//                        for (Dispatch dis : JmeterList) {
//                            DispatchIDs = dis.getId() + "," + DispatchIDs;
//                            dispatchMapper.updatedispatchstatus("已分配", dis.getSlaverid(), dis.getExecplanid(), dis.getBatchid(), dis.getTestcaseid());
//                            TestPlanCaseController.log.info("功能任务-更新调度状态为已分配：。。。。。。。。" + dis.getId());
//                        }
//                        if (!DispatchIDs.isEmpty()) {
//                            DispatchIDs = DispatchIDs.substring(0, DispatchIDs.length() - 1);
//                            TestPlanCaseController.log.info("功能任务-DispatchIDs:=======================" + DispatchIDs);
//                            tpcservice.ExecuteHttpPlanFunctionCase(SlaverId,JmeterPath, JmxPath, DispatchIDs, url, username, password);
//                        }
//                    }
                }
                if (Protocol.equals(new String("rpc"))) {
                    String JmeterClassName = "";
                    String DeployUnitNameForJmeter = "";
                }
            }
        } catch (Exception ex) {
            slaverMapper.updateSlaverStaus(SlaverId, "空闲");
            TestPlanCaseController.log.error("功能任务-execfunctiontest 异常:=======================" + ex.getMessage());
            return ResultGenerator.genFailedResult(ex.getMessage());
        }
        return ResultGenerator.genOkResult();
    }


    public JmeterPerformanceObject GetJmeterPerformance(Dispatch dispatch)
    {
        JmeterPerformanceObject jmeterPerformanceObject=new JmeterPerformanceObject();
        jmeterPerformanceObject.setTestplanid(dispatch.getExecplanid());
        jmeterPerformanceObject.setCaseid(dispatch.getTestcaseid());
        jmeterPerformanceObject.setSlaverid(dispatch.getSlaverid());
        jmeterPerformanceObject.setBatchid(dispatch.getBatchid());
        jmeterPerformanceObject.setCasename(dispatch.getTestcasename());
        jmeterPerformanceObject.setBatchname(dispatch.getBatchname());
        jmeterPerformanceObject.setExecuteplanname(dispatch.getExecplanname());
        Apicases apicases=apicasesService.getBy("id",dispatch.getTestcaseid());
        jmeterPerformanceObject.setExpect(apicases.getExpect());
        jmeterPerformanceObject.setCasetype(apicases.getCasetype());
        Api api=apiService.getBy("id",apicases.getApiid());
        jmeterPerformanceObject.setApistyle(api.getApistyle());
        jmeterPerformanceObject.setRequestmMthod(api.getVisittype());

        jmeterPerformanceObject.setRequestcontenttype(api.getRequestcontenttype());
        jmeterPerformanceObject.setResponecontenttype(api.getResponecontenttype());
        Deployunit deployunit=deployunitService.getBy("id",api.getDeployunitid());
        jmeterPerformanceObject.setProtocal(deployunit.getProtocal());

        Executeplan executeplan=epservice.getBy("id",dispatch.getExecplanid());
        Long EnvID= executeplan.getEnvid();
        Macdepunit macdepunit= macdepunitService.getmacdepbyenvidanddepid(EnvID,deployunit.getId());
        if(macdepunit!=null)
        {
            String testserver = "";
            String resource = "";
            if(macdepunit.getVisittype().equals(new String("ip")))
            {
                Long MachineID=macdepunit.getMachineid();
                Machine machine= machineService.getBy("id",MachineID);
                testserver = machine.getIp();
                resource = deployunit.getProtocal() + "://" + testserver + ":" + deployunit.getPort() + api.getPath();
            }
            else
            {
                testserver= macdepunit.getDomain();
                resource = deployunit.getProtocal() + "://" + testserver  + api.getPath();
            }
            jmeterPerformanceObject.setResource(resource);
        }
        List<ApiCasedata> apiCasedataList=apiCasedataService.getcasedatabycaseid(dispatch.getTestcaseid());

        HashMap<String,String> HeaderMap=new HashMap<>();
        HashMap<String,String> ParamsMap=new HashMap<>();
        HashMap<String,String> BodyMap=new HashMap<>();
        for(ApiCasedata apiCasedata:apiCasedataList)
        {
            if(apiCasedata.getPropertytype().equals(new String("Params")))
            {
                ParamsMap.put(apiCasedata.getApiparam(),apiCasedata.getApiparamvalue());
            }
            if(apiCasedata.getPropertytype().equals(new String("Header")))
            {
                HeaderMap.put(apiCasedata.getApiparam(),apiCasedata.getApiparamvalue());
            }
            if(apiCasedata.getPropertytype().equals(new String("Body")))
            {
                BodyMap.put(apiCasedata.getApiparam(),apiCasedata.getApiparamvalue());
            }
        }
        if(HeaderMap.size()>0)
        {
            jmeterPerformanceObject.setHeadjson( JSON.toJSONString(HeaderMap));
        }
        else
        {
            List<ApiParams> paramsList= apiParamsService.getApiParamsbypropertytype(api.getId(),"Header");
            //API是否有参数，有参数无用例数据表示用例数据不完整
            if(paramsList.size()>0)
            {
                jmeterPerformanceObject.setHeadjson("nocasedatas");
            }
            //API是否有参数，无参数无用例数据，表示无Header
            else
            {
                jmeterPerformanceObject.setHeadjson("headjson");
            }
        }
        if(ParamsMap.size()>0)
        {
            jmeterPerformanceObject.setParamsjson( JSON.toJSONString(ParamsMap));
        }
        else
        {
            List<ApiParams> paramsList= apiParamsService.getApiParamsbypropertytype(api.getId(),"Params");
            if(paramsList.size()>0)
            {
                jmeterPerformanceObject.setParamsjson("nocasedatas");
            }
            else
            {
                jmeterPerformanceObject.setParamsjson("paramjson");
            }
        }
        if(BodyMap.size()>0)
        {
            jmeterPerformanceObject.setBodyjson( JSON.toJSONString(BodyMap));
        }
        else
        {
            List<ApiParams> paramsList= apiParamsService.getApiParamsbypropertytype(api.getId(),"Body");
            if(paramsList.size()>0)
            {
                jmeterPerformanceObject.setBodyjson("nocasedatas");
            }
            else
            {
                jmeterPerformanceObject.setBodyjson("bodyjson");
            }
        }
        jmeterPerformanceObject.setMysqlurl(url);
        jmeterPerformanceObject.setMysqlusername(username);
        jmeterPerformanceObject.setMysqlpassword(password);
        return jmeterPerformanceObject;
    }

    public void JmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
        ApicasesReport ar = new ApicasesReport();
        ar.setTestplanid(dis.getExecplanid());
        ar.setCaseid(dis.getTestcaseid());
        ar.setCasename(dis.getTestcasename());
        ar.setErrorinfo("性能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
        ar.setBatchname(dis.getBatchname());
        ar.setExpect(dis.getExpect());
        ar.setStatus("失败");
        ar.setSlaverid(dis.getSlaverid());
        ar.setRuntime(new Long(0));
        Long planid = dis.getExecplanid();

        apicasesReportPerformanceMapper.addcasereport(ar);
        //epservice.updatetestplanstatus(planid, "fail");
        //PerformanceDispatchScheduleTask.log.info("性能任务-未找到用例对应的jmeter-class类......." + jmeterclassname);
    }

    public boolean JmeterClassExist(String jmeterclassname, String JmeterPath) {
        String JmeterExtJarPath = JmeterPath.replace("bin", "lib");
        String JarPath = JmeterExtJarPath + "/ext/api-jmeter-autotest-1.0.jar";
        boolean flag = false;
        try {
            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
            File f = new File(JarPath);
            URL url1 = f.toURI().toURL();
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(JarPath);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while (enumFiles.hasMoreElements()) {
                entry = (JarEntry) enumFiles.nextElement();
                if (entry.getName().indexOf("META-INF") < 0) {
                    String classFullName = entry.getName();
                    if (classFullName.indexOf(".class") > 0) {
                        //去掉后缀.class
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        if (className.equals(jmeterclassname)) {
                            flag = true;
                        }
                        //打印类名
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public void FunJmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
        ApicasesReport ar = new ApicasesReport();
        ar.setTestplanid(dis.getExecplanid());
        ar.setCaseid(dis.getTestcaseid());
        ar.setCasename(dis.getTestcasename());
        ar.setErrorinfo("功能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
        ar.setBatchname(dis.getBatchname());
        ar.setExpect(dis.getExpect());
        ar.setStatus("失败");
        ar.setRuntime(new Long(0));
        Long planid = dis.getExecplanid();

        apicasereportservice.addcasereport(ar);
        epservice.updatetestplanstatus(planid, "fail");
        TestPlanCaseController.log.info("功能任务-未找到用例对应的jmeter-class类......." + jmeterclassname);
    }

    //功能用例平均分配
    public List<List<Dispatch>> FunctionDispatch(int Jmeternums, List<Dispatch> dispatchList) {
        if (dispatchList.size() < Jmeternums) {
            Jmeternums = dispatchList.size();
        }
        List<List<Dispatch>> LastDispatchList = new ArrayList<List<Dispatch>>();
        List<Dispatch> splitdispatchList;
        int sizemode = (dispatchList.size()) / Jmeternums;
        int sizeleft = (dispatchList.size()) % Jmeternums;
        int j = 0;
        int x = 0;
        for (int i = 0; i < Jmeternums; i++) {
            splitdispatchList = new ArrayList<Dispatch>();
            for (j = x; j < (sizemode + x); j++) {
                Dispatch dis = dispatchList.get(j);
                splitdispatchList.add(dis);
            }
            x = j;
            LastDispatchList.add(splitdispatchList);
        }
        if (sizeleft != 0) {
            for (int y = 1; y < sizeleft + 1; y++) {
                Dispatch dis = dispatchList.get(dispatchList.size() - y);
                LastDispatchList.get(LastDispatchList.size() - 1).add(dis);
            }
        }
        return LastDispatchList;
    }


    public int GetJmeterProcess(String DictionaryCode, String DicType) {
        List<Dictionary> slavermaxfunthreaddic = dictionaryMapper.findDicNameValueWithCode(DictionaryCode);

        int JmeterProcess = 1;
        //字典表未配置，默认取一条
        if (slavermaxfunthreaddic.size() == 0) {
            TestPlanCaseController.log.info("功能任务-字典表未配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
        } else {
            String slavermaxthread = slavermaxfunthreaddic.get(0).getDicitmevalue();
            try {
                JmeterProcess = Integer.valueOf(slavermaxthread);
            } catch (Exception ex) {
                TestPlanCaseController.log.error("功能任务-字典表未正确配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
            }
            TestPlanCaseController.log.info("功能任务-获取字典表slaver并发执行jmerter进程个数：。。。。。。。。" + slavermaxthread);
        }
        return JmeterProcess;
    }

    public HashMap<String, List<Dispatch>> GetProtocolDispatch(List<Dispatch> dispatchList) {
        List<Dispatch> dispatchResultList = new ArrayList<>();
        HashMap<Long, List<Dispatch>> GroupDispatch = new HashMap<Long, List<Dispatch>>();

        //获取计划id分组中的第一组列表
        for (Dispatch dispatch : dispatchList) {
            Long planid = dispatch.getExecplanid();
            if (!GroupDispatch.containsKey(planid)) {
                List<Dispatch> dispatchListtmp = new ArrayList<>();
                dispatchListtmp.add(dispatch);
                GroupDispatch.put(planid, dispatchListtmp);
            } else {
                GroupDispatch.get(planid).add(dispatch);
            }
        }
        for (Long planid : GroupDispatch.keySet()) {
            dispatchResultList = GroupDispatch.get(planid);
            break;
        }

        //获取发布单元分组列表
        HashMap<String, List<Dispatch>> DeployUnitGroupDispatch = new HashMap<String, List<Dispatch>>();
        for (Dispatch dispatch : dispatchResultList) {
            String DeployUnit = dispatch.getDeployunitname();
            if (!DeployUnitGroupDispatch.containsKey(DeployUnit)) {
                List<Dispatch> dispatchListtmp = new ArrayList<>();
                dispatchListtmp.add(dispatch);
                DeployUnitGroupDispatch.put(DeployUnit, dispatchListtmp);
            } else {
                DeployUnitGroupDispatch.get(DeployUnit).add(dispatch);
            }
        }

        //合并协议列表
        HashMap<String, List<Dispatch>> ProtocolGroupDispatch = new HashMap<String, List<Dispatch>>();

        for (String DeployUnit : DeployUnitGroupDispatch.keySet()) {
            Deployunit deployunit = deployunitService.findDeployNameValueWithCode(DeployUnit);
            String Protocal = deployunit.getProtocal();
            if (Protocal.equals(new String("http")) || Protocal.equals(new String("https"))) {
                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "http");
            }
            if (Protocal.equals(new String("rpc"))) {
                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "rpc");
            }
        }
        return ProtocolGroupDispatch;
    }

    public HashMap<String, List<Dispatch>> MergeCaseList(HashMap<String, List<Dispatch>> ProtocolGroupDispatch, HashMap<String, List<Dispatch>> DeployUnitGroupDispatch, String DeployUnit, String Protocol) {
        HashMap<String, List<Dispatch>> ProtocolGroupResultDispatch = ProtocolGroupDispatch;
        if (!ProtocolGroupResultDispatch.containsKey(Protocol)) {
            List<Dispatch> dispatchListtmp = new ArrayList<>();
            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
                dispatchListtmp.add(dis);
            }
            ProtocolGroupResultDispatch.put(Protocol, dispatchListtmp);
        } else {
            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
                ProtocolGroupResultDispatch.get(Protocol).add(dis);
            }
        }
        return ProtocolGroupResultDispatch;
    }


}

