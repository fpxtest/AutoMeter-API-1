package com.api.autotest.test.httpapitestcase;

import com.api.autotest.core.TestAssert;
import com.api.autotest.core.TestCore;
import com.api.autotest.dto.RequestObject;
import com.api.autotest.dto.ResponeData;
import com.api.autotest.dto.TestResponeData;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpApiPerformance extends AbstractJavaSamplerClient {
    TestCore Core = null;

    // 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext context) {
        Core = new TestCore(context, getLogger());
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
        params.addArgument("postdata", "/opt/");
        params.addArgument("dubbojson", "/opt/");
        params.addArgument("variablesjson", "/opt/");
        params.addArgument("mysqlurl", "/opt/");
        params.addArgument("mysqlusername", "/opt/");
        params.addArgument("mysqlpassword", "/opt/");
        params.addArgument("machineip", "11");
        params.addArgument("deployvisitytype", "11");
        params.addArgument("reportlogfolder", "11");


        return params;
    }

    // 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext ctx) {
        String reportlogfolder = ctx.getParameter("reportlogfolder").replace("Autometer", " ");
        SampleResult results = new SampleResult();
        //Jmeter java实例开始执行
        results.sampleStart();
        //用例运行开始时间
        long Start = new Date().getTime();
        //断言信息汇总
        String AssertInfo = "";
        String ErrorInfo = "";
        String ActualResult = "";
        RequestObject requestObject = null;
        TestAssert TestAssert = new TestAssert(getLogger());
        try {
            // 初始化用例数据
            requestObject = InitalTestData(Core, ctx);
            getLogger().info("Finish InitalTestData 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
            // 发送用例请求，并返回结果
            TestResponeData responeData = Core.request(requestObject);// SendCaseRequest(requestObject, Core);
            getLogger().info("Finish SendCaseRequest 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
            ActualResult = responeData.getResponeContent();
            //断言
            AssertInfo = Core.FixAssert(TestAssert, requestObject.getApicasesAssertList(), responeData);
        } catch (Exception ex) {
            getLogger().info("Exception 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:" + ex.getMessage());
            String ExceptionMess = ex.getMessage();
            if (ExceptionMess.contains("Illegal character in path at")) {
                ExceptionMess = "Url不合法，请检查是否有无法替换的变量，或者有相关非法字符：" + ex.getMessage();
            }
            ErrorInfo = CaseException(results, TestAssert, ExceptionMess);
        } finally {
            // 保存用例运行结果，Jmeter的sample运行结果
            long End = new Date().getTime();
            //CaseFinish(Core,results, TestAssert, AssertInfo,End-Start,ErrorInfo,ActualResult,ctx,requestObject);
            results.setSuccessful(TestAssert.isCaseresult());
            results.sampleEnd();
            WriteToFile(TestAssert, AssertInfo, End - Start, ErrorInfo, ActualResult, ctx, requestObject, reportlogfolder);
        }
        //Jmeter事务，表示这是事务的结束点
        return results;
    }

    //初始化用例的基础数据
    private RequestObject InitalTestData(TestCore core, JavaSamplerContext ctx) throws Exception {
        getLogger().info("Start InitTestData 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
        RequestObject ob = core.InitHttpDatabyJmeter(ctx);
        getLogger().info("Finish InitTestData 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。:");
        return ob;
    }

    //用例运行过程中的异常信息处理
    private String CaseException(SampleResult results, TestAssert testAssert, String exceptionMessage) {
        // 断言用例运行结果为失败
        results.setSuccessful(false);
        testAssert.setCaseresult(false);
        String ErrorInfo = exceptionMessage.replace("'", "");
        //end = new Date().getTime();
        getLogger().error("用例执行发生异常，请检查!" + exceptionMessage);
        return ErrorInfo;
    }

    //用例运行结束收集信息
    private void CaseFinish(TestCore core, SampleResult results, TestAssert testAssert, String assertInfo, long time, String ErrorInfo, String ActualResult, JavaSamplerContext ctx, RequestObject requestObject) {
        //jmeter java实例执行完成，记录结果
        results.setSuccessful(testAssert.isCaseresult());
        //性能并发高时考虑先把结果放到redis，再批量放到mysql
        core.savetestcaseresult(testAssert.isCaseresult(), time, ActualResult, assertInfo, ErrorInfo, requestObject, ctx);
    }

    private void WriteToFile(TestAssert testAssert, String assertInfo, long time, String ErrorInfo, String ActualResult, JavaSamplerContext ctx, RequestObject requestObject, String LogFolder) {
        FileWriter fw = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String header = "";
            String Params = "";
            String PostData = "";
            Map<String, Object> paramsmap = new HashMap<>();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(d);

            stringBuilder.append(requestObject.getCaseid() + "|");
            stringBuilder.append(requestObject.getTestplanid() + "|");
            stringBuilder.append(requestObject.getBatchname() + "|");
            stringBuilder.append(requestObject.getSlaverid() + "|");
            stringBuilder.append(testAssert.isCaseresult() + "|");


//            ActualResult=ActualResult.replaceAll(System.getProperty("line.separator"), "");
//            ActualResult=ActualResult.replace("\\n", "");
//            ActualResult=ActualResult.replaceAll("\\\\n","");
//            ActualResult=ActualResult.replace("\\r\\n", "");
//            ActualResult=ActualResult.replaceAll("\\\\r\\\\n", "");
//            ActualResult=ActualResult.replace("\\r", "");
//            ActualResult=ActualResult.replaceAll("\\\\r", "");
            //Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(ActualResult);
            ActualResult = m.replaceAll("");
            stringBuilder.append(ActualResult + "|");

            stringBuilder.append(assertInfo + "|");
            stringBuilder.append(time + "|");
            stringBuilder.append(requestObject.getExpect() + "|");
            stringBuilder.append(ErrorInfo + "|");
            stringBuilder.append(dateNowStr + "|");
            stringBuilder.append(dateNowStr + "|");
            stringBuilder.append("admin" + "|");

            Map<String, Object> headermap = requestObject.getHeader().getParams();
            for (String key : headermap.keySet()) {
                header = header + key + " ：" + headermap.get(key);
            }
            header = header.replace("'", "''");
            paramsmap = requestObject.getParamers().getParams();
            for (String key : paramsmap.keySet()) {
                Params = Params + key + " ：" + paramsmap.get(key) + " ";
            }
            if (!Params.isEmpty()) {
                PostData = "参数：" + Params;
            } else {
                PostData = requestObject.getPostData();
            }
            PostData = PostData.replace("'", "''");

            stringBuilder.append(header + "|");
            stringBuilder.append(PostData.replace(System.getProperty("line.separator"), "") + "|");
            stringBuilder.append(requestObject.getResource() + "|");
            stringBuilder.append(requestObject.getRequestmMthod());

            String LogFileName = requestObject.getTestplanid() + "-" + requestObject.getBatchid() + "-" + requestObject.getSlaverid();
            fw = new FileWriter(LogFolder + "/" + LogFileName + ".txt", true);
            fw.write(stringBuilder.toString() + System.getProperty("line.separator"));
        } catch (Exception ex) {
            getLogger().error("用例运行结束保存日志发生异常，请检查!" + ex.getMessage());
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    getLogger().error("用例运行结束日志文件关闭发生异常，请检查!" + ex.getMessage());
                }
            }
        }
    }

    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext ctx) {
        super.teardownTest(ctx);
    }

    // 本地调试
    public static void main(String[] args) {

        String json = "{\"Authorization\":\"BearerAutometereyJhbGciOiJSUzI1NiIsInppcCI6IkRFRiJ9.eNp0lz2OFDEQhe8y8SaMlkWajEuQrtxuo7HUf7Ldza4QIcSQkRKRERJwHzQcA-OqsssuE43fK837Xrs90-r3J78Pp8tJjbNdTncntYdrlK83q5U3_o1yVg2T8Rc1jnfSHc1kgukOgrJTZzBZHzq2N8rpa2ewb6OKBKX1ui8h1aA1wotMSJIJRALjSVLoZiEwflJYWkJQXEJIXFBAXJYvp6qUAKLEkKYs0BQIalNOzb7oQgHdoB71uow22HWpoMxu8NWkKsImVSXmN2XYhNUShXpVeiUkvgfuIGHPiIeqwLJBJDQIg7Iw0GAA496ublaLNs5sqwuZJQYM25nlBmKWy4gJ6yVm3Yo-qBDTrPadmmzYrVrNO3XZvFOZTbu12bxUr7dU7KPYvGbHxDaJvQEjsVnt2sWv16dXHF1xbptDK06sOK7ZWN1oXE0Bq00Gt44Hb14PM67vlp6_b62Ljcahvkau8SprK7XhVqrCjU4yXu1otn2xcG9pTZAsAYASwlFQMEoKtfofQ7lnyC2SoosDcUVTYnFyqN9U0FeMRJEDs4aypDEeVQ5HjdFmOaxbZ4PPKiYxvnISgDkJwTRCmCMwj8p7M8dnZcMrvgDzUdOgjJoqZSA6lVEpNyt9tUvuRLJUKQ41IIfApAuPHMI8Gb0Hs00KDjfXBKosIDELUMwgFrMkbMhHR5gSO_CjJPy2wMCPlvCxStwG_lNjEvGVk8DMSUimEcacgsm3sLl_zc2r7lxz25p75lY8qGmBcbhOWWmdgtIKU9IaI7x2dgv1_1rrYbC0E6O1E641kdzaVGJSB_6j45KQpIAECgCwplxQGBdU_smF-NzMOPa07PnI7I9Sgd7ovxis1htRzzjCy8Ylq1AuG1UGlctGxeKO6hWjdlj40b5a1GZGHe0rRW32wIeadiPpYPcq0ET2gIksA36vEUyw1p-fH2-_vt9-fLt9_vT7y9f4PmaettPlxcP9_fn88Or88sNfAAAA__8.RDgcm6V5dMuHNLCpUe8nzN_2IRqqoNOFJoG6Yk9JDTfYLZ4NaqP2sSsLJaFtUzQ8cu7njnvRwbAYJf2Xv--3Pw\",\"bb\":\"aaaaaaaaa\"}";
//        DocumentContext documentContext=JsonPath.parse(json);
//        documentContext.set("$.code","100");
//        System.out.println(documentContext.jsonString());

        Arguments params = new Arguments();
        params.addArgument("testplanid", "24");
        params.addArgument("caseid", "47");
        params.addArgument("batchid", "29");
        params.addArgument("slaverid", "19");
        params.addArgument("batchname", "撒啊大大所大所");

        params.addArgument("executeplanname", "测试中心性能测试");
        params.addArgument("casename", "获取执行计划统计数据性能用例");
        params.addArgument("expect", "[{\"assertcondition\":\"=\",\"assertsubtype\":\"\",\"asserttype\":\"Json\",\"assertvalues\":\"200\",\"assertvaluetype\":\"int\",\"caseid\":47,\"createTime\":1636306168000,\"creator\":\"admin\",\"expression\":\"$.code\",\"id\":20,\"lastmodifyTime\":1636306168000}]");
        params.addArgument("protocal", "http");
        params.addArgument("RequestmMthod", "GET");
        params.addArgument("casetype", "性能");
        params.addArgument("resource", "http://127.0.0.1:8080/executeplan/getstaticsplan");
        params.addArgument("apistyle", "普通方式");
        params.addArgument("requestcontenttype", "Json");
        params.addArgument("responecontenttype", "json");
        params.addArgument("headjson", json);
        params.addArgument("paramsjson", "");
        params.addArgument("bodyjson", "");
        params.addArgument("dubbojson", "");

        params.addArgument("mysqlurl", "jdbc:mysql://127.0.0.1:3306/testcenter?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC");
        params.addArgument("mysqlusername", "root");
        params.addArgument("mysqlpassword", "root");

        params.addArgument("machineip", "127.0.0.1");
        params.addArgument("deployvisitytype", "ip");

        JavaSamplerContext ctx = new JavaSamplerContext(params);
        HttpApiPerformance test = new HttpApiPerformance();
        test.setupTest(ctx);
        test.runTest(ctx);
        test.teardownTest(ctx);
        System.exit(0);

    }
}
