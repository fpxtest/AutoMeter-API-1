package com.api.autotest.core;

import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.Httphelp;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.MysqlConnectionUtils;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/
public class Testcore {
    Logger logger=null;
    String testplanid = "";
    String caseid = "";
    String expect = "";
    HttpHeader header;
    HttpParamers paramers;
    ArrayList<HashMap<String, String>> caselist;
    ArrayList<HashMap<String, String>> planlist;
    ArrayList<HashMap<String, String>> casedatalist;
    ArrayList<HashMap<String, String>> apilist;
    ArrayList<HashMap<String, String>> deployunitlist;
    HashMap<String, String> headmap = new HashMap<String, String>();
    HashMap<String, String> paramsmap = new HashMap<String, String>();
    HashMap<String, String> bodymap = new HashMap<String, String>();
    HashMap<String, String> dubbomap = new HashMap<String, String>();

    public Testcore(Logger log)
    {
        logger=log;
    }

    public HashMap<String, String> getExpectmap() {
        HashMap<String, String> expectmap = new HashMap<String, String>();
        if(caselist.size()!=0)
        {
            expect = getcaseValue("expect", caselist).trim();
            String[] exparr = expect.split(",");
            for (String str : exparr) {
                String[] value = str.split("=");
                if (!expectmap.containsKey(value[0])) {
                    expectmap.put(value[0], value[1]);
                }
            }
        }
        return expectmap;
    }

    public String getcaseName() {
        String casename = getcaseValue("casename", caselist);
        return casename;
    }

    public void InitHttpData(JavaSamplerContext context) {
        getdbconnection();
        testplanid = context.getParameter("testplanid");
        caseid = context.getParameter("caseid");
        planlist = getcaseData("select * from executeplan where id=" + testplanid);
        caselist = getcaseData("select * from apicases where id=" + caseid);
        casedatalist = getcaseData("select * from api_casedata where caseid=" + caseid);
        apilist = getcaseData("select b.visittype,b.path from apicases a inner join api b on a.deployunitname=b.deployunitname and a.apiname=b.apiname where a.id=" + caseid);
        deployunitlist = getcaseData("select b.protocal,b.port from apicases a inner join deployunit b on a.deployunitname=b.deployunitname where a.id=" + caseid);
        //System.out.println("casedatalist:..........."+casedatalist);
        fixhttprequestdatas();
        // 设置header
        header = new HttpHeader();
        for (String key : headmap.keySet()) {
            header.addParam(key, headmap.get(key));
        }
        // 设置参数
        paramers = HttpParamers.httpPostParamers();
        for (String key : paramsmap.keySet()) {
            paramers.addParam(key, paramsmap.get(key));
        }
    }

    // 发送http请求
    public String request() {
        // 获取计划测试服务器ip
        String testserver = getcaseValue("ip", planlist);
        // url请求资源路径
        String path = getcaseValue("path", apilist);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        // http请求方式 get，post
        String method = getcaseValue("visittype", apilist).toLowerCase();
        // http,https,rpc
        String protocal = getcaseValue("protocal", deployunitlist).toLowerCase();
        // 发布单元端口
        String port = getcaseValue("port", deployunitlist);
        String resource = protocal + "://" + testserver + ":" + port + path;
        logger.info("resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path);
        String result = "respone";
        if (protocal.equals("http")) {
            try {
                logger.info("request start is ....." + result);
                result = Httphelp.doService(resource, method, paramers, header, 10, 10);
                logger.info("request result is ....." + result);
            } catch (Exception e) {
                result = e.getMessage();
                e.printStackTrace();
            }
        }
        if (protocal == "https") {
        }
        if (protocal == "rpc") {
        }
        return result;
    }

    public void getdbconnection() {
        MysqlConnectionUtils.initDbResource();
    }

    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        logger.info("Sql is:  "+Sql);
        ArrayList<HashMap<String, String>> list=MysqlConnectionUtils.query(Sql);
        logger.info("list size is:  "+list.size());
        for (HashMap<String, String> li: list)
        {
            for (String key: li.keySet()) {
                logger.info("key is:  "+key+ " value is :"+li.get(key));
            }
        }
        return MysqlConnectionUtils.query(Sql);
    }

    // 获取用例期望值
    public String getcaseValue(String key, ArrayList<HashMap<String, String>> list) {
        HashMap<String, String> hs = list.get(0);
        return hs.get(key);
    }

    // 获取用例的不同请求类型的数据
    public void fixhttprequestdatas() {
        for (HashMap<String, String> data : casedatalist) {
            // 获取Header，params，Body，Dubbo不同数据后，开始组装请求数据
            if (data.get("propertytype") == "Header") {
                headmap.put(data.get("apiparam"), data.get("apiparamvalue"));
            }
            if (data.get("propertytype") == "Body") {
                bodymap.put(data.get("apiparam"), data.get("apiparamvalue"));
            }
            if (data.get("propertytype") == "Params") {
                paramsmap.put(data.get("apiparam"), data.get("apiparamvalue"));
            }
            if (data.get("propertytype") == "Dubbo") {
                dubbomap.put(data.get("apiparam"), data.get("apiparamvalue"));
            }
        }
    }

    // 记录测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        if (status) {
            sql = "insert apicases_report(caseid,testplanid,status,respone,assertvalue,runtime,expect,create_time,lastmodify_time,creator)" +
                    " values(" + caseid + "," + testplanid + ", '" + "成功" + "' , '" + respone + "' ,'" + assertvalue + "', " + time + ",'" + expect + "','" + dateNowStr + "', '" + dateNowStr + "','admin')";
        } else {
            sql = "insert apicases_report(caseid,testplanid,status,respone,assertvalue,runtime,expect,create_time,lastmodify_time,creator)" +
                    " values(" + caseid + "," + testplanid + ", '" + "失败" + "' , '" + respone + "','" + assertvalue + "'," + time + ",'" + expect + "','" + dateNowStr + "','" + dateNowStr + "','admin')";
        }
        System.out.println("case result sql is: " + sql);
        MysqlConnectionUtils.update(sql);
    }

}
