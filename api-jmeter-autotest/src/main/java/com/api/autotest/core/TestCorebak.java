package com.api.autotest.core;

import com.alibaba.fastjson.JSON;
import com.api.autotest.common.utils.HttpHeader;
import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.Httphelp;
import com.api.autotest.common.utils.MysqlConnectionUtils;
import com.api.autotest.dto.ApicasesReportstatics;
import com.api.autotest.dto.RequestObject;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fanseasn on 2020/10/17.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/10/17
*/
public class TestCorebak {
    public static String logplannameandcasename="";
    private Logger logger=null;

    public TestCorebak(JavaSamplerContext context, Logger log)
    {
        logger=log;
        String MysqlUrl=context.getParameter("mysqlurl");
        String MysqlUserName=context.getParameter("mysqlusername");
        String MysqlPass=context.getParameter("mysqlpassword");
        logger.info("connectstr is :  " + MysqlUrl+"   "+ MysqlUserName+"   "+MysqlPass);
        GetDBConnection(MysqlUrl,MysqlUserName,MysqlPass);
    }

    //获取期望值VALUE
    public String GetExpectValue(String expectname, HashMap<String, String> expectmap) throws Exception {
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

    //解析期望值map
    public HashMap<String, String> GetExpectMap(String expect) throws Exception {
        HashMap<String, String> expectmap = new HashMap<String, String>();
        //if(caselist.size()!=0)
        if(!expect.isEmpty())
        {
            logger.info(logplannameandcasename+"期望值all为 ....." + expect);
            String[] exparr = expect.split("\\|");
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

//                    String[] chars = value[0].split("  ");
//                    for (int i = 0; i < chars.length; i++) {
//                        logger.info(logplannameandcasename+"期望值key Asc为 ....." + Integer.parseInt(chars[i]));
//                    }
                    String expectkey = value[0].replace((char) 12288, ' ').trim();
                    //expectkey = value[0].replace((char) 32, ' ').trim();
                    expectmap.put(expectkey, value[1]);
                    logger.info(logplannameandcasename+"期望值key为 ....." + expectkey+" 期望值value为"+value[1]);
                }
            }
        }
        return expectmap;
    }

    //根据jmeter传递下来的数据拼装用例请求的数据
    public RequestObject InitHttpDatabyJmeter(JavaSamplerContext context) throws Exception {
        String casename = context.getParameter("casename");
        String executeplanname = context.getParameter("executeplanname");
        logplannameandcasename=executeplanname+"--"+casename+" :";
        logger.info(logplannameandcasename+"casename is :  " + casename );
        logger.info(logplannameandcasename+"executeplanname is :  " + executeplanname );

        String testplanid = context.getParameter("testplanid");
        String caseid = context.getParameter("caseid");
        String slaverid = context.getParameter("slaverid");
        String batchid = context.getParameter("batchid");
        String batchname = context.getParameter("batchname");

        String RequestmMthod = context.getParameter("RequestmMthod");
        logger.info(logplannameandcasename+"RequestmMthod is :  " + RequestmMthod );

        String resource = context.getParameter("resource");
        logger.info(logplannameandcasename+"resource is :  " + resource );

        String apistyle = context.getParameter("apistyle");
        logger.info(logplannameandcasename+"apistyle is :  " + apistyle );

        String requestcontenttype = context.getParameter("requestcontenttype");
        logger.info(logplannameandcasename+"requestcontenttype is :  " + requestcontenttype );

        String responecontenttype = context.getParameter("responecontenttype");
        logger.info(logplannameandcasename+"responecontenttype is :  " + responecontenttype );

        String headjson = context.getParameter("headjson");
        logger.info(logplannameandcasename+"headjson is :  " + headjson );

        String paramsjson = context.getParameter("paramsjson");
        logger.info(logplannameandcasename+"paramsjson is :  " + paramsjson );

        String bodyjson = context.getParameter("bodyjson");
        String dubbo = context.getParameter("dubbojson");
        String expect=context.getParameter("expect");
        logger.info(logplannameandcasename+"expect is :  " + expect );

        String casetype=context.getParameter("casetype");
        logger.info(logplannameandcasename+"casetype is :  " + casetype );

        String protocal = context.getParameter("protocal");
        logger.info(logplannameandcasename+"protocal is :  " + protocal );

        GetExpectMap(expect);

        RequestObject newob= new RequestObject();
        newob.setCaseid(caseid);
        newob.setTestplanid(testplanid);
        newob.setSlaverid(slaverid);
        newob.setBatchname(batchname);
        newob.setBatchid(batchid);
        newob.setExpect(expect);
        newob.setCasetype(casetype);

        newob.setResponecontenttype(responecontenttype);
        newob.setResource(resource);
        newob.setRequestmMthod(RequestmMthod);
        newob.setRequestcontenttype(requestcontenttype);
        newob.setApistyle(apistyle);
        newob.setProtocal(protocal);

        if(headjson.equals(new String("nocasedatas")))
        {
            //表示api设置了header参数，但是用例未赋值
            throw new Exception("用例的Header参数未设置数据，请设置完整再运行");
        }
        else
        {
            HttpHeader header=null;
            if(headjson.equals(new String("headjson")))
            {
                header=new HttpHeader();
                newob.setHeader(header);
            }
            else
            {
                try {
                    Map headermaps = (Map) JSON.parse(headjson);
                    header=new HttpHeader();
                    for (Object map : headermaps.entrySet()){
                        System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
                        header.addParam(((Map.Entry)map).getKey().toString(),((Map.Entry)map).getValue().toString());
                    }
                    newob.setHeader(header);
                }
                catch (Exception ex)
                {
                    throw  new Exception("Header参数用例数据异常："+headjson);
                }
            }
        }
        if(paramsjson.equals(new String("nocasedatas")))
        {
            throw new Exception("用例的Params参数未设置数据，请设置完整再运行");
        }
        else
        {
            HttpParamers params=null;

            if(paramsjson.equals(new String("paramjson")))
            {
                params=new HttpParamers();
                newob.setParamers(params);
            }
            else
            {
                try {
                    Map paramsmaps = (Map) JSON.parse(paramsjson);
                    params=new HttpParamers();
                    for (Object map : paramsmaps.entrySet()){
                        System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
                        params.addParam(((Map.Entry)map).getKey().toString(),((Map.Entry)map).getValue().toString());
                    }
                    newob.setParamers(params);
                }
                catch (Exception ex)
                {
                    throw  new Exception("Paras参数用例数据异常："+paramsjson);
                }
            }
        }
        return newob;
    }

    //通过调度IDs获取请求拼装数据列表
    public List<RequestObject> GetDispatchOBList(JavaSamplerContext context)  {
        List<RequestObject> FunctionROList=new ArrayList<>();
        String DispatchIds=context.getParameter("DispatchIds");
        String[] DispatchArray=DispatchIds.split(",");
        for (String DispatchID : DispatchArray)
        {
            ArrayList<HashMap<String, String>> DispatchList = getcaseData("select * from dispatch where id=" + DispatchID);
            String PlanId= getcaseValue("execplanid", DispatchList);
            String CaseId= getcaseValue("testcaseid", DispatchList);
            String SlaverId= getcaseValue("slaverid", DispatchList);
            String BatchId= getcaseValue("batchid", DispatchList);
            String BatchName= getcaseValue("batchname", DispatchList);
            String ExecPlanName= getcaseValue("execplanname", DispatchList);
            String TestCaseName= getcaseValue("testcasename", DispatchList);
            RequestObject ro= GetCaseRequestData(PlanId,CaseId,SlaverId,BatchId,BatchName,ExecPlanName,TestCaseName);
            FunctionROList.add(ro);
        }
        return FunctionROList;
    }

    // 拼装请求需要的用例数据
    public RequestObject GetCaseRequestData(String PlanId, String TestCaseId, String SlaverId, String BatchId, String BatchName, String ExecPlanName, String TestCaseName) {
        RequestObject ro=new RequestObject();
        //ArrayList<HashMap<String, String>> planlist = getcaseData("select * from executeplan where id=" + PlanId);
        ArrayList<HashMap<String, String>> deployunitmachineiplist=getcaseData("select m.ip,a.domain,a.visittype from macdepunit a INNER JOIN apicases b INNER JOIN executeplan c JOIN machine m on a.depunitid=b.deployunitid and  a.envid=c.envid and  m.id=a.machineid where b.id="+ TestCaseId +" and c.id="+ PlanId);
        ArrayList<HashMap<String, String>> caselist = getcaseData("select * from apicases where id=" + TestCaseId);
        ArrayList<HashMap<String, String>> casedatalist = getcaseData("select * from api_casedata where caseid=" + TestCaseId);
        ArrayList<HashMap<String, String>> apilist = getcaseData("select b.visittype,b.apistyle,b.path,b.requestcontenttype,b.responecontenttype from apicases a inner join api b on a.apiid=b.id where a.id=" + TestCaseId);
        ArrayList<HashMap<String, String>> deployunitlist = getcaseData("select b.protocal,b.port,b.id from apicases a inner join deployunit b on a.deployunitid=b.id where a.id=" + TestCaseId);

        HashMap<String, String> headmap=fixhttprequestdatas("Header",casedatalist);
        HashMap<String, String> bodymap=fixhttprequestdatas("Body",casedatalist);
        HashMap<String, String> paramsmap=fixhttprequestdatas("Params",casedatalist);
        HashMap<String, String> dubbomap=fixhttprequestdatas("Dubbo",casedatalist);
        // 设置header
        HttpHeader header = new HttpHeader();
        for (String key : headmap.keySet()) {
            header.addParam(key, headmap.get(key));
        }
        // 设置参数
        HttpParamers paramers = new  HttpParamers();
        for (String key : paramsmap.keySet()) {
            paramers.addParam(key, paramsmap.get(key));
        }
        // url请求资源路径
        String path = getcaseValue("path", apilist);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        logplannameandcasename=ExecPlanName+"--"+TestCaseName+" :";
        String casetype=getcaseValue("casetype", caselist);
        String expect=getcaseValue("expect", caselist);

        //获取请求响应的数据格式
        String requestcontenttype =getcaseValue("requestcontenttype", apilist);
        String responecontenttype =getcaseValue("responecontenttype", apilist);
        // http请求方式 get，post
        String method = getcaseValue("visittype", apilist).toLowerCase();
        // api风格
        String apistyle =getcaseValue("apistyle", apilist).toLowerCase();
        // 协议 http,https,rpc
        String protocal = getcaseValue("protocal", deployunitlist).toLowerCase();
        // 发布单元端口
        String port = getcaseValue("port", deployunitlist);

        String deployunitid = getcaseValue("id", deployunitlist);

        // 获取发布单元访问方式，ip或者域名
        String deployunitvisittype = getcaseValue("visittype", deployunitmachineiplist);
        // 根据访问方式来确定ip还是域名
        String testserver = "";
        String resource = "";
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
        logger.info(logplannameandcasename+"resource is :  " + resource + "   protocal  is:   " + protocal + "  expect is :      " + expect + "  visittype is: " + method + "   path is: " + path+" casetype is: "+casetype);
        ro.setCaseid(TestCaseId);
        ro.setDeployunitid(deployunitid);
        ro.setTestplanid(PlanId);
        ro.setSlaverid(SlaverId);
        ro.setBatchname(BatchName);
        ro.setTestplanname(ExecPlanName);
        ro.setBatchid(BatchId);
        ro.setExpect(expect);
        ro.setCasetype(casetype);
        ro.setHeader(header);
        ro.setProtocal(protocal);
        ro.setApistyle(apistyle);
        ro.setParamers(paramers);
        ro.setRequestcontenttype(requestcontenttype);
        ro.setRequestmMthod(method);
        ro.setResource(resource);
        ro.setResponecontenttype(responecontenttype);
        return ro;
    }

    // 发送http请求
    public String request(RequestObject requestObject) throws Exception {
        String result="";
        if (requestObject.getProtocal().equals(new String("http"))||requestObject.getProtocal().equals(new String("https"))) {
                //if(method.equals(new String("get")))
                if(requestObject.getRequestmMthod().equals(new String("get")))
                {
                    logger.info(logplannameandcasename+"getrequest url is ....." +Httphelp.getrequesturl(requestObject.getResource(), requestObject.getApistyle(),requestObject.getParamers()));
                }
                result = Httphelp.doService(requestObject.getProtocal(),requestObject.getResource(), requestObject.getRequestmMthod(),requestObject.getApistyle(), requestObject.getParamers(),requestObject.getRequestcontenttype(), requestObject.getHeader(), 10, 10);
                logger.info(logplannameandcasename+"request result is ....." + result);
        }

        if (requestObject.getProtocal().equals(new String("rpc"))) {
        }
        return result;
    }

    //初始化数据库连接
    public void GetDBConnection(String mysqluel, String mysqlusername, String mysqlpass) {
        MysqlConnectionUtils.initDbResource(mysqluel,mysqlusername,mysqlpass);
    }

    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> getcaseData(String Sql)  {
        logger.info(logplannameandcasename+"Sql is:  "+Sql);
        ArrayList<HashMap<String, String>> list= null;
        try {
            list = MysqlConnectionUtils.query(Sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename+"Sql is:  "+Sql+"  数据库异常："+e.getMessage());
        }
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

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap=new HashMap<>();
        for (HashMap<String, String> data : casedatalist) {
            String propertytype= data.get("propertytype");
            if (propertytype.equals(MapType)) {
                DataMap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
        }
        return DataMap;
    }

    // 记录用例测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue,String errorinfo,RequestObject ob) {
        //GetDBConnection();
        String resulttable="";
        if(ob.getCasetype().equals(new String("功能")))
        {
            resulttable="apicases_report";
        }
        if(ob.getCasetype().equals(new String("性能")))
        {
            resulttable="apicases_report_performance";
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        if (status) {
            sql = "insert "+ resulttable+" (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator)" +
                    " values(" + ob.getCaseid() + "," + ob.getTestplanid() + ", '"+ob.getBatchname()+"', "+ ob.getSlaverid()  + ", '成功" + "' , '" + respone + "' ,'" + assertvalue + "', " + time + ",'" + ob.getExpect() + "','" +errorinfo+ "','"+ dateNowStr + "', '" + dateNowStr + "','admin')";
        } else {
            sql = "insert  "+ resulttable+" (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator)" +
                    " values(" + ob.getCaseid() + "," + ob.getTestplanid() + ", '"+ob.getBatchname()+"', "+ ob.getSlaverid()  + ", '失败" + "' , '" + respone + "','" + assertvalue + "'," + time + ",'" + ob.getExpect() + "','" +errorinfo+ "','"+ dateNowStr + "','" + dateNowStr + "','admin')";
        }
        logger.info(logplannameandcasename+"测试结果 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        logger.info(logplannameandcasename+"记录用例测试结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }


    // 记录用例测试结果
    public void SaveReportStatics(ApicasesReportstatics apicasesReportstatics) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert apicases_reportstatics (testplanid,deployunitid,batchname,slaverid,totalcases,totalpasscases,totalfailcases,runtime,create_time,lastmodify_time,creator)" +
                " values(" + apicasesReportstatics.getTestplanid()+","+ apicasesReportstatics.getDeployunitid() + ", '" + apicasesReportstatics.getBatchname() + "', "+apicasesReportstatics.getSlaverid()+", "+ apicasesReportstatics.getTotalcases()+", "+ apicasesReportstatics.getTotalpasscases()+", "+ apicasesReportstatics.getTotalfailcases()+", "+ apicasesReportstatics.getRuntime()+ ", '" + dateNowStr + "', '" + dateNowStr + "','admin')";
        logger.info(logplannameandcasename+"功能测试统计结果 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        logger.info(logplannameandcasename+"功能测试统计结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public void PlanBatchAllDipatchFinish(ApicasesReportstatics apicasesReportstatics) {
        long DispatchNotFinishNums =0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid="+apicasesReportstatics.getTestplanid() + " and batchname= '"+apicasesReportstatics.getBatchname()+"' and status in('待分配','已分配')";
            logger.info(logplannameandcasename+"查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result= MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums= Long.parseLong(getcaseValue("nums",result));
        } catch (Exception e) {
            logger.info(logplannameandcasename+"查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        if(DispatchNotFinishNums>0)
        {
            logger.info(logplannameandcasename+"查询计划下的批次调度未完成数量："+DispatchNotFinishNums);
        }
        else
        {
            UpdateReportStatics(apicasesReportstatics.getTestplanid(),apicasesReportstatics.getBatchname(),"已完成");
        }
    }


    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public void PlanBatchAllDipatchFinish(String Testplanid,String batchname) {
        long DispatchNotFinishNums =0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid="+Testplanid + " and batchname= '"+batchname+"' and status in('待分配','已分配')";
            logger.info(logplannameandcasename+"查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result= MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums= Long.parseLong(getcaseValue("nums",result));
        } catch (Exception e) {
            logger.info(logplannameandcasename+"查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        if(DispatchNotFinishNums>0)
        {
            logger.info(logplannameandcasename+"查询计划下的批次调度未完成数量："+DispatchNotFinishNums);
        }
        else
        {
            UpdateReportStatics(Testplanid,batchname,"已完成");
        }
    }


    // 更新计划批次状态
    public void UpdateReportStatics(String Planid,String BatchName,String status) {
        String UpdateSql="update  executeplanbatch set status='"+status+"' where executeplanid="+Planid + " and batchname= '"+BatchName+"'";
        logger.info(logplannameandcasename+"更新计划批次状态结果完成  sql is...........: " + UpdateSql);
        logger.info(logplannameandcasename+"更新计划批次状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }



    //处理前置条件
    public void fixprecondition(String planid,String testcaseid) throws Exception {
        ArrayList<HashMap<String, String>> preconditionlist =getcaseData("select * from apicases_condition where basetype ='前置条件' and target='用例' and  caseid=" + testcaseid);
        fixcondition(preconditionlist,"前置",planid);
    }

    //处理后置条件
    public void fixpostcondition(String planid,String testcaseid) throws Exception {
        ArrayList<HashMap<String, String>> postconditionlist =getcaseData("select * from apicases_condition where basetype ='后置条件' and target='用例' and  caseid=" + testcaseid);
        fixcondition(postconditionlist,"后置",planid);
    }

    //处理前后置条件
    public void fixcondition(ArrayList<HashMap<String, String>> list,String type,String testplanid) throws Exception {
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
                String planid=hs.get("execplanid");

                String conditioncaseid=hs.get("conditioncaseid");
                RequestObject newob= GetCaseRequestData(planid,conditioncaseid,"","","","","");
                String result= request(newob);
                logger.info(logplannameandcasename+type+"条件接口返回：。。。。。。"+result);
            }
        }
    }

    // 记录前后置条件测试结果
    public String savetestcaseconditionresult(String testcaseid,String planid,String batchnameid,String batchnames,String slaverids,String status,String error,String condition,String testcasetype) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "insert apicases_condition_report (caseid,testplanid,batchid,batchname,slaverid,conditiontype,casetype,status,errorinfo,create_time,lastmodify_time,creator)" +
                " values(" + testcaseid + "," + planid +", "+batchnameid+ ", '"+batchnames+"', "+ slaverids + ", '"+condition+ "', '"+testcasetype + "', '"+status+  "','" +error+ "','"+ dateNowStr + "', '" + dateNowStr + "','admin')";
        logger.info(logplannameandcasename+"前后置条件测试结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename+"前后置条件测试结果 result  is...........: " + MysqlConnectionUtils.update(sql));
        return MysqlConnectionUtils.update(sql);
    }


    // 更新用例调度结果
    public void updatedispatchcasestatus(String testplanid,String batchid,String slaverid,String caseid) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "update dispatch set status='已完成',lastmodify_time='"+dateNowStr+"' where slaverid="+slaverid+" and execplanid="+testplanid+" and batchid="+batchid+" and testcaseid="+caseid;
        logger.info(logplannameandcasename+"更新调度用例状态 result sql is...........: " + sql);
        System.out.println("case result sql is: " + sql);
        logger.info(logplannameandcasename+"更新用例调度结果 is...........: " +MysqlConnectionUtils.update(sql));
    }

    //生产性能报告目录
    public void genealperformacestaticsreport(String testclass,String batchname,String testplanid,String batchid,String slaverid,String caseid,String casereportfolder,double costtime) throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "insert performancereportsource (planid,batchid,batchname,slaverid,caseid,testclass,runtime,source,status,create_time,lastmodify_time)" +
                " values(" + testplanid + "," + batchid + ", '"+batchname+"', "+slaverid+", " +caseid+ " , '" + testclass+"' ,"+costtime+ " , '" + casereportfolder + "', '待解析', '"+ dateNowStr + "', '" + dateNowStr +"')";
        logger.info(logplannameandcasename+"保存性能结果 sql is...........: " + sql);
        logger.info(logplannameandcasename+"保存性能结果 is...........: " +MysqlConnectionUtils.update(sql));
    }
}
