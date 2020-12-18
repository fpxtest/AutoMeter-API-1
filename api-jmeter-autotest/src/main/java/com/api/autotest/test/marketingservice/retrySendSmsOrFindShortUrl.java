package com.api.autotest.test.marketingservice;

import com.alibaba.fastjson.JSONObject;
import com.api.autotest.core.TestAssert;
import com.api.autotest.core.Testcore;
import com.api.autotest.dto.RequestObject;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;
import java.util.HashMap;

public class retrySendSmsOrFindShortUrl extends AbstractJavaSamplerClient {
    HashMap<String, String> expect = new HashMap<>();
    String errorinfo = "";
    String actualresult = "";
    Testcore core = null;
    RequestObject ob=null;
    long start = 0;
    long end = 0;

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        getLogger().info( "retrySendSmsOrFindShortUrl setupTest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:" );
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
        getLogger().info( "retrySendSmsOrFindShortUrl runTest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:" );
        SampleResult results = new SampleResult();
        results.sampleStart();
        String assertinfo = ""; //断言信息
        TestAssert testAssert= new TestAssert();
        core=new Testcore(getLogger());
        try {
                        // 获得请求响应
            start = new Date().getTime();
            ob =core.InitHttpDatabyJmeter(ctx);
            //获取期望值数据
            //expect = core.getExpectmap();
            getLogger().info(Testcore.logplannameandcasename + "开始请求.............");
            actualresult = core.request(ob);
            getLogger().info(Testcore.logplannameandcasename + "请求结果 is:" + actualresult);
            // 结束时间
            end = new Date().getTime();
            // 解析请求响应内容，使用期望值expect开始断言
            String expectcode = core.getExpectValue("code");
            getLogger().info(Testcore.logplannameandcasename + "expectcode is:" + expectcode);
            JSONObject jsonObject = JSONObject.parseObject(actualresult);

            String actualcode = jsonObject.get("code").toString();
            getLogger().info(Testcore.logplannameandcasename + "actualcode is:" + expectcode);
            // 完成期望值和实际值的比较代码，并且收集断言结果
            assertinfo = testAssert.AssertEqual(expectcode, actualcode);


            // 解析请求响应内容，使用期望值expect开始断言
            String expectmessage = core.getExpectValue("message");
            getLogger().info(Testcore.logplannameandcasename + "expectmessage is:" + expectmessage);
            JSONObject jsonObjectmessage = JSONObject.parseObject(actualresult);

            String actualmessage = jsonObjectmessage.get("message").toString();
            getLogger().info(Testcore.logplannameandcasename + "actualcode is:" + actualmessage);
            // 完成期望值和实际值的比较代码，并且收集断言结果
            assertinfo = testAssert.AssertEqual(expectmessage, actualmessage);


            // 解析请求响应内容，使用期望值expect开始断言
            String expectresult = core.getExpectValue("result");
            getLogger().info(Testcore.logplannameandcasename + "expectresult is:" + expectresult);
            JSONObject jsonObjectexpectresult = JSONObject.parseObject(actualresult);

            String actualresult = jsonObjectmessage.get("result").toString();
            getLogger().info(Testcore.logplannameandcasename + "actualresult is:" + actualresult);
            // 完成期望值和实际值的比较代码，并且收集断言结果
            assertinfo = testAssert.AssertEqual(expectresult, actualresult);

        } catch (Exception ex) {
            testAssert.setCaseresult(false);
            errorinfo = ex.getMessage().replace("'","");
            end = new Date().getTime();
            getLogger().error(Testcore.logplannameandcasename + "用例执行发生异常，请检查!" + ex.toString());
        } finally {
            // 保存用例运行结果，jmeter的sample运行结果
            results.setSuccessful(testAssert.isCaseresult());
            core.savetestcaseresult(testAssert.isCaseresult(), end - start, actualresult, assertinfo, errorinfo);
        }
        //定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction
        results.sampleEnd();
        return results;
    }

    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext ctx) {
        super.teardownTest(ctx);
        getLogger().info( "retrySendSmsOrFindShortUrl teardownTest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:" );
//        //后置条件
//        try {
//            core.fixpostcondition();
//        } catch (Exception e) {
//            getLogger().info(Testcore.logplannameandcasename + "后置条件处理发生异常，刷新用例状态为失败");
//            core.updatetestcaseresultfail(errorinfo+" 后置条件处理异常："+e.getMessage().replace("'",""));
//        }
//        getLogger().info(Testcore.logplannameandcasename + "处理后置条件完成");
//        //更新调度表状态
//        core.updatedispatchcasestatus();
//        //通知slaver性能测试解析报告，生成数据入库
//        try {
//            getLogger().info(Testcore.logplannameandcasename + "开始通知slaverservice处理性能结果");
//            core.genealperformacestaticsreport();
//            getLogger().info(Testcore.logplannameandcasename + "通知slaverservice处理性能结果完成");
//        } catch (Exception e) {
//            getLogger().info(Testcore.logplannameandcasename + "解析性能结果文件出错：" + e.getMessage());
//        }
    }


    public void checkpreconditionright() throws Exception {
        if (errorinfo != "") {
            throw new Exception(errorinfo);
        }
    }


    public void setuphelp(JavaSamplerContext context) {
//        core = new Testcore(getLogger());
//        //初始化用例请求数据
//        ob=core.InitHttpData(context);
//        getLogger().info(Testcore.logplannameandcasename + "数据库初始化完成");
//        //前置条件
//        try {
//            core.fixprecondition();
//        } catch (Exception e) {
//            errorinfo ="前置条件处理异常："+ e.getMessage().replace("'","");
//        }
//        getLogger().info(Testcore.logplannameandcasename + "处理前置条件完成");
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
        retrySendSmsOrFindShortUrl test = new retrySendSmsOrFindShortUrl();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
