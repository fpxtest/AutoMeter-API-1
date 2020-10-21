package com.api.autotest.test.demo;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Date;

public class DemoTest extends AbstractJavaSamplerClient {

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
    }

    // 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        //定义一个参数，显示到Jmeter的参数列表中，第一个参数为参数默认的显示名称，第二个参数为默认值
        params.addArgument("key", "true");
        params.addArgument("expResult", "true");
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
        // 获取传入参数
        String value = ctx.getParameter("key");
        String expResult = ctx.getParameter("expResult");

        // 在结果树中显示数据
        results.setResponseData("key=" + value + "|expResult=" + expResult, null);
        results.setDataType(SampleResult.TEXT);
        // 结束时间
        long end = new Date().getTime();

//        String response;
        // 当前用例执行结果1-成功|0-失败
//        int status;
        if (value.equals(expResult)) {
//            response = "DemoTest run success!";
            // 打印级别为info的日志到jmeter.log文件中
            getLogger().info("文件日志 DemoTest run success! - [ " + (end - start) + " ] ms");
            // 控制台打印日志
            System.out.println("控制台日志 DemoTest run success! - [ " + (end - start) + " ] ms");
            // 用于设置运行结果的成功或失败，如果是"false"则表示结果失败，否则则表示成功
            results.setSuccessful(true);
//            status = 1;
        } else {
//            response = "DemoTest run fail!";
            getLogger().error("文件日志 DemoTest run fail! - [ " + (end - start) + " ] ms");
            System.out.println("控制台日志 DemoTest run fail! - [ " + (end - start) + " ] ms");
            results.setSuccessful(false);
//            status = 0;
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
        params.addArgument("key", "true");
        params.addArgument("expResult", "false");

        JavaSamplerContext ctx = new JavaSamplerContext(params);
        DemoTest test = new DemoTest();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
