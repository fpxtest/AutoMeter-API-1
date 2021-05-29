package com.zoctan.api.core.Scheduled;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.service.DeployunitService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;
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
public class ConditionScheduleTask {
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    RedisUtils redisUtils;
    @Autowired(required = false)
    private ExecuteplanService epservice;
    @Autowired(required = false)
    private TestPlanCaseService tpcservice;
    @Autowired(required = false)
    private DeployunitService deployunitService;
    @Autowired(required = false)
    private DispatchMapper dispatchMapper;

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
            // 全局性能任务的redis的key相同，保证全局性能任务同一时刻只有一个线程进入工作
            String redisKey = "ConditionScheduleTask-RedisLock";
            long redis_default_expire_time = 2000;
            //默认上锁时间为五小时
            //此key存放的值为任务执行的ip，
            // redis_default_expire_time 不能设置为永久，避免死锁
            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
            if (lock) {
                List<Slaver> Slaverlist = slaverMapper.findslaverbytype("性能");
                List<Dispatch> DispatchAllList = dispatchMapper.getcasebyrunmode( "待分配","性能","多机并行");
                if(DispatchAllList.size()>0)
                {
                    //按照planid分组取第一组,再按照CaseID分组取第一组，每组请求slaver前，先查询，每组中的slaver是否空闲状态，如果空闲则发送slaver性能任务
                    List<Dispatch> PlanIDResultDispatchList=GetGroupList(DispatchAllList,"PlanID");
                    List<Dispatch> CaseResultDispatchList=GetGroupList(PlanIDResultDispatchList,"CaseID");
                    int RuningSlaverNums= dispatchMapper.findbusyslavernums(Slaverlist,"已分配");
                    // slaver全部为空闲状态
                    if(RuningSlaverNums==0)
                    {
                        for (Slaver slaver: Slaverlist) {
                            Dispatch dispatch=GetCaseDispatch(CaseResultDispatchList,slaver.getId());
                            if(dispatch!=null)
                            {
                                String params = JSON.toJSONString(dispatch);
                                HttpHeader header = new HttpHeader();
                                String ServerUrl="http://"+slaver.getIp()+":"+slaver.getPort()+"/execperformancetest";
                                String respon= Httphelp.doPost(ServerUrl, params, header, 10, 10);
                            }
                        }
                    }
                    else
                    {
                        ConditionScheduleTask.log.info("性能任务--有"+RuningSlaverNums+"台性能机器正在运行中，无法并行执行性能测试");
                        return ;
                    }
                }
                //TODO 执行任务结束后需要释放锁
                //释放锁
                redisUtils.deletekey(redisKey);
                ConditionScheduleTask.log.info("性能任务-============释放分布式锁成功=======================");
            } else {
                ConditionScheduleTask.log.info("性能任务-============获得分布式锁失败=======================");
                ip = (String) redisUtils.getkey(redisKey);
                ConditionScheduleTask.log.info("性能任务-============{}机器上占用分布式锁，正在执行中=======================" + ip);
                return;
            }
        } catch (Exception ex) {
            ConditionScheduleTask.log.info("性能任务-调度定时器异常: " + ex.getMessage());
        }
    }


    private List<Dispatch> GetGroupList(List<Dispatch> dispatchList,String ID)
    {
        Long ObjectID=new Long(0);
        HashMap<Long,List<Dispatch>> ResultGroup=new HashMap<>();
        for (Dispatch dispatch : dispatchList )
        {
            if(!ResultGroup.containsKey(dispatch.getExecplanid()))
            {
                List<Dispatch> tmp=new ArrayList<>();
                tmp.add(dispatch);
                if(ID.equals(new String("PlanID")))
                {
                    ObjectID=dispatch.getExecplanid();
                }
                if(ID.equals(new String("CaseID")))
                {
                    ObjectID=dispatch.getTestcaseid();
                }
                ResultGroup.put(dispatch.getExecplanid(),tmp);
            }
            else
            {
                ResultGroup.get(ObjectID).add(dispatch);
            }
        }
        List<Dispatch> ResultDispatchList=new ArrayList<>();
        for(Long Planid : ResultGroup.keySet())
        {
            ResultDispatchList = ResultGroup.get(Planid);
            break;
        }
        return ResultDispatchList;
    }

    private Dispatch GetCaseDispatch(List<Dispatch> dispatchList,Long SlaverID)
    {
        for (Dispatch dis: dispatchList) {
            if(SlaverID.equals(dis.getSlaverid()))
            {
                return dis;
            }
        }
        return  null;
    }

}
