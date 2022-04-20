package com.zoctan.api.core.Scheduled;

import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.entity.*;
import com.zoctan.api.mapper.*;
import com.zoctan.api.service.DeployunitService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
//@Slf4j
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//@Component
public class PerformanceDispatchScheduleTask {
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Autowired(required = false)
//    private DictionaryMapper dictionaryMapper;
//    @Autowired(required = false)
//    private ApicasesMapper apicasesMapper;
//    @Autowired(required = false)
//    RedisUtils redisUtils;
//    @Autowired(required = false)
//    private DispatchMapper dispatchMapper;
//    @Autowired(required = false)
//    private SlaverMapper slaverMapper;
//    @Autowired(required = false)
//    private ApicasesReportPerformanceMapper apicasesReportPerformanceMapper;
//    @Autowired(required = false)
//    private ExecuteplanService epservice;
//    @Autowired(required = false)
//    private TestPlanCaseService tpcservice;
//    @Autowired(required = false)
//    private DeployunitService deployunitService;
//
//    //3.添加定时任务,处理单机性能任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        String ip = null;
//        InetAddress address = null;
//        try {
//            address = InetAddress.getLocalHost();
//            ip = address.getHostAddress();
//            List<Slaver> slaverlist = slaverMapper.findslaverbyip(ip);
//            if (slaverlist.size() == 0) {
//                PerformanceDispatchScheduleTask.log.error("性能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
//                return;
//            }
//            String SlaverType = slaverlist.get(0).getStype();
//            if (SlaverType.equals(new String( "功能"))) {
//                PerformanceDispatchScheduleTask.log.info("性能任务-当前slaver配置为功能执行机，性能任务忽略。。。。。");
//                return;
//            }
//            Long SlaverId = slaverlist.get(0).getId();
//            //获取当前正在运行的Jmeter进程数
//            int RealPeformanceJmeter= GetSlaverRealJmeterProcess(SlaverId,SlaverType);
//            if(RealPeformanceJmeter<=0)
//            {
//                // 无jmeter进程资源，则返回
//                return;
//            }
//            // 全局性能任务的redis的key相同，保证全局性能任务同一时刻只有一个线程进入工作
//            String redisKey = "Performance-dispatch-RedisLock";
//            long redis_default_expire_time = 2000;
//            //默认上锁时间为五小时
//            //此key存放的值为任务执行的ip，
//            // redis_default_expire_time 不能设置为永久，避免死锁
//            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
//            if (lock) {
//                PerformanceDispatchScheduleTask.log.info("============获得redis分布式锁成功=======================");
//                String ProjectPath = System.getProperty("user.dir");
//                String JmeterPath = ProjectPath + "/apache-jmeter-5.3/bin";
//                String JmxPath = ProjectPath + "/servicejmxcase";
//                String JmeterPerformanceReportPath = ProjectPath + "/slaverservice/performancereport";
//                PerformanceDispatchScheduleTask.log.info("性能任务-jmeter可执行路径  is:" + JmeterPath+" jmx文件路径  is:" + JmxPath +" 性能报告文件路径  is:" + JmeterPerformanceReportPath);
//
//                List<Dispatch> DispatchAllList = dispatchMapper.getcasebyslaveridandrunmode(SlaverId, "待分配", SlaverType,"单机运行", new Long(100));
//                PerformanceDispatchScheduleTask.log.info("性能任务-获取待执行的单机运行性能用例数：。。。。。。。。" + DispatchAllList.size());
//
//                List<Dispatch> PerformanceDispatchList = PerformanceDispatch(RealPeformanceJmeter, DispatchAllList);
//                PerformanceDispatchScheduleTask.log.info("性能任务-可以单机运行并行执行Jmeter性能测试的用例数：。。。。。。。。" + PerformanceDispatchList.size());
//
//                for (Dispatch dispatch : PerformanceDispatchList) {
//                    Executeplan executeplan= epservice.getBy("id",dispatch.getExecplanid());
//                    // 判断计划是否为单机类型
//                    if(executeplan.getRunmode().equals(new String("单机运行")))
//                    {
//                        String JmxCaseName = dispatch.getCasejmxname();
//                        String DeployUnitName=dispatch.getDeployunitname();
//                        String CaseName = dispatch.getTestcasename();
//                        PerformanceDispatchScheduleTask.log.info("性能任务-执行单机运行性能用例名 is......." + CaseName);
//                        Deployunit Deployunit = deployunitService.findDeployNameValueWithCode(DeployUnitName);
//                        String Protocal = Deployunit.getProtocal();
//                        //如果是http,https，直接使用httpapitestcase下的functionhttpapi或者performancehttpapi来执行测试
//                        String JmeterClassName = "";
//                        String ClassName="";
//                        String DeployUnitNameForJmeter = "";
//                        if (Protocal.equals(new String("http")) || Protocal.equals(new String("https"))) {
//                            DeployUnitNameForJmeter = "httpapitestcase";
//                            JmeterClassName = "HttpApiPerformance";
//                            ClassName="com.api.autotest.test."+DeployUnitNameForJmeter+"."+JmeterClassName;
//                        }
//                        if (Protocal.equals(new String("rpc"))){
//                            DeployUnitNameForJmeter = dispatch.getDeployunitname();
//                            JmeterClassName = DeployUnitName ;
//                            ClassName="com.api.autotest.test."+DeployUnitName+"."+JmxCaseName;
//                        }
//                        Apicases apicases = apicasesMapper.getjmetername(dispatch.getTestcaseid());
//                        PerformanceDispatchScheduleTask.log.info("性能任务-DeployUnitNameForJmeter is......." + DeployUnitNameForJmeter+" JmeterClassName is........"+JmeterClassName);
//
//                        if (!JmeterClassExist(ClassName, JmeterPath)) {
//                            JmeterClassNotExist(dispatch, ClassName, CaseName);
//                            dispatchMapper.updatedispatchstatus("调度异常", dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
//                        } else {
//                            // 更新调度表对应用例状态为已分配
//                            dispatchMapper.updatedispatchstatus("已分配", dispatch.getSlaverid(), dispatch.getExecplanid(), dispatch.getBatchid(), dispatch.getTestcaseid());
//                            PerformanceDispatchScheduleTask.log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。更新dispatch状态为已分配.....开始调用jmeter..。。。。。。。。。。。。。。。。。。。。。。。。。" + dispatch.getId());
//                            // 增加逻辑 获取计划的当前状态，如果为stop，放弃整个循环执行,return 掉
//                            tpcservice.ExecuteHttpPerformancePlanCase(apicases.getCasetype(), dispatch.getSlaverid(), dispatch.getBatchid(), dispatch.getExecplanid(), dispatch.getTestcaseid(), apicases.getThreadnum(), apicases.getLoops(), DeployUnitNameForJmeter, JmeterPath, JmxPath, JmeterClassName, dispatch.getBatchname(), JmeterPerformanceReportPath, url, username, password);
//                            PerformanceDispatchScheduleTask.log.info("性能任务-。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。调用jmeter完成..。。。。。。。。。。。。。。。。。。。。。。。。。"+ dispatch.getId());
//                        }
//                    }
//                }
//                //TODO 执行任务结束后需要释放锁
//                //释放锁
//                redisUtils.deletekey(redisKey);
//                PerformanceDispatchScheduleTask.log.info("性能任务-============释放分布式锁成功=======================");
//            } else {
//                PerformanceDispatchScheduleTask.log.info("性能任务-============获得分布式锁失败=======================");
//                ip = (String) redisUtils.getkey(redisKey);
//                PerformanceDispatchScheduleTask.log.info("性能任务-============{}机器上占用分布式锁，正在执行中=======================" + ip);
//                return;
//            }
//        } catch (Exception ex) {
//            PerformanceDispatchScheduleTask.log.info("性能任务-调度定时器异常: " + ex.getMessage());
//        }
//    }
//
//
//    public int GetSlaverRealJmeterProcess(Long SlaverId,String SlaverType)
//    {
//        int PerformanceJmeter = GetJmeterProcess("PerformanceJmeterProcess", SlaverType);
//        //获取当前正在运行的Jmeter进程数
//        List<Dispatch> DoingDispatchAllList = dispatchMapper.getdoingcasebyslaverid(SlaverId, "已分配", SlaverType);
//        PerformanceDispatchScheduleTask.log.info("性能任务-当前slaver正在进行性能测试并行Jmeter进程数:"+ DoingDispatchAllList.size());
//        //可以并行启动Jmeter进程数需要减去当前正在运行的Jmeter进程数
//        int RealPeformanceJmeter=PerformanceJmeter- DoingDispatchAllList.size();
//        PerformanceDispatchScheduleTask.log.info("性能任务-当前slaver实际可以并行性能测试Jmeter进程数:"+RealPeformanceJmeter);
//        return RealPeformanceJmeter;
//    }
//
//
//    public void JmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
//        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
//        ApicasesReport ar = new ApicasesReport();
//        ar.setTestplanid(dis.getExecplanid());
//        ar.setCaseid(dis.getTestcaseid());
//        ar.setCasename(dis.getTestcasename());
//        ar.setErrorinfo("性能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
//        ar.setBatchname(dis.getBatchname());
//        ar.setExpect(dis.getExpect());
//        ar.setStatus("失败");
//        ar.setSlaverid(dis.getSlaverid());
//        ar.setRuntime(new Long(0));
//        Long planid = dis.getExecplanid();
//
//        apicasesReportPerformanceMapper.addcasereport(ar);
//        //epservice.updatetestplanstatus(planid, "fail");
//        //PerformanceDispatchScheduleTask.log.info("性能任务-未找到用例对应的jmeter-class类......." + jmeterclassname);
//    }
//
//
//    public List<Dispatch> PerformanceDispatch(int Jmeternums, List<Dispatch> dispatchList) {
//        List<Dispatch> dispatchResultList = new ArrayList<>();
//        if (dispatchList.size() <= Jmeternums) {
//            dispatchResultList = dispatchList;
//        } else {
//            dispatchResultList = dispatchList.subList(0, Jmeternums);
//        }
//        return dispatchResultList;
//    }
//
//    public int GetJmeterProcess(String DictionaryCode, String DicType) {
//        List<Dictionary> slavermaxfunthreaddic = dictionaryMapper.findDicNameValueWithCode(DictionaryCode);
//
//        int JmeterProcess = 1;
//        //字典表未配置，默认取一条
//        if (slavermaxfunthreaddic.size() == 0) {
//            PerformanceDispatchScheduleTask.log.info("性能任务-字典表未配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
//        } else {
//            String slavermaxthread = slavermaxfunthreaddic.get(0).getDicitmevalue();
//            try {
//                JmeterProcess = Integer.valueOf(slavermaxthread);
//            } catch (Exception ex) {
//                PerformanceDispatchScheduleTask.log.error("性能任务-字典表未正确配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
//            }
//            PerformanceDispatchScheduleTask.log.info("性能任务-获取字典表slaver并发执行jmerter进程个数：。。。。。。。。" + slavermaxthread);
//        }
//        return JmeterProcess;
//    }
//
//
//    public boolean JmeterClassExist(String jmeterclassname, String JmeterPath) {
//        String JmeterExtJarPath = JmeterPath.replace("bin", "lib");
//        String JarPath = JmeterExtJarPath + "/ext/api-jmeter-autotest-1.0.jar";
//        boolean flag = false;
//        try {
//            //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
//            File f = new File(JarPath);
//            URL url1 = f.toURI().toURL();
//            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());
//
//            //通过jarFile和JarEntry得到所有的类
//            JarFile jar = new JarFile(JarPath);
//            //返回zip文件条目的枚举
//            Enumeration<JarEntry> enumFiles = jar.entries();
//            JarEntry entry;
//
//            //测试此枚举是否包含更多的元素
//            while (enumFiles.hasMoreElements()) {
//                entry = (JarEntry) enumFiles.nextElement();
//                if (entry.getName().indexOf("META-INF") < 0) {
//                    String classFullName = entry.getName();
//                    if (classFullName.indexOf(".class") > 0) {
//                        //去掉后缀.class
//                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
//                        if (className.equals(jmeterclassname)) {
//                            flag = true;
//                        }
//                        //打印类名
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
}
