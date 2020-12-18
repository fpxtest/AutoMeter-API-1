package com.api.autotest.test.cornerservice;

import com.alibaba.fastjson.JSONObject;
import com.api.autotest.core.Testcore;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;
import java.util.HashMap;

public class getInfoByLocationtestlalo extends AbstractJavaSamplerClient {
    HashMap<String, String> expect = new HashMap<>();
    String assertinfo =""; //断言信息
    Testcore core = null;

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        core = new Testcore(getLogger());
        //初始化用例请求数据
        try {
            core.InitHttpData(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLogger().info(Testcore.logplannameandcasename+"数据初始化完成");
        //前置条件
        getLogger().info(Testcore.logplannameandcasename+"开始处理前置条件" );
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
        return params;
    }

    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext ctx) {
        long start=0;
        long end = 0;
        String errorinfo="";
        String actualresult = "";
        SampleResult results = new SampleResult();
        results.sampleStart();
        try {
//          获取期望值数据
            //expect = core.getExpectmap();
            start = new Date().getTime();
            getLogger().info(Testcore.logplannameandcasename + "开始请求.............");
            //actualresult = core.request();
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
           // assertinfo = TestAssert.AssertEqual(actualcode, expectcode);
        } catch (Exception ex) {
           // TestAssert.caseresult = false;
            errorinfo = ex.getMessage();
            end = new Date().getTime();
            getLogger().error(Testcore.logplannameandcasename + "用例在断言处代码发生异常，请检查!" + ex.toString());
        } finally {
            // 保存用例运行结果，jmeter的sample运行结果
          //  results.setSuccessful(TestAssert.caseresult);
          //  core.savetestcaseresult(TestAssert.caseresult, end - start, actualresult, assertinfo,errorinfo);
          //  core.updatedispatchcasestatus();
        }
        //定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction
        results.sampleEnd();
        return results;
    }

    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext ctx) {
        super.teardownTest(ctx);
        //后置条件
        getLogger().info(Testcore.logplannameandcasename+"开始处理后置条件" );
    }

    // 本地调试
    public static void main(String[] args) {
        Arguments params = new Arguments();
        params.addArgument("testplanid", "11");
        params.addArgument("caseid", "2");
        JavaSamplerContext ctx = new JavaSamplerContext(params);
        getInfoByLocationtestlalo test = new getInfoByLocationtestlalo();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
