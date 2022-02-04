package com.api.autotest.core;

import com.api.autotest.common.utils.HttpParamers;
import com.api.autotest.common.utils.MysqlConnectionUtils;
import com.api.autotest.dto.ApicasesReportstatics;
import com.api.autotest.dto.RequestObject;
import com.api.autotest.dto.TestconditionReport;
import com.api.autotest.dto.TestvariablesValue;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.log.Logger;
import org.apache.poi.hslf.record.HSLFEscherRecordFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.api.autotest.core.TestCaseData.logplannameandcasename;


public class TestMysqlHelp {
    private Logger logger = null;

    public TestMysqlHelp(String MysqlUrl,String MysqlUserName,String MysqlPass,Logger log)
    {
        logger=log;
        GetDBConnection(MysqlUrl, MysqlUserName, MysqlPass);
    }

    public void GetDBConnection(String mysqluel, String mysqlusername, String mysqlpass) {
        MysqlConnectionUtils.initDbResource(mysqluel, mysqlusername, mysqlpass);
    }

    //获取计划批次的数据统计
    public ArrayList<HashMap<String, String>> GetStatic(String planid, String Batchname) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            String sql = "select sum(totalcases) as tc,sum(totalpasscases) as tpc ,sum(totalfailcases) as tfc from apicases_reportstatics where testplanid="+planid +" and batchname='" + Batchname + "'";
            logger.info(logplannameandcasename + "获取数据库 获取统计 result sql is...........: " + sql);
            list = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取统计异常...........: " + e.getMessage());
        }
        return list;
    }


    //获取账号数据
    public ArrayList<HashMap<String, String>> findWithUsername(String username) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            String sql = "SELECT a.* FROM account a where a.name = '" + username + "'";
            logger.info(logplannameandcasename + "获取数据库 获取账号 result sql is...........: " + sql);
            list = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取账号异常...........: " + e.getMessage());
        }
        return list;
    }


    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> findDicNameValueWithCode(String DicCode) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            String sql = "SELECT a.dicitemname,a.dicitmevalue FROM dictionary a where a.diccode = '" + DicCode + "'";
            logger.info(logplannameandcasename + "获取数据库 获取字典值caseid result sql is...........: " + sql);
            list = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取字典值异常...........: " + e.getMessage());
        }
        return list;
    }

    //获取计划批次
    public ArrayList<HashMap<String, String>> GetplanBatchCreator(String planid,String BatchName) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            String sql = "SELECT a.* FROM executeplanbatch a where a.executeplanid = " + planid + " and a.batchname='"+BatchName+"'";
            logger.info(logplannameandcasename + "获取数据库 获取计划批次 result sql is...........: " + sql);
            list = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取计划批次异常...........: " + e.getMessage());
        }
        return list;
    }

    //获取数据库用例相关数据
    public ArrayList<HashMap<String, String>> getcaseData(String Sql) {
        logger.info(logplannameandcasename + "获取数据库 Sql is:  " + Sql);
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            list = MysqlConnectionUtils.query(Sql);
            for (HashMap<String, String> maplog:list) {
                for (String Key: maplog.keySet()) {
                    logger.info("获取数据的字段名为:  " + Key + "  字段值为：" + maplog.get(Key));
                }
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 Sql is:  " + Sql + "  数据库异常：" + e.getMessage());
        }
        logger.info(logplannameandcasename + "获取数据库 list size is:  " + list.size());
        return list;
    }

    // 获取用例期望值
    public String getcaseValue(String key, ArrayList<HashMap<String, String>> list) {
        if(list.size()>0)
        {
            HashMap<String, String> hs = list.get(0);
            return hs.get(key).trim();
        }
        else
        {
            return "";
        }
    }


    //获取变量值类型
    public String GetVariablesDataType(String VariablesName) {
        String ValueType = "";
        try {
            String sql = "select valuetype from testvariables where  testvariablesname='" + VariablesName + "'";
            logger.info(logplannameandcasename + "获取数据库 获取变量值类型 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            if (result.size() > 0) {
                ValueType = result.get(0).get("valuetype");
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取变量值类型异常...........: " + e.getMessage());
        }
        return ValueType;
    }

    //根据变量名获取caseid
    public String GetCaseIdByVariablesName(String VariablesName) {
        String CaseID = "";
        try {
            String sql = "select caseid from apicases_variables where  variablesname='" + VariablesName + "'";
            logger.info(logplannameandcasename + "获取数据库 根据变量名获取caseid result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            if (result.size() > 0) {
                CaseID = result.get(0).get("caseid");
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 根据变量名获取caseid异常...........: " + e.getMessage());
        }
        return CaseID;
    }

    //获取变量值
    public String GetVariablesValues(String PlanID, String TestCaseId, String BatchName, String VariablesName) {
        String VariablesResult = "";
        try {
            String sql = "select variablesvalue from testvariables_value where planid=" + PlanID + " and caseid=" + TestCaseId + " and batchname= '" + BatchName + "'" + " and variablesname='" + VariablesName + "'";
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次中条件接口获取的中间变量 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            if (result.size() > 0) {
                VariablesResult = result.get(0).get("variablesvalue");
            }
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次中条件接口获取的中间变量异常...........: " + e.getMessage());
        }
        return VariablesResult;
    }


    //获取条件
    public ArrayList<HashMap<String, String>> GetConditionByPlanIDAndConditionType(Long Caseid, String ConditionType, String ObjectType) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select * from testcondition where objectid=" + Caseid + " and conditiontype='" + ConditionType + "' and objecttype='" + ObjectType + "'";
            logger.info(logplannameandcasename + "获取数据库 获取条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取条件顺序
    public ArrayList<HashMap<String, String>> GetConditionOrderByID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select * from condition_order where conditionid=" + ConditionID +" order by conditionorder  asc" ;
            logger.info(logplannameandcasename + "获取数据库 获取条件顺序 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取条件顺序异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取接口条件
    public ArrayList<HashMap<String, String>> GetApiConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select * from condition_api where conditionid=" + ConditionID;
            logger.info(logplannameandcasename + "获取数据库 获取接口条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取接口条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取脚本条件
    public ArrayList<HashMap<String, String>> GetScriptConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select * from condition_script where conditionid=" + ConditionID;
            logger.info(logplannameandcasename + "获取数据库 获取脚本条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取脚本条件异常...........: " + e.getMessage());
        }
        return result;
    }

    //获取数据库条件
    public ArrayList<HashMap<String, String>> GetDBConditionByConditionID(Long ConditionID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select * from condition_db where conditionid=" + ConditionID;
            logger.info(logplannameandcasename + "获取数据库 获取数据库条件 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 获取数据库条件异常...........: " + e.getMessage());
        }
        return result;
    }


    //保存条件结果
    public void SubConditionReportSave(TestconditionReport testconditionReport) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert testcondition_report (conditionid,conditiontype,subconditionid,conditionresult,conditionstatus,runtime,create_time,lastmodify_time,creator,batchname,planname,testplanid,subconditiontype,status,subconditionname)" +
                " values(" + testconditionReport.getConditionid() + ", '" + testconditionReport.getConditiontype() + "', " + testconditionReport.getSubconditionid() + ", '" + testconditionReport.getConditionresult() + "', '" + testconditionReport.getConditionstatus() + "', " + testconditionReport.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin'" + ", '" + testconditionReport.getBatchname().replace("'","''") + "',  '" + testconditionReport.getPlanname().replace("'","''") + "'," + testconditionReport.getTestplanid() + ", '" + testconditionReport.getSubconditiontype() + "', '" + testconditionReport.getStatus() + "', '" + testconditionReport.getSubconditionname().replace("'","''") + "')";
        logger.info(logplannameandcasename + "获取数据库 接口条件报告结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "获取数据库 接口条件报告结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //保存变量结果
    public void testVariablesValueSave(TestvariablesValue testvariablesValue) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert testvariables_value (planid,planname,caseid,casename,variablesid,variablesname,variablesvalue,memo,create_time,lastmodify_time,creator,batchname)" +
                " values(" + testvariablesValue.getPlanid() + ", '" + testvariablesValue.getPlanname().replace("'","''") + "', " + testvariablesValue.getCaseid() + ", '" + testvariablesValue.getCasename().replace("'","''") + "', " + testvariablesValue.getVariablesid() + ", '" + testvariablesValue.getVariablesname().replace("'","''") + "', '" + testvariablesValue.getMemo().replace("'","''") + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')" + ", '" + testvariablesValue.getBatchname().replace("'","''") + "'";
        logger.info(logplannameandcasename + "获取数据库 保存变量结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "获取数据库 保存变量结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询用例变量
    public ArrayList<HashMap<String, String>> GetApiCaseVaribales(Long CaseID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select *  from apicases_variables where caseid=" + CaseID;
            logger.info(logplannameandcasename + "获取数据库 查询用例变量 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 查询用例变量异常...........: " + e.getMessage());
        }
        return result;
    }

    //查询变量
    public ArrayList<HashMap<String, String>> GetVaribales(String VaribaleID) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            String sql = "select *  from testvariables where id=" + VaribaleID;
            logger.info(logplannameandcasename + "获取数据库 查询变量 result sql is...........: " + sql);
            result = MysqlConnectionUtils.query(sql);
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 查询变量异常...........: " + e.getMessage());
        }
        return result;
    }

    // 获取用例Header，params，Body，Dubbo数据
    public HashMap<String, String> fixhttprequestdatas(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = new HashMap<>();
        for (HashMap<String, String> data : casedatalist) {
            String propertytype = data.get("propertytype");
            if (propertytype.equals(MapType)) {
                DataMap.put(data.get("apiparam").trim(), data.get("apiparamvalue").trim());
            }
        }
        return DataMap;
    }

    public HashMap<String, String> getparamsdatabytype(String MapType, ArrayList<HashMap<String, String>> casedatalist) {
        HashMap<String, String> DataMap = new HashMap<>();
        for (HashMap<String, String> data : casedatalist) {
            String propertytype = data.get("paramstype");
            if (propertytype.equals(MapType)) {
                DataMap.put(data.get("keyname").trim(), data.get("keyvalue").trim());
            }
        }
        return DataMap;
    }

    // 记录用例测试结果
    public void savetestcaseresult(boolean status, long time, String respone, String assertvalue, String errorinfo, RequestObject requestObject, JavaSamplerContext context) {
        try {
            String resulttable = "";
            String casetype = "";
            String testplanid = "";
            String caseid = "";
            String slaverid = "";
            String expect = "";
            String batchname = "";
            String header = "";
            String Params = "";
            Map<String, Object> paramsmap =new HashMap<>();
            String  PostData="";
            String Url = "";
            String Method = "";
            if (requestObject == null) {
                casetype = context.getParameter("casetype");
                testplanid = context.getParameter("testplanid");
                caseid = context.getParameter("caseid");
                slaverid = context.getParameter("slaverid");
                expect = context.getParameter("expect");
                batchname = context.getParameter("batchname").replace("'","''");
            } else {
                casetype = requestObject.getCasetype();// context.getParameter("casetype");
                testplanid = requestObject.getTestplanid();// context.getParameter("testplanid");
                caseid = requestObject.getCaseid();// context.getParameter("caseid");
                slaverid = requestObject.getSlaverid();// context.getParameter("slaverid");
                expect = requestObject.getExpect();// context.getParameter("expect");
                batchname = requestObject.getBatchname().replace("'","''");// context.getParameter("batchname");
                Url = requestObject.getResource().replace("'","''");
                Method = requestObject.getRequestmMthod().toUpperCase();
                Map<String, Object> headermap = requestObject.getHeader().getParams();
                for (String key : headermap.keySet()) {
                    header = header + key + " ：" + headermap.get(key);
                }
                header=header.replace("'","''");
                paramsmap = requestObject.getParamers().getParams();
                for (String key : paramsmap.keySet()) {
                    Params = Params + key + " ：" + paramsmap.get(key);
                }
                if(!Params.isEmpty())
                {
                    PostData= requestObject.getPostData();
                    PostData="参数："+Params+"--------Body 内容："+PostData;
                }
                else
                {
                    PostData= requestObject.getPostData();
                }
                PostData=PostData.replace("'","''");
            }

            if (casetype.equals(new String("功能"))) {
                resulttable = "apicases_report";
            }
            if (casetype.equals(new String("性能"))) {
                resulttable = "apicases_report_performance";
            }
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(d);
            String sql = "";
            if (status) {
                sql = "insert " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas,url,requestmethod)" +
                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '成功" + "' , '" + respone.replace("'","''") + "' ,'" + assertvalue.replace("'","''") + "', " + time + ",'" + expect.replace("'","''") + "','" + errorinfo + "','" + dateNowStr + "', '" + dateNowStr + "','admin', '" + header + "', '" + PostData + "', '" + Url + "', '" + Method + "')";
            } else {
                sql = "insert  " + resulttable + " (caseid,testplanid,batchname,slaverid,status,respone,assertvalue,runtime,expect,errorinfo,create_time,lastmodify_time,creator,requestheader,requestdatas,url,requestmethod)" +
                        " values(" + caseid + "," + testplanid + ", '" + batchname + "', " + slaverid + ", '失败" + "' , '" + respone.replace("'","''") + "','" + assertvalue.replace("'","''") + "'," + time + ",'" + expect.replace("'","''") + "','" + errorinfo + "','" + dateNowStr + "','" + dateNowStr + "','admin', '" + header + "', '" + PostData + "', '" + Url + "', '" + Method + "')";
            }
            logger.info(logplannameandcasename + "获取数据库 测试结果 result sql is...........: " + sql);
            System.out.println("case result sql is: " + sql);
            logger.info(logplannameandcasename + "获取数据库 记录用例测试结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "获取数据库 记录用例测试结果异常...........: " + ex.getMessage());
        }
    }

    // 记录用例测试结果
    public void SaveReportStatics(ApicasesReportstatics apicasesReportstatics) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "insert apicases_reportstatics (testplanid,deployunitid,batchname,slaverid,totalcases,totalpasscases,totalfailcases,runtime,create_time,lastmodify_time,creator)" +
                " values(" + apicasesReportstatics.getTestplanid() + "," + apicasesReportstatics.getDeployunitid() + ", '" + apicasesReportstatics.getBatchname().replace("'","''") + "', " + apicasesReportstatics.getSlaverid() + ", " + apicasesReportstatics.getTotalcases() + ", " + apicasesReportstatics.getTotalpasscases() + ", " + apicasesReportstatics.getTotalfailcases() + ", " + apicasesReportstatics.getRuntime() + ", '" + dateNowStr + "', '" + dateNowStr + "','admin')";
        logger.info(logplannameandcasename + "获取数据库 功能测试统计结果 result sql is...........: " + sql);
        logger.info(logplannameandcasename + "获取数据库 功能测试统计结果 result sql is...........: " + MysqlConnectionUtils.update(sql));
    }

    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public void PlanBatchAllDipatchFinish(ApicasesReportstatics apicasesReportstatics) {
        long DispatchNotFinishNums = 0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid=" + apicasesReportstatics.getTestplanid() + " and batchname= '" + apicasesReportstatics.getBatchname() + "' and status in('待分配','已分配')";
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums = Long.parseLong(getcaseValue("nums", result));
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        if (DispatchNotFinishNums > 0) {
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次调度未完成数量：" + DispatchNotFinishNums);
        } else {
            UpdateReportStatics(apicasesReportstatics.getTestplanid(), apicasesReportstatics.getBatchname(), "已完成");
        }
    }

    //查询此计划下的批次调度是否已经全部完成，如果完成，刷新计划批次状态为finish
    public long PlanBatchAllDipatchFinish(String Testplanid, String batchname) {
        long DispatchNotFinishNums = 0;
        try {
            String sql = "select count(*) as nums from dispatch where execplanid=" + Testplanid + " and batchname= '" + batchname + "' and status in('待分配','已分配')";
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次调度是否已经全部完成 result sql is...........: " + sql);
            ArrayList<HashMap<String, String>> result = MysqlConnectionUtils.query(sql);
            DispatchNotFinishNums = Long.parseLong(getcaseValue("nums", result));
        } catch (Exception e) {
            logger.info(logplannameandcasename + "获取数据库 查询计划下的批次调度是否已经全部完成异常...........: " + e.getMessage());
        }
        return  DispatchNotFinishNums;
    }

    // 更新计划批次状态
    public void UpdateReportStatics(String Planid, String BatchName, String status) {
        String UpdateSql = "update  executeplanbatch set status='" + status + "' where executeplanid=" + Planid + " and batchname= '" + BatchName + "'";
        logger.info(logplannameandcasename + "获取数据库 更新计划批次状态结果完成  sql is...........: " + UpdateSql);
        logger.info(logplannameandcasename + "获取数据库 更新计划批次状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    // 更新Slaver状态
    public void UpdateSlaverStatus(String Slaverid, String status) {
        String UpdateSql = "update  slaver set status='" + status + "' where id=" + Slaverid;
        logger.info(logplannameandcasename + "获取数据库 更新Slaver状态结果完成  sql is...........: " + UpdateSql);
        logger.info(logplannameandcasename + "获取数据库 更新Slaver状态结果完成 result sql is...........: " + MysqlConnectionUtils.update(UpdateSql));
    }

    // 更新用例调度结果
    public void updatedispatchcasestatus(String testplanid, String caseid, String slaverid, String batchid) {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = sdf.format(d);
            String sql = "";
            sql = "update dispatch set status='已完成',lastmodify_time='" + dateNowStr + "' where slaverid=" + slaverid + " and execplanid=" + testplanid + " and batchid=" + batchid + " and testcaseid=" + caseid;
            logger.info(logplannameandcasename + "获取数据库 更新调度用例状态 result sql is...........: " + sql);
            System.out.println("case result sql is: " + sql);
            logger.info(logplannameandcasename + "获取数据库 更新用例调度结果 is...........: " + MysqlConnectionUtils.update(sql));
        } catch (Exception ex) {
            logger.info(logplannameandcasename + "获取数据库 更新用例调度结果异常...........: " + ex.getMessage());
        }
    }

    //生成性能报告目录
    public void genealperformacestaticsreport(String testclass, String batchname, String testplanid, String batchid, String slaverid, String caseid, String casereportfolder, double costtime) throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String sql = "";
        sql = "insert performancereportsource (planid,batchid,batchname,slaverid,caseid,testclass,runtime,source,status,create_time,lastmodify_time)" +
                " values(" + testplanid + "," + batchid + ", '" + batchname.replace("'","''") + "', " + slaverid + ", " + caseid + " , '" + testclass + "' ," + costtime + " , '" + casereportfolder + "', '待解析', '" + dateNowStr + "', '" + dateNowStr + "')";
        logger.info(logplannameandcasename + "获取数据库 保存性能结果 sql is...........: " + sql);
        logger.info(logplannameandcasename + "获取数据库 保存性能结果 is...........: " + MysqlConnectionUtils.update(sql));
    }
}
