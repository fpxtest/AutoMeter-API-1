package com.api.autotest.core;

import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.Httphelp;
import com.api.autotest.common.utils.MysqlConnectionUtils;
import com.api.autotest.dto.RequestObject;
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
    public static String logplannameandcasename="";
    Logger logger=null;
    String testplanid = "";
    String caseid = "";
    String batchid = "";
    String slaverid = "";
    String batchname = "";
    String expect = "";
    String protocal ="";
    String resource ="";
    String method="";
    String apistyle="";
    String requestcontenttype="";
    HttpHeader header;
    HttpParamers paramers;
    ArrayList<HashMap<String, String>> caselist;
    ArrayList<HashMap<String, String>> planlist;
    ArrayList<HashMap<String, String>> deployunitmachineiplist;
    ArrayList<HashMap<String, String>> casedatalist;
    ArrayList<HashMap<String, String>> apilist;
    ArrayList<HashMap<String, String>> deployunitlist;
    ArrayList<HashMap<String, String>> preconditionlist;
    ArrayList<HashMap<String, String>> postconditionlist;

    HashMap<String, String> headmap;
    HashMap<String, String> paramsmap ;
    HashMap<String, String> bodymap ;
    HashMap<String, String> dubbomap ;
    HashMap<String, String> expectmap;

    public Testcore(Logger log)
    {
        logger=log;
    }

    // 获取期望值
    public String getExpectValue(String expectname) throws Exception {
        String expectvalue = "";
        expectvalue = expectmap.get(expectname);
        if(expectvalue==null)
        {
            throw new Exception("断言处期望名称:"+expectname+"未能获取期望值，请检查用例设计的期望名称和断言代码处的期望名称是否一致");
        }
        else
        {
            return expectvalue;
        }
    }

    //解析期望值
    public HashMap<String, String> getExpectmap() throws Exception {
        expectmap = new HashMap<String, String>();
        if(caselist.size()!=0)
        {
            expect = getcaseValue("expect", caselist).trim();
            logger.info(logplannameandcasename+"期望值all为 ....." + expect);
            String[] exparr = expect.split(",");
            for (String str : exparr) {
                logger.info(logplannameandcasename+"期望值为 ....." + str);
                String[] value=null;
                if(str.contains("="))
                {
                    value = str.split("=");
                }
                if(str.contains(">"))
                {
                    value = str.split(">");
                }
                if(str.contains("<"))
                {
                    value = str.split("<");
                }
                if(value==null)
                {
                    throw new Exception("用例的期望值:"+expect+"填写不符合规范");
                }
                if(value.length<2)
                {
                    throw new Exception("用例的期望名称:"+value[0]+"没有期望值，请填写完整");
                }
                if (!expectmap.containsKey(value[0])) {
                    expectmap.put(value[0].trim(), value[1].trim());
                }
            }
        }
        return expectmap;
    }

    // 获取用例名
    public String getcaseName() {
        String casename = getcaseValue("casename", caselist);
        return casename;
    }

    //初始化api请求需要的用例,环境数据
    public RequestObject InitHttpData(JavaSamplerContext context) {
        getdbconnection();
        testplanid = context.getParameter("testplanid");
        caseid = context.getParameter("caseid");
        slaverid = context.getParameter("slaverid");
        batchid = context.getParameter("batchid");
        batchname = context.getParameter("batchname");
        preconditionlist =getcaseData("select * from apicases_condition where basetype ='前置条件' and target='用例' and  caseid=" + caseid);
        postconditionlist =getcaseData("select * from apicases_condition where basetype ='后置条件' and target='用例' and  caseid=" + caseid);
        RequestObject newob= getcaserequestdata(testplanid,caseid);
        return newob;
    }

    // 拼装请求需要的用例数据
    public RequestObject getcaserequestdata(String planid, String testcaseid)
    {
        RequestObject ro=new RequestObject();
        planlist = getcaseData("select * from executeplan where id=" + planid);
        deployunitmachineiplist=getcaseData("select m.ip,a.domain,a.visittype from macdepunit a INNER JOIN apicases b INNER JOIN executeplan c JOIN machine m on a.depunitid=b.deployunitid and  a.envid=c.envid and  m.id=a.machineid where b.id="+testcaseid+" and c.id="+planid);
        caselist = getcaseData("select * from apicases where id=" + testcaseid);
        casedatalist = getcaseData("select * from api_casedata where caseid=" + testcaseid);
        apilist = getcaseData("select b.visittype,b.apistyle,b.path,b.requestcontenttype,b.responecontenttype from apicases a inner join api b on a.apiid=b.id where a.id=" + testcaseid);
        deployunitlist = getcaseData("select b.protocal,b.port from apicases a inner join deployunit b on a.deployunitid=b.id where a.id=" + testcaseid);
        fixhttprequestdatas();
        // 设置header
        header = new HttpHeader();
        for (String key : headmap.keySet()) {
            header.addParam(key, headmap.get(key));
        }
        // 设置参数
        paramers = new  HttpParamers();
        for (String key : paramsmap.keySet()) {
            paramers.addParam(key, paramsmap.get(key));
        }
        // url请求资源路径
        String path = getcaseValue("path", apilist);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        logplannameandcasename=getcaseValue("executeplanname", planlist)+"--"+getcaseValue("casename", caselist)+" :";

        //获取请求响应的数据格式
        requestcontenttype =getcaseValue("requestcontenttype", apilist);
        String responecontenttype =getcaseValue("responecontenttype", apilist);
        // http请求方式 get，post
        method = getcaseValue("visittype", apilist).toLowerCase();
        // api风格
        apistyle =getcaseValue("apistyle", apilist).toLowerCase();
        // 协议 http,https,rpc
        protocal = getcaseValue("protocal", deployunitlist).toLowerCase();
        // 发布单元端口
        String port = getcaseValue("port", deployunitlist);
        // 获取发布单元访问方式，ip或者域名
        String deployunitvisittype = getcaseValue("visittype", deployunitmachineiplist);
        // 根据访问方式来确定ip还是域名
        String testserver = "";
        if(deployunitvisittype.equals(new String("ip")))
        {
            testserver = getcaseValue("ip", deployunitmachineiplist);
            resource = protocal + "://" + testserver + ":" + port + path;
        }
        else
        {
            testserver= getcaseValue("domain", deployunitmachineiplist);
            resource = protocal + "://" + testserver  + path;
        }
        logger.info(logplannameandcasename+"resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path);
        ro.setHeader(header);
        ro.setApistyle(apistyle);
        ro.setParamers(paramers);
        ro.setRequestcontenttype(responecontenttype);
        ro.setRequestmMthod(method);
        ro.setResource(resource);
        return ro;
    }

    // 发送http请求
    public String request(RequestObject requestObject) throws Exception {
        String result="";
        if (protocal.equals(new String("http"))||protocal.equals(new String("https"))) {
                if(method.equals(new String("get")))
                {
                    logger.info(logplannameandcasename+"getrequest url is ....." +Httphelp.getrequesturl(resource, apistyle,paramers));
                }
                result = Httphelp.doService(protocal,requestObject.getResource(), requestObject.getRequestmMthod(),requestObject.getApistyle(), requestObject.getParamers(),requestObject.getRequestcontenttype(), requestObject.getHeader(), 10, 10);
                logger.info(logplannameandcasename+"request result is ....." + result);
        }

        if (protocal == "rpc") {
        }
        return result;
    }

    public void getdbconnection() {
        MysqlConnectionUtils.initDbResource();
    }

    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        logger.info(logplannameandcasename+"Sql is:  "+Sql);
        ArrayList<HashMap<String, String>> list=MysqlConnectionUtils.query(Sql);
        logger.info(logplannameandcasename+"list size is:  "+list.size());
        for (HashMap<String, String> li: list)
        {
            for (String key: li.keySet()) {
                logger.info(logplannameandcasename+"key is:  "+key+ " value is :"+li.get(key));
            }
        }
        return list;
    }

    // 获取用例期望值
    public String getcaseValue(String key, ArrayList<HashMap<String, String>> list) {
        HashMap<String, String> hs = list.get(0);
        return hs.get(key).trim();
    }

    // 获取用例的不同请求类型的数据
    public void fixhttprequestdatas() {
         headmap = new HashMap<String, String>();
         paramsmap = new HashMap<String, String>();
         bodymap = new HashMap<String, String>();
         dubbomap = new HashMap<String, String>();
        for (HashMap<String, String> data : casedatalist) {
            // 获取Header，params，Body，Dubbo不同数据后，开始组装请求数据
            String propertytype= data.get("propertytype");
            if (propertytype.equals(new String("Header"))) {
                headmap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
            if (propertytype.equals(new String("Body")) ) {
                bodymap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
            if (propertytype.equals(new String( "Params"))) {
                paramsmap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
            if (propertytype.equals(new String("Dubbo"))) {
                dubbomap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
        }
    }

    // 记录测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue,String errorinfo) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        if (status) {
            sql = "insert apicases_report(caseid,testplanid,batchname,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator)" +
                    " values(" + caseid + "," + testplanid + ", '"+batchname+"', '" + "成功" + "' , '" + respone + "' ,'" + assertvalue + "', " + time + ",'" + expect + "','" +errorinfo+ "','"+ dateNowStr + "', '" + dateNowStr + "','admin')";
        } else {
            sql = "insert apicases_report(caseid,testplanid,batchname,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator)" +
                    " values(" + caseid + "," + testplanid + ", '"+batchname+"', '" + "失败" + "' , '" + respone + "','" + assertvalue + "'," + time + ",'" + expect + "','" +errorinfo+ "','"+ dateNowStr + "','" + dateNowStr + "','admin')";
        }
        logger.info(logplannameandcasename+"测试结果 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        MysqlConnectionUtils.update(sql);
    }

    //更新结果为失败
    public void updatetestcaseresultfail(String errorinfo) {
        String sql="update apicases_report set status='失败',errorinfo='"+errorinfo+"' where testplanid="+testplanid+" and caseid="+caseid+" and batchname='"+batchname+"'";
        logger.info(logplannameandcasename+"更新用例状态为失败的sql：...........: " + sql);
        MysqlConnectionUtils.update(sql);

    }
    // 更新用例调度结果
    public void updatedispatchcasestatus() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "update dispatch set status='已完成',lastmodify_time='"+dateNowStr+"' where slaverid="+slaverid+" and execplanid="+testplanid+" and batchid="+batchid+" and testcaseid="+caseid;
        logger.info(logplannameandcasename+"更新调度用例状态 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        MysqlConnectionUtils.update(sql);
    }


    public void fixprecondition() throws Exception {
        fixcondition(preconditionlist,"前置");
    }

    public void fixpostcondition() throws Exception {
        fixcondition(postconditionlist,"后置");
    }

    //处理前后置条件
    public void fixcondition(ArrayList<HashMap<String, String>> list,String type) throws Exception {
        for (HashMap<String, String> hs:list )
        {
            // 数据库
            if(hs.get("conditionbasetype").equals(new String("数据库")))
            {
                String sql=hs.get("conditionvalue");
                String[] sqlarray= sql.split(";");
                logger.info(logplannameandcasename+type+"条件数据库sql语句。。。。。。"+sql);
                String assembleid=hs.get("envassemid");
                String machineipsql="select d.ip,b.connectstr from  enviroment_assemble b join macdepunit c join machine d join executeplan e on e.envid=c.envid and c.machineid=d.id and b.id=c.assembleid where c.assembleid ="+assembleid+" and e.id="+testplanid;
                logger.info(logplannameandcasename+type+"获取数据库机器ip和连接字sql：。。。。。。"+machineipsql);

                ArrayList<HashMap<String, String>> ipconnectstrlist=getcaseData(machineipsql);
                String dbip=getcaseValue("ip",ipconnectstrlist);
                String connectstrvalue=getcaseValue("connectstr",ipconnectstrlist);
                logger.info(logplannameandcasename+type+"条件数据库连接字。。。。。。"+connectstrvalue);

                String[] connectstrarray= connectstrvalue.split(",");
                HashMap<String,String> tmpmap=new HashMap<String,String>();
                for (String value: connectstrarray) {
                    if(value.contains("="))
                    {
                        tmpmap.put(value.split("=")[0],value.split("=")[1]);
                    }
                    else
                    {
                        throw new Exception(type+"连接字不符合规范："+connectstrvalue);
                    }
                }
                String username=tmpmap.get("username");
                String password=tmpmap.get("password");
                String dbname=tmpmap.get("db");
                String port=tmpmap.get("port");
                String dbconnect="jdbc:mysql://"+dbip+":"+port+"/"+dbname;
                logger.info(logplannameandcasename+type+"条件数据库连接url。。。。。。"+dbconnect);

                if(hs.get("conditiontype").equals(new String("mysql")))
                {
                    MysqlConnectionUtils.getConnectionbycon(dbconnect,username,password);
                    for (String execsql: sqlarray) {
                        MysqlConnectionUtils.execsql(execsql);
                    }
                }
            }
            if(hs.get("conditionbasetype").equals(new String("接口")))
            {
                // 接口
                logger.info(logplannameandcasename+type+"条件接口。。。。。。");
                // 根据caseid获取请求准备数据，然后发送请求
                String conditioncaseid=hs.get("conditioncaseid");
                RequestObject newob=getcaserequestdata(testplanid,conditioncaseid);
                String result= request(newob);
                logger.info(logplannameandcasename+type+"条件接口返回：。。。。。。"+result);
            }
        }
    }
}
