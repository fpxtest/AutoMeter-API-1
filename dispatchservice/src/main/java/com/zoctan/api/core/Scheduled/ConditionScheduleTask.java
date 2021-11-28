package com.zoctan.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.entity.Testcondition;
import com.zoctan.api.entity.TestconditionReport;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fanseasn on 2020/11/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/21
*/
//@Slf4j
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//@Component
public class ConditionScheduleTask {
//    @Value("${spring.conditionserver.serverurl}")
//    private String conditionserver;
//
//    @Autowired(required = false)
//    RedisUtils redisUtils;
//    @Autowired(required = false)
//    private TestconditionReportService testconditionReportService;
//    @Autowired(required = false)
//    private TestconditionService testconditionService;
//    @Autowired(required = false)
//    private DispatchMapper dispatchMapper;
//    private String redisKey = "";
//
//
//    //3.添加定时任务,处理并行多机并发性能测试任务
//    @Scheduled(cron = "0/15 * * * * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        String ip = null;
//        InetAddress address = null;
//        try {
//            address = InetAddress.getLocalHost();
//            ip = address.getHostAddress();
//            long redis_default_expire_time = 2000;
//            //此key存放的值为任务执行的ip，
//            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
//            if (lock) {
//                Dispatch dispatch = dispatchMapper.getrecentdispatch("待分配");
//                if (dispatch != null) {
//                    try {
//                        Long PlanID = dispatch.getExecplanid();
//                        Long CaseID = dispatch.getTestcaseid();
//                        String BatchName = dispatch.getBatchname();
//                        ConditionScheduleTask.log.info("调度处理条件任务：" + dispatch.getExecplanname() + "|" + BatchName);
//                        PlanCondition(dispatch,PlanID,BatchName);
//                        //判断此计划是否有条件需要处理
////                        Condition testcondition = new Condition(Testcondition.class);
////                        testcondition.createCriteria().andCondition("objectid = " + PlanID).andCondition("objecttype = '执行计划'");
////                        if (testconditionService.ifexist(testcondition) > 0) {
////                            ConditionScheduleTask.log.info("调度有计划条件需要处理................................");
////                            //判断planid,batchid在conditionreport表中无记录，才执行，避免多次重复调用
////                            Condition reportcon = new Condition(TestconditionReport.class);
////                            reportcon.createCriteria().andCondition("testplanid = " + PlanID).andCondition("batchname = '" + BatchName + "'");
////                            if (testconditionReportService.ifexist(reportcon) == 0) {
////                                ConditionScheduleTask.log.info("条件报告表中无此计划和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
////                                //在执行计划用例前增加条件服务器调用，处理计划前置条件
////                                RequestConditionServiceByPlanId(dispatch,"execplancondition");
////                            }
////                        }
//                        CaseCondition(dispatch,CaseID,BatchName);
//                        //判断此用例是否有条件需要处理
////                        Condition testconditioncase = new Condition(Testcondition.class);
////                        testconditioncase.createCriteria().andCondition("objectid = " + CaseID).andCondition("objecttype = '测试用例'");
////                        if (testconditionService.ifexist(testconditioncase) > 0) {
////                            ConditionScheduleTask.log.info("调度有用例条件需要处理................................");
////                            Condition reportcon = new Condition(TestconditionReport.class);
////                            reportcon.createCriteria().andCondition("testplanid = " + CaseID).andCondition("batchname = '" + BatchName + "'");
////                            if (testconditionReportService.ifexist(reportcon) == 0) {
////                                ConditionScheduleTask.log.info("条件报告表中无此计划和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
////                                //在执行计划用例前增加条件服务器调用，处理计划前置条件
////                                RequestConditionServiceByPlanId(dispatch,"execcasecondition");
////                            }
////                        }
//                    } catch (Exception ex) {
//                        ConditionScheduleTask.log.info("调度处理条件任务请求条件服务失败：" + ex.getMessage());
//                    }
//                }
//                //TODO 执行任务结束后需要释放锁
//                //释放锁
//                redisUtils.deletekey(redisKey);
//                ConditionScheduleTask.log.info("调度处理条件任务-============释放分布式锁成功=======================");
//
//            } else {
//                ConditionScheduleTask.log.info("调度处理条件任务-============获得分布式锁失败=======================");
//                ip = (String) redisUtils.getkey(redisKey);
//                ConditionScheduleTask.log.info("调度处理条件任务-============{}机器上占用分布式锁，正在执行中=======================" + ip);
//                return;
//            }
//        } catch (Exception ex) {
//            ConditionScheduleTask.log.info("调度处理条件任务-调度定时器异常: " + ex.getMessage());
//        }
//    }
//
//
//    private void PlanCondition(Dispatch dispatch,Long PlanID,String BatchName) throws Exception {
//        //判断此计划是否有条件需要处理
//        Condition testcondition = new Condition(Testcondition.class);
//        testcondition.createCriteria().andCondition("objectid = " + PlanID).andCondition("objecttype = '执行计划'");
//        if (testconditionService.ifexist(testcondition) > 0) {
//            ConditionScheduleTask.log.info("调度有计划条件需要处理................................");
//            //判断planid,batchid在conditionreport表中无记录，才执行，避免多次重复调用
//            Condition reportcon = new Condition(TestconditionReport.class);
//            reportcon.createCriteria().andCondition("testplanid = " + PlanID).andCondition("batchname = '" + BatchName + "'");
//            if (testconditionReportService.ifexist(reportcon) == 0) {
//                ConditionScheduleTask.log.info("条件报告表中无此计划和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
//                //在执行计划用例前增加条件服务器调用，处理计划前置条件
//                RequestConditionServiceByPlanId(dispatch,"execplancondition");
//            }
//        }
//    }
//
//    private void CaseCondition(Dispatch dispatch,Long CaseID,String BatchName) throws Exception {
//        //判断此用例是否有条件需要处理
//        Condition testconditioncase = new Condition(Testcondition.class);
//        testconditioncase.createCriteria().andCondition("objectid = " + CaseID).andCondition("objecttype = '测试用例'");
//        if (testconditionService.ifexist(testconditioncase) > 0) {
//            ConditionScheduleTask.log.info("调度有用例条件需要处理................................");
//            Condition reportcon = new Condition(TestconditionReport.class);
//            reportcon.createCriteria().andCondition("testplanid = " + CaseID).andCondition("batchname = '" + BatchName + "'");
//            if (testconditionReportService.ifexist(reportcon) == 0) {
//                ConditionScheduleTask.log.info("条件报告表中无此用例和批次对应的条件结果................................" + dispatch.getExecplanname() + "|" + BatchName);
//                //在执行计划用例前增加条件服务器调用，处理计划前置条件
//                RequestConditionServiceByPlanId(dispatch,"execcasecondition");
//            }
//        }
//    }
//
//    private void RequestConditionServiceByPlanId(Dispatch dispatch,String Url) throws Exception {
//        String params = JSON.toJSONString(dispatch);
//        HttpHeader header = new HttpHeader();
//        String ServerUrl = conditionserver + "/testcondition/"+Url;
//        ConditionScheduleTask.log.info("调度处理条件任务请求conditionserver。。。。。。。: " + ServerUrl);
//        try {
//            ConditionScheduleTask.log.info("调度处理条件任务请求数据。。。。。。。: " + params);
//            String respone = Httphelp.doPost(ServerUrl, params, header, 30000);
//            ConditionScheduleTask.log.info("调度处理条件任务请求条件服务响应: " + respone);
//        } catch (Exception ex) {
//            ConditionScheduleTask.log.info("-------------调度处理条件任务请求异常: " + ex.getMessage());
//        }
//    }
//
//    @PostConstruct
//    public void Init() {
//        redisKey = "Dispatchservice-ConditionScheduleTask-RedisLock" + new Date();
//        ConditionScheduleTask.log.info("调度处理条件任务-redisKey is:" + redisKey);
//    }
}
