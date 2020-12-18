package com.zoctan.api.core.Scheduled;

import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.mapper.DictionaryMapper;
import com.zoctan.api.mapper.DispatchMapper;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.service.ApicasesReportService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
public class ScheduleTask {
    @Autowired(required = false)
    private DictionaryMapper dictionaryMapper;

    @Autowired(required = false)
    private ApicasesMapper apicasesMapper;
    @Autowired(required = false)
    RedisUtils redisUtils;
    @Autowired(required = false)
    private DispatchMapper dispatchMapper;
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    @Autowired(required = false)
    private ApicasesReportService apicasereportservice;
    @Autowired(required = false)
    private ExecuteplanService epservice;
    @Autowired(required = false)
    private TestPlanCaseService tpcservice;

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String ip = null;
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            String redisKey = "dispatch-RedisLock"+ UUID.randomUUID().toString()+ip;
            long redis_default_expire_time = 2000;
            //默认上锁时间为五小时
            //此key存放的值为任务执行的ip，
            // redis_default_expire_time 不能设置为永久，避免死锁
            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
            if (lock) {
                System.out.println("============获得redis分布式锁成功=======================");
                List<Dictionary> jmeterpathdic = dictionaryMapper.findDicNameValueWithCode("jmeterpath");
                List<Dictionary> jmxpathdic = dictionaryMapper.findDicNameValueWithCode("jmxpath");
                List<Dictionary> jmeterperformancereportdic = dictionaryMapper.findDicNameValueWithCode("performancereportpath");
                String jmeterperformancereportpath=jmeterperformancereportdic.get(0).getDicitmevalue();
                String jmeterpath = jmeterpathdic.get(0).getDicitmevalue();
                String jmxpath = jmxpathdic.get(0).getDicitmevalue();
                ScheduleTask.log.info("jmeter可执行路径  is:" + jmeterpath);
                ScheduleTask.log.info("jmx文件路径  is:" + jmxpath);
                Long planid = null;
                Integer runningcount = dispatchMapper.findbusythreadnums("已分配");
                if (runningcount.intValue() == 0) {
                    ScheduleTask.log.info("执行任务全部处于空闲状态，可以开始分配" );
                    //表示slaver线程都没有运行，开始分配case运行
                    List<Dictionary> slavermaxthreaddic = dictionaryMapper.findDicNameValueWithCode("slavermaxthread");
                    String slavermaxthread = slavermaxthreaddic.get(0).getDicitmevalue();

                    List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
                    if (slaverlist.size() == 0) {
                        ScheduleTask.log.info("没有找到slaver。。。。。。。。" );
                        throw new Exception("未找到ip为：" + ip + "的slaver");
                    } else {
                        List<Dispatch> runlist = dispatchMapper.getcasebyslaverid(slaverlist.get(0).getId(), "待分配", Long.parseLong(slavermaxthread));
                        ScheduleTask.log.info("获取待执行的用例：。。。。。。。。" +runlist.size());
                        for (Dispatch dis : runlist) {
                            // 判断用例调用的jmx文件是否存在，如果未找到，返回客户端

                            Apicases apicases= apicasesMapper.getjmetername(dis.getTestcaseid());

                            String jmxcasename = dis.getCasejmxname();
                            String casename = dis.getTestcasename();
                            ScheduleTask.log.info("用例名 is......." + casename);
                            String jmeterextjarpath = jmeterpath.replace("bin", "lib");
                            String jarpath = jmeterextjarpath + "/ext/api-jmeter-autotest-1.0.jar";
                            ScheduleTask.log.info("jarpath 路径 is......." + jarpath);
                            String jmeterclassname = "com.api.autotest.test." + dis.getDeployunitname() + "." + jmxcasename;
                            ScheduleTask.log.info("jmeterclassname 类名......." + jmeterclassname);
                            if (!jmeterclassexistornot(jarpath, jmeterclassname)) {
                                // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
                                ApicasesReport ar = new ApicasesReport();
                                ar.setTestplanid(dis.getExecplanid());
                                ar.setCaseid(dis.getTestcaseid());
                                ar.setCasename(dis.getTestcasename());
                                ar.setErrorinfo("执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
                                ar.setBatchname(dis.getBatchname());
                                ar.setExpect(dis.getExpect());
                                ar.setStatus("失败");
                                ar.setRuntime(new Long(0));
                                planid = dis.getExecplanid();

                                apicasereportservice.addcasereport(ar);
                                epservice.updatetestplanstatus(planid, "fail");
                                ScheduleTask.log.info("未找到用例对应的jmeter-class类......." + jmeterclassname);
                                //return ResultGenerator.genFailedResult("执行用例："+casename+" |未找到用例对应的jmeter-class类："+jmeterclassname+" 请检查是否已经开发提交");
                            } else {
                                // 增加逻辑 获取计划的当前状态，如果为stop，放弃整个循环执行,return 掉
                                tpcservice.executeplancase(apicases.getCasetype(), dis.getSlaverid(),dis.getBatchid(), dis.getExecplanid(), dis.getTestcaseid(),apicases.getThreadnum(),apicases.getLoops(),  dis.getDeployunitname(), jmeterpath, jmxpath, jmxcasename, dis.getBatchname(),jmeterperformancereportpath);

                                // 更新调度表对应用例状态为已分配
                                dispatchMapper.updatedispatchstatus("已分配",dis.getSlaverid(),dis.getExecplanid(),dis.getBatchid(),dis.getTestcaseid());
                                ScheduleTask.log.info("调用jmeter完成..更新dispatch状态为已分配....." + dis);
                            }
                        }
                    }
                }
                //TODO 执行任务结束后需要释放锁
                //释放锁
                redisUtils.deletekey(redisKey);
                System.out.println("============释放分布式锁成功=======================");
            } else {
                System.out.println("============获得分布式锁失败=======================");
                ip = (String) redisUtils.getkey(redisKey);
                System.out.println("============{}机器上占用分布式锁，正在执行中======================="+ip);
                return;
            }
        } catch (Exception ex) {
            System.out.println("调度定时器异常: "+ex.getMessage());
        }
    }


    public boolean jmeterclassexistornot(String jarpath, String jmeterclassname) {
        boolean flag = false;
        try {
            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
            File f = new File(jarpath);
            URL url1 = f.toURI().toURL();
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(jarpath);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while (enumFiles.hasMoreElements()) {
                entry = (JarEntry) enumFiles.nextElement();
                if (entry.getName().indexOf("META-INF") < 0) {
                    String classFullName = entry.getName();
                    if (classFullName.indexOf(".class") > 0) {
                        //去掉后缀.class
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        if (className.equals(jmeterclassname)) {
                            flag = true;
                        }
                        //打印类名
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
