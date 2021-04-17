package com.api.autotest.test.httpapitestcase;

import com.api.autotest.core.TestAssert;
import com.api.autotest.core.TestCore;
import com.api.autotest.core.TestCorebak;
import com.api.autotest.dto.RequestObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;
import java.util.HashMap;

public class HttpApiPerformance extends AbstractJavaSamplerClient {
    //测试核心
    //private TestCore core;
    //API请求数据对象
    //private RequestObject ob;
    //运行用例错误信息
    //private String errorInfo="";
    //运行API用例返回值
    //private String ActualResult="";
    //用例运行开始时间
    //private long start = 0;
    //用例运行结束时间
    //private long end = 0;

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
    }

    // 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        //定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
        params.addArgument("testplanid", "11");
        params.addArgument("caseid", "15");
        params.addArgument("batchid", "11");
        params.addArgument("slaverid", "15");
        params.addArgument("batchname", "cornerservice2020-10-21-tag-100");
        params.addArgument("executeplanname", "11");
        params.addArgument("casename", "11");
        params.addArgument("expect", "/opt/");
        params.addArgument("protocal", "11");
        params.addArgument("RequestmMthod", "11");
        params.addArgument("casetype", "11");
        params.addArgument("resource", "15");
        params.addArgument("apistyle", "11");
        params.addArgument("requestcontenttype", "15");
        params.addArgument("responecontenttype", "cornerservice2020-10-21-tag-100");
        params.addArgument("headjson", "/opt/");
        params.addArgument("paramsjson", "/opt/");
        params.addArgument("bodyjson", "/opt/");
        params.addArgument("dubbojson", "/opt/");
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
        long Start = new Date().getTime();
        //断言信息汇总
        String AssertInfo = "";
        String ErrorInfo="";
        String ActualResult="";
        TestCorebak Core = new TestCorebak(ctx,getLogger());
        RequestObject requestObject=null;
        TestAssert TestAssert = new TestAssert();
        try {
            // 初始化用例数据
            requestObject=InitalTestData(Core,ctx);
            // 发送用例请求，并返回结果
            ActualResult=SendCaseRequest(requestObject,Core);
            String ResponeContentType=requestObject.getResponecontenttype();
            if(ResponeContentType.equals(new String("json")))
            {
                AssertInfo= ParseJsonResult(Core,ActualResult,TestAssert,requestObject);
            }
            if(ResponeContentType.equals(new String("xml")))
            {
                //处理xml
            }
        } catch (Exception ex) {
            ErrorInfo=CaseException(results, TestAssert, ex.getMessage());
        } finally {
            // 保存用例运行结果，Jmeter的sample运行结果
            long End = new Date().getTime();
            CaseFinish(Core,results, TestAssert, AssertInfo,End-Start,ErrorInfo,ActualResult,requestObject);
        }
        //Jmeter事务，表示这是事务的结束点
        results.sampleEnd();
        return results;
    }

    //初始化用例的基础数据
    private RequestObject InitalTestData(TestCorebak core,JavaSamplerContext ctx) throws Exception {
        getLogger().info("Hello World runTest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
        //core = new TestCore(getLogger());
        RequestObject ob = core.InitHttpDatabyJmeter(ctx);
        //用例开始运行时间
        //start = new Date().getTime();
        return ob;
    }

    //用例发送请求
    private String SendCaseRequest(RequestObject ob,TestCorebak core) throws Exception {
        String Result = core.request(ob);
        getLogger().info(TestCore.logplannameandcasename + "请求结果 is:" + Result);
        // 用例结束时间
        //end = new Date().getTime();
        return Result;
    }

    //用例运行过程中的异常信息处理
    private String CaseException(SampleResult results, TestAssert testAssert, String exceptionMessage) {
        // 断言用例运行结果为失败
        results.setSuccessful(false);
        testAssert.setCaseresult(false);
        String ErrorInfo = exceptionMessage.replace("'", "");
        //end = new Date().getTime();
        getLogger().error(TestCore.logplannameandcasename + "用例执行发生异常，请检查!" + exceptionMessage);
        return ErrorInfo;
    }

    //用例运行结束收集信息
    private void CaseFinish(TestCorebak core, SampleResult results, TestAssert testAssert, String assertInfo,long time,String ErrorInfo,String ActualResult,RequestObject ob) {
        //jmeter java实例执行完成，记录结果
        results.setSuccessful(testAssert.isCaseresult());
        core.savetestcaseresult(testAssert.isCaseresult(), time, ActualResult, assertInfo, ErrorInfo,ob);
    }

    //获取用例期望值
    private String getCaseExpectValue(TestCorebak core,String expectKey,HashMap<String,String> ExpectMap) throws Exception {
        String expectValue = core.GetExpectValue(expectKey,ExpectMap);
        getLogger().info(TestCore.logplannameandcasename + "expectValue is:" + expectValue);
        return expectValue;
    }

    //通过jsonpath获取实际值和期望值比较，返回断言信息
    private String ParseJsonResult(TestCorebak core,String ActualJson,TestAssert Ass,RequestObject ob) throws Exception {
        String AssertInfo="";
        HashMap<String,String> ExpectMap=core.GetExpectMap(ob.getExpect());
        for (String JPath: ExpectMap.keySet()) {
            //获取期望值的status结果
            String ExpectValue = getCaseExpectValue(core,JPath,ExpectMap);
            //获取实际值使用jsonpath解析

            String ActualResult=JsonPath.read(ActualJson,JPath).toString();
            getLogger().info(TestCore.logplannameandcasename +"ExpectValue is:" + ExpectValue + "  ActualResult is:" + ActualResult);
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
        params.addArgument("testplanid", "12");
        params.addArgument("caseid", "1");
        params.addArgument("batchid", "41");
        params.addArgument("slaverid", "5");
        params.addArgument("batchname", "xxxxxxxxxxxxxxxxxxxxx");

        params.addArgument("executeplanname", "兑换服务回归测试");
        params.addArgument("casename", "获取用户信息正确");
        params.addArgument("expect", "zxzxz");
        params.addArgument("protocal", "https");
        params.addArgument("RequestmMthod", "get");
        params.addArgument("casetype", "功能");
        params.addArgument("resource", "https://marketing-ui.confucius.mobi/redeem/ui/retrySendSmsOrFindShortUrl");
        params.addArgument("apistyle", "普通方式");
        params.addArgument("requestcontenttype", "");
        params.addArgument("responecontenttype", "json");
        params.addArgument("headjson", "headjson");
        params.addArgument("paramsjson", "{\"goodsThirdPartyType\":\"ALIPAY\"}");
        params.addArgument("bodyjson", "");
        params.addArgument("dubbojson", "");

        params.addArgument("mysqlurl", "jdbc:mysql://127.0.0.1:3306/testcenter?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC");
        params.addArgument("mysqlusername", "root");
        params.addArgument("mysqlpassword", "root");

        JavaSamplerContext ctx = new JavaSamplerContext(params);
        HttpApiPerformance test = new HttpApiPerformance();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
