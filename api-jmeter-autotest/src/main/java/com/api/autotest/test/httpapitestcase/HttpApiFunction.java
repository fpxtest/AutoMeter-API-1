package com.api.autotest.test.httpapitestcase;

import com.api.autotest.core.TestAssert;
import com.api.autotest.core.TestCore;
import com.api.autotest.core.TestCorebak;
import com.api.autotest.dto.ApicasesReportstatics;
import com.api.autotest.dto.RequestObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.*;

public class HttpApiFunction extends AbstractJavaSamplerClient {
    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
    }

    // 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        //定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
        params.addArgument("DispatchIds", "11");
        params.addArgument("mysqlurl", "/opt/");
        params.addArgument("mysqlusername", "/opt/");
        params.addArgument("mysqlpassword", "/opt/");
        return params;
    }

    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult results = new SampleResult();
        //Jmeter java实例开始执行
        results.sampleStart();
        //用例运行开始时间
        TestCorebak Core = new TestCorebak(ctx, getLogger());
        Map<String,List<RequestObject>> BatchRequestObjectMap = new HashMap<>();
        // 初始化用例数据
        BatchRequestObjectMap = InitalTestData(Core, ctx);

        for(String BatchName:BatchRequestObjectMap.keySet())
        {
            getLogger().info("BatchName 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:"+BatchName+" size is"+BatchRequestObjectMap.get(BatchName).size());
            if(BatchRequestObjectMap.get(BatchName).size()>0)
            {
                String TestPlanID=BatchRequestObjectMap.get(BatchName).get(0).getTestplanid();
                getLogger().info("TestPlanID 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:"+TestPlanID);
                Core.UpdateReportStatics(TestPlanID,BatchName,"运行中");
            }
            Map<String,List<RequestObject>> DeployUnitrequestObjectMap=GetGroupMap(BatchRequestObjectMap.get(BatchName),"DeployID");
            for(String DeployUnitID:DeployUnitrequestObjectMap.keySet())
            {
                ApicasesReportstatics apicasesReportstatics=new ApicasesReportstatics();
                getLogger().info("DeployUnitID 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:"+DeployUnitID+" size is:"+DeployUnitrequestObjectMap.get(DeployUnitID).size());
                apicasesReportstatics.setDeployunitid(DeployUnitID);
                int BatchDeployTotalCaseNums=DeployUnitrequestObjectMap.get(DeployUnitID).size();
                int BatchDeployTotalPassNums=0;
                int BatchDeployTotalFailNUms=0;
                long AllCostTime=0;
                // 发送用例请求，并返回结果
                for (RequestObject requestObject : DeployUnitrequestObjectMap.get(DeployUnitID)) {
                    getLogger().info("Deployid:"+DeployUnitID+" requestObject case id is 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:"+requestObject.getCaseid());
                    apicasesReportstatics.setTestplanid(requestObject.getTestplanid());
                    apicasesReportstatics.setTestplanname(requestObject.getTestplanname());
                    apicasesReportstatics.setSlaverid(requestObject.getSlaverid());
                    long Start = new Date().getTime();
                    //断言信息汇总
                    String AssertInfo = "";
                    String ErrorInfo = "";
                    String ActualResult = "";
                    TestAssert TestAssert = new TestAssert();
                    try {
                        ActualResult = SendCaseRequest(requestObject, Core);
                        String ResponeContentType = requestObject.getResponecontenttype();
                        if (ResponeContentType.equals(new String("json"))) {
                            AssertInfo = ParseJsonResult(Core, ActualResult, TestAssert,requestObject);
                        }
                        if (ResponeContentType.equals(new String("xml"))) {
                            //处理xml
                        }
                    } catch (Exception ex) {
                        ErrorInfo = CaseException(results, TestAssert, ex.getMessage());
                    } finally {
                        // 保存用例运行结果，Jmeter的sample运行结果
                        long End = new Date().getTime();
                        long CostTime=End-Start;
                        AllCostTime= AllCostTime+CostTime;
                        if(TestAssert.isCaseresult())
                        {
                            BatchDeployTotalPassNums=BatchDeployTotalPassNums+1;
                        }
                        else
                        {
                            BatchDeployTotalFailNUms=BatchDeployTotalFailNUms+1;
                        }
                        CaseFinish(Core, results, TestAssert, AssertInfo, CostTime, ErrorInfo, ActualResult,requestObject);
                    }
                }
                //收集本次运行的功能用例统计结果
                CollectionReportStatics(Core,apicasesReportstatics,BatchName, BatchDeployTotalCaseNums,BatchDeployTotalPassNums,BatchDeployTotalFailNUms,AllCostTime);
                //
            }
        }



        //Jmeter事务，表示这是事务的结束点
        results.sampleEnd();
        return results;
    }

    //初始化用例的基础数据
    private Map<String,List<RequestObject>> InitalTestData(TestCorebak core, JavaSamplerContext ctx)  {
        getLogger().info("根据调度ids获取请求数据列表 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
        List<RequestObject> objectList = core.GetDispatchOBList(ctx);
        Map<String,List<RequestObject>> BatchObject=GetGroupMap(objectList,"BatchName");
        return BatchObject;
    }


    private Map<String,List<RequestObject>> GetGroupMap(List<RequestObject> objectList,String ObjectName)
    {
        Map<String,List<RequestObject>> GroupObject=new HashMap<>();
        String KeyName="";
        for (RequestObject ob:objectList) {
            if(ObjectName.equals(new String("BatchName")))
            {
                KeyName= ob.getBatchname();
            }
            if(ObjectName.equals(new String("DeployID")))
            {
                KeyName=ob.getDeployunitid();
            }
            if(!GroupObject.containsKey(KeyName))
            {
                List<RequestObject> tmp=new ArrayList<>();
                tmp.add(ob);
                GroupObject.put(KeyName,tmp);
            }
            else
            {
                GroupObject.get(KeyName).add(ob);
            }
        }
        return GroupObject;
    }

    //用例发送请求
    private String SendCaseRequest(RequestObject ob, TestCorebak core) throws Exception {
        String Result = core.request(ob);
        getLogger().info(TestCore.logplannameandcasename + "请求结果 is:" + Result);
        return Result;
    }

    //用例运行过程中的异常信息处理
    private String CaseException(SampleResult results, TestAssert testAssert, String exceptionMessage) {
        // 断言用例运行结果为失败
        testAssert.setCaseresult(false);
        String ErrorInfo = exceptionMessage.replace("'", "");
        //end = new Date().getTime();
        getLogger().error(TestCore.logplannameandcasename + "用例执行发生异常，请检查!" + exceptionMessage);
        return ErrorInfo;
    }

    //用例运行结束保存记录，并且更新dispatch状态为完成
    private void CaseFinish(TestCorebak core, SampleResult results, TestAssert testAssert, String assertInfo, long time, String ErrorInfo, String ActualResult,RequestObject ob) {
        //jmeter java实例执行完成，记录结果
        results.setSuccessful(testAssert.isCaseresult());
        ActualResult = ActualResult.replace("'", "");
        assertInfo = assertInfo.replace("'", "");
        ErrorInfo = ErrorInfo.replace("'", "");
        core.savetestcaseresult(testAssert.isCaseresult(), time, ActualResult, assertInfo, ErrorInfo,ob);
        core.updatedispatchcasestatus(ob.getTestplanid(),ob.getBatchid(),ob.getSlaverid(),ob.getCaseid());
    }

    //功能用例统计收集信息
    private void CollectionReportStatics(TestCorebak core, ApicasesReportstatics apicasesReportstatics, String BatchName, int TotalCaseNums,int TotalPassNums,int TotalFailNUms,long AllCostTime) {
        apicasesReportstatics.setBatchname(BatchName);
        apicasesReportstatics.setTotalcases(String.valueOf(TotalCaseNums));
        apicasesReportstatics.setTotalpasscases(String.valueOf(TotalPassNums));
        apicasesReportstatics.setTotalfailcases(String.valueOf(TotalFailNUms));
        apicasesReportstatics.setRuntime(String.valueOf(AllCostTime));
        core.SaveReportStatics(apicasesReportstatics);
        //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
        core.PlanBatchAllDipatchFinish(apicasesReportstatics);
        getLogger().info("功能用例统计收集信息 完成。。。。。。。。。。。。。。。。");
    }

    //获取用例期望值
    private String getCaseExpectValue(TestCorebak core, String expectKey,HashMap<String,String> ExpectMap) throws Exception {
        String expectValue = core.GetExpectValue(expectKey,ExpectMap);
        getLogger().info(TestCore.logplannameandcasename + "expectValue is:" + expectValue);
        return expectValue;
    }

    //通过jsonpath获取实际值和期望值比较，返回断言信息
    private String ParseJsonResult(TestCorebak core, String ActualJson, TestAssert Ass,RequestObject ob) throws Exception {
        String AssertInfo = "";
        HashMap<String,String> ExpectMap=core.GetExpectMap(ob.getExpect());
        for (String JPath: ExpectMap.keySet()) {
            //获取期望值的status结果
            String ExpectValue = getCaseExpectValue(core,JPath,ExpectMap);
            //获取实际值使用jsonpath解析
            String ActualResult="";
            try {
                 ActualResult=JsonPath.read(ActualJson,JPath).toString();
            }
            catch (Exception ex)
            {
                ActualResult=ex.getMessage();
            }

            getLogger().info(TestCore.logplannameandcasename + "ExpectValue is:" + ExpectValue + "  ActualResult is:" + ActualResult);
            AssertInfo = Ass.AssertEqual(ExpectValue, ActualResult);
        }
        return AssertInfo;
    }

    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext ctx) {
        super.teardownTest(ctx);
    }

    // 本地调试
    public static void main(String[] args) {
        Arguments params = new Arguments();
        params.addArgument("DispatchIds", "3");
        params.addArgument("mysqlurl", "jdbc:mysql://127.0.0.1:3306/testcenter?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC");
        params.addArgument("mysqlusername", "root");
        params.addArgument("mysqlpassword", "root");

        JavaSamplerContext ctx = new JavaSamplerContext(params);
        HttpApiFunction test = new HttpApiFunction();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
