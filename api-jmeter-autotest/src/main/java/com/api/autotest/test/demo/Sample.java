package com.api.autotest.test.demo;

import com.alibaba.fastjson.JSONObject;
import com.api.autotest.core.TestAssert;
import com.api.autotest.core.Testcore;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;
import java.util.HashMap;

public class Sample extends AbstractJavaSamplerClient {
    HashMap<String, String> expect = new HashMap<>();
    boolean caseresult = false; //用例运行的结果，成功，失败
    long costtime;
    String assertinfo;
    Testcore core = null;
    TestAssert testass = null;

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        core = new Testcore(getLogger());
        testass = new TestAssert();
        //初始化用例请求数据
        core.InitHttpData(context);
        getLogger().info("InitHttpData finish :");
        //获取期望值数据
        expect = core.getExpectmap();
        if (expect == null) {
            getLogger().error("未获取用例的期望值，请检查用例期望值是否为空");
        }
    }

    // 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        //定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
        params.addArgument("testplanid", "23");
        params.addArgument("caseid", "16");
        return params;
    }

    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult results = new SampleResult();
        // 如果有中文，设置编码
        results.setEncodingAndType("UTF-8");
        //定义一个事务，表示这是事务的起始点，类似于LoadRunner的lr.start_transaction
        results.sampleStart();
        // 开始时间
        long start = new Date().getTime();
        // 获得请求响应
        String actualresult = core.request();
        getLogger().info("actualresult is:" + actualresult);
        getLogger().info("actualresult is :" + actualresult);
        // 结束时间
        long end = new Date().getTime();
        costtime = end - start;
        // 在结果树中显示数据
        results.setResponseData("casename=" + core.getcaseName() + " |expResult=" + expect + " |actualresult" + actualresult, null);
        results.setDataType(SampleResult.TEXT);

        try {
            // 解析请求响应内容，使用期望值expect开始断言
            String expectcode = expect.get("code");
            getLogger().info("expectcode is:" + expectcode);
            JSONObject jsonObject;
            jsonObject = JSONObject.parseObject(actualresult);

            String actualcode = jsonObject.get("code").toString();
            getLogger().info("actualcode is:" + expectcode);
            // 完成期望值和实际值的比较代码，并且收集断言结果
            if (expectcode.equals(actualcode)) {
                caseresult = true;
                testass.assertcollection(expectcode, actualcode, caseresult);
            }
            assertinfo = testass.assertcollection(expectcode.toString(), actualcode.toString(), caseresult);
        } catch (Exception ex) {
            assertinfo = testass.assertcollection(ex.toString(), ex.toString(), caseresult);
            getLogger().error("用例在断言处代码发生异常，请检查!"+ex.toString());

        } finally {
            // 保存用例运行结果，jmeter的sample运行结果
            results.setSuccessful(caseresult);
            core.savetestcaseresult(caseresult, costtime, actualresult, assertinfo);
        }
        //定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction
        results.sampleEnd();
        return results;
    }

    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext ctx) {
        super.teardownTest(ctx);
    }

    // 本地调试
    public static void main(String[] args) {

        Arguments params = new Arguments();
        params.addArgument("testplanid", "23");
        params.addArgument("caseid", "16");

        JavaSamplerContext ctx = new JavaSamplerContext(params);
        Sample test = new Sample();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
