package com.api.autotest.test.helloworldservice;

import com.alibaba.fastjson.JSONObject;
import com.api.autotest.core.TestAssert;
import com.api.autotest.core.TestCore;
import com.api.autotest.dto.RequestObject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;

public class HelloWorld extends AbstractJavaSamplerClient {
    //测试核心
    private TestCore core;
    //API请求数据对象
    private RequestObject ob;
    //运行用例错误信息
    private String errorInfo="";
    //运行API用例返回值
    private String actualResult="";
    //用例运行开始时间
    private long start = 0;
    //用例运行结束时间
    private long end = 0;

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
        return params;
    }

    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult results = new SampleResult();
        //Jmeter java实例开始执行
        results.sampleStart();
        //用例多次断言信息汇总
        String assertInfo = "";
        //断言对象
        TestAssert testAssert = new TestAssert();
        try {
            // 初始化用例数据
            initalTestData(ctx);
            // 发送用例请求，并返回结果
            actualResult=sendCaseRequest();
            // ===========================用例断言区，新开发一个用例，需要在此编写用例断言======================================
            // 此例子返回类型为json格式，把请求返回值actualResult转换成JSONObject对象，新的用例开发根据实际返回类型做相应断言处理
            JSONObject actualResultObject = JSONObject.parseObject(actualResult);
            // ---------------断言status步骤开始-------------------------------------
            //获取期望值的status结果
            String expectStatus = getCaseExpectValue("status");
            //获取实际值status结果
            String actualStatus = actualResultObject.get("status").toString();
            //日志记录实际值
            getLogger().info(TestCore.logplannameandcasename + "actualStatus is:" + actualStatus);
            // 完成期望值status和实际值status的比较，并且收集断言结果到assertInfo中
            assertInfo = testAssert.AssertEqual(expectStatus, actualStatus);
            // ---------------断言status步骤结束-------------------------------------

            // ---------------断言msg步骤开始----------------------------------------
            //获取期望值的msg结果
            String expectMsg = getCaseExpectValue("msg");
            //获取实际值msg结果
            String actualMsg = actualResultObject.get("msg").toString();
            //日志记录实际值
            getLogger().info(TestCore.logplannameandcasename + "actualMsg is:" + actualMsg);
            // 完成期望值和实际值msg的比较，并且收集断言结果到assertInfo中
            assertInfo = testAssert.AssertEqual(expectMsg, actualMsg);
            // ---------------断言msg步骤结束----------------------------------------
            // ===========================用例断言区========================================================================
        } catch (Exception ex) {
            caseException(results, testAssert, ex.getMessage());
        } finally {
            // 保存用例运行结果，Jmeter的sample运行结果
            caseFinish(results, testAssert, assertInfo);
        }
        //Jmeter事务，表示这是事务的结束点
        results.sampleEnd();
        return results;
    }

    //初始化用例的基础数据
    private void initalTestData(JavaSamplerContext ctx) throws Exception {
        getLogger().info("Hello World runTest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
        core = new TestCore(getLogger());
        ob = core.InitHttpDatabyJmeter(ctx);
        //用例开始运行时间
        start = new Date().getTime();
    }

    //用例发送请求
    private String sendCaseRequest() throws Exception {
        String Result = core.request(ob);
        getLogger().info(TestCore.logplannameandcasename + "请求结果 is:" + actualResult);
        // 用例结束时间
        end = new Date().getTime();
        return Result;
    }

    //用例运行过程中的异常信息处理
    private void caseException(SampleResult results, TestAssert testAssert, String exceptionMessage) {
        // 断言用例运行结果为失败
        testAssert.setCaseresult(false);
        errorInfo = exceptionMessage.replace("'", "");
        end = new Date().getTime();
        getLogger().error(TestCore.logplannameandcasename + "用例执行发生异常，请检查!" + exceptionMessage);
    }

    //用例运行结束收集信息
    private void caseFinish(SampleResult results, TestAssert testAssert, String assertInfo) {
        //jmeter java实例执行完成，记录结果
        results.setSuccessful(testAssert.isCaseresult());
        core.savetestcaseresult(testAssert.isCaseresult(), end - start, actualResult, assertInfo, errorInfo);
    }

    //获取用例期望值
    private String getCaseExpectValue(String expectKey) throws Exception {
        String expectValue = core.getExpectValue(expectKey);
        getLogger().info(TestCore.logplannameandcasename + "expectValue is:" + expectValue);
        return expectValue;
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
        params.addArgument("batchid", "1");
        params.addArgument("slaverid", "5");
        params.addArgument("batchname", "xxx10000");

        params.addArgument("executeplanname", "兑换服务性能测试");
        params.addArgument("casename", "获取用户信息性能");
        params.addArgument("expect", "code=0,message= Success, result=http://b6i.cn/4Vgxk");
        params.addArgument("protocal", "https");
        params.addArgument("RequestmMthod", "get");
        params.addArgument("casetype", "性能");
        params.addArgument("resource", "https://marketing-ui.confucius.mobi/redeem/ui/retrySendSmsOrFindShortUrl");
        params.addArgument("apistyle", "普通方式");
        params.addArgument("requestcontenttype", "");
        params.addArgument("responecontenttype", "json");
        params.addArgument("headjson", "");
        params.addArgument("paramsjson", "{\"orderId\":\"2012030033263636700\",\"goodsThirdPartyType\":\"ALIPAY\",\"status\":\"OFF\"}");
        params.addArgument("bodyjson", "");
        params.addArgument("dubbojson", "");
        JavaSamplerContext ctx = new JavaSamplerContext(params);
        HelloWorld test = new HelloWorld();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
