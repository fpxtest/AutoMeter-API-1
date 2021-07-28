package com.zoctan.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.mapper.TestconditionMapper;
import com.zoctan.api.mapper.TestconditionReportMapper;
import com.zoctan.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Component
public class FunctionDispatchScheduleTask {

    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    RedisUtils redisUtils;
    @Autowired(required = false)
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private TestconditionReportMapper testconditionReportMapper;
    @Autowired(required = false)
    private TestconditionService testconditionService;
    @Autowired(required = false)
    private ConditionApiService conditionApiService;
    private String redisKey = "";


    //3.添加定时任务,处理并行多机并发性能测试任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String ip = null;
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            long redis_default_expire_time = 2000;
            //默认上锁时间为五小时
            //此key存放的值为任务执行的ip，
            // redis_default_expire_time 不能设置为永久，避免死锁
            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
            if (lock) {
                Dispatch dispatch = dispatchMapper.getrecentdispatchbyusetype("待分配", "功能");
                if (dispatch != null) {
                    Long PlanID = dispatch.getExecplanid();
                    String BatchName = dispatch.getBatchname();
                    //判断计划的前置条件是否已经完成
                    boolean flag=IsConditionFinish(PlanID,BatchName);
                    if(flag)
                    {
                        List<Dispatch> SlaverIDList = dispatchMapper.getdistinctslaverid("待分配", "功能", PlanID, BatchName);
                        if (SlaverIDList.size() > 0) {
                            try {
                                for (Dispatch dispatch1 : SlaverIDList) {
                                    Long Slaverid =dispatch1.getSlaverid();
                                    FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器..................PlanID:" + PlanID + " BatchName:" + BatchName + " slaverid:" + Slaverid);
                                    Slaver slaver = slaverMapper.findslaverbyid(Slaverid);
                                    if (slaver != null) {
                                        if (slaver.getStatus().equals("空闲")) {
                                            List<Dispatch> SlaverDispathcList = dispatchMapper.getfunctiondispatchsbyslaverid(Slaverid, "待分配", "功能", PlanID, BatchName);
                                            FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器 slaverid:" + slaver + " 获取dispatch数-：" + SlaverDispathcList.size());
                                            if (SlaverDispathcList.size() > 0) {
                                                String params = JSON.toJSONString(SlaverDispathcList);
                                                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-============执行机id：" + slaver.getId() + "  执行机名：" + slaver.getSlavername() + " 执行的dispatch：" + params);
                                                HttpHeader header = new HttpHeader();
                                                String ServerUrl = "http://" + slaver.getIp() + ":" + slaver.getPort() + "/exectestplancase/execfunctiontest";
                                                String respon = Httphelp.doPost(ServerUrl, params, header, 30000);
                                                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-============请求slaver响应结果：" + respon);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器请求执行服务异常：" + ex.getMessage());
                            }
                        }
                    }
                }
                //TODO 执行任务结束后需要释放锁
                //释放锁
                redisUtils.deletekey(redisKey);
                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-============释放分布式锁成功=======================");

            } else {
                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-============获得分布式锁失败=======================");
                ip = (String) redisUtils.getkey(redisKey);
                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-============{}机器上占用分布式锁，正在执行中=======================" + ip);
                return;
            }
        } catch (Exception ex) {
            FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-异常: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private boolean IsConditionFinish(Long PlanID,String BatchName)
    {
        boolean flag=true;
        List<Testcondition> testconditionList=testconditionService.GetConditionByPlanIDAndConditionType(PlanID,"前置条件");
        FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-前置条件..................PlanID:" + PlanID + " BatchName:" + BatchName +" 数量："+ testconditionList.size());
        if(testconditionList.size()>0) {
            long ConditionID= testconditionList.get(0).getId();
            List<ConditionApi> conditionApiList=conditionApiService.GetCaseListByConditionID(ConditionID);
            FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-API条件..................PlanID:" + PlanID + " BatchName:" + BatchName +" 数量："+ conditionApiList.size());
            if(conditionApiList.size()>0)
            {
                List<TestconditionReport> testconditionReportList= testconditionReportMapper.getunfinishapiconditionnums(PlanID,BatchName);
                FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-条件报告..................PlanID:" + PlanID + " BatchName:" + BatchName +" 数量："+ testconditionReportList.size());
                //配置了API条件，但是还未执行
                if(testconditionReportList.size()==0)
                {
                    flag=false;
                }
                else
                {
                    List<TestconditionReport> testconditionstatusReportList= testconditionReportMapper.getunfinishapiconditionnumswithstatus(PlanID,BatchName,"进行中");
                    FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-条件报告状态为进行中..................PlanID:" + PlanID + " BatchName:" + BatchName +" 数量："+ testconditionstatusReportList.size());
                    //配置了API条件，开始执行，状态为进行中的条数为0表示都已经执行完成
                    if(testconditionstatusReportList.size()==0)
                    {
                        flag=true;
                    }
                }
            }
            //配置了条件，但是未配置API条件，认为是无API条件，可以执行用例
            if(conditionApiList.size()==0)
            {
                flag=true;
            }
        }
        return flag;
    }

    private HashMap<Long, List<Dispatch>> GetSlaverDispatchList(List<Dispatch> PlanDispatchList) {
        HashMap<Long, List<Dispatch>> result = new HashMap<>();
        for (Dispatch dispatch : PlanDispatchList) {
            if (!result.containsKey(dispatch.getSlaverid())) {
                List<Dispatch> tmp = new ArrayList<>();
                tmp.add(dispatch);
                result.put(dispatch.getSlaverid(), tmp);
            } else {
                result.get(dispatch.getSlaverid()).add(dispatch);
            }
        }
        return result;
    }

    private List<Dispatch> GetGroupList(List<Dispatch> dispatchList, String ID) {
        Long ObjectID = new Long(0);
        HashMap<Long, List<Dispatch>> ResultGroup = new HashMap<>();
        for (Dispatch dispatch : dispatchList) {
            if (!ResultGroup.containsKey(dispatch.getExecplanid())) {
                List<Dispatch> tmp = new ArrayList<>();
                tmp.add(dispatch);
                if (ID.equals(new String("PlanID"))) {
                    ObjectID = dispatch.getExecplanid();
                }
                if (ID.equals(new String("CaseID"))) {
                    ObjectID = dispatch.getTestcaseid();
                }
                if (ID.equals(new String("BatchID"))) {
                    ObjectID = dispatch.getBatchid();
                }
                ResultGroup.put(dispatch.getExecplanid(), tmp);
            } else {
                ResultGroup.get(ObjectID).add(dispatch);
            }
        }
        List<Dispatch> ResultDispatchList = new ArrayList<>();
        for (Long Planid : ResultGroup.keySet()) {
            ResultDispatchList = ResultGroup.get(Planid);
            break;
        }
        return ResultDispatchList;
    }

    private Dispatch GetCaseDispatch(List<Dispatch> dispatchList, Long SlaverID) {
        for (Dispatch dis : dispatchList) {
            if (SlaverID.equals(dis.getSlaverid())) {
                return dis;
            }
        }
        return null;
    }

    @PostConstruct
    public void Init() {
        redisKey = "Dispatchservice-FunctionDispatchScheduleTask-RedisLock" + new Date();
        FunctionDispatchScheduleTask.log.info("调度服务【功能】测试定时器-redisKey is:" + redisKey);
    }
}
