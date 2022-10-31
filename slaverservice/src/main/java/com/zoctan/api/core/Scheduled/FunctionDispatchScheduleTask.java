package com.zoctan.api.core.Scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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
public class FunctionDispatchScheduleTask {
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
//    private ApicasesReportService apicasereportservice;
//    @Autowired(required = false)
//    private ExecuteplanService epservice;
//    @Autowired(required = false)
//    private TestPlanCaseService tpcservice;
//    @Autowired(required = false)
//    private DeployunitService deployunitService;
//
//    //3.添加定时任务
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
//                FunctionDispatchScheduleTask.log.error("功能任务-没有找到slaver。。。。。。。。" + "未找到ip为：" + ip + "的slaver，请检查调度中心-执行节点");
//                return;
//            }
//            String SlaverType = slaverlist.get(0).getStype();
//            if (SlaverType.equals(new String("性能"))) {
//                FunctionDispatchScheduleTask.log.info("功能任务-当前slaver配置为性能执行机，功能任务忽略。。。。。");
//                return;
//            }
//            Long SlaverId = slaverlist.get(0).getId();
//
//            List<Dispatch> DoingdispatchAllList = dispatchMapper.getdoingcasebyslaverid(SlaverId, "已分配", SlaverType);
//            if(DoingdispatchAllList.size()>0)
//            {
//                FunctionDispatchScheduleTask.log.info("功能任务-当前slaver还未完成测试，不进行任何工作。。。。。");
//                return;
//            }
//            String redisKey = "dispatch-RedisLock" + UUID.randomUUID().toString() + ip + SlaverType;
//            long redis_default_expire_time = 2000;
//            //默认上锁时间为五小时
//            //此key存放的值为任务执行的ip，
//            // redis_default_expire_time 不能设置为永久，避免死锁
//            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
//            if (lock) {
//                FunctionDispatchScheduleTask.log.info("功能任务-============获得redis分布式锁成功=======================");
//                String ProjectPath = System.getProperty("user.dir");
//                String JmeterPath = ProjectPath + "/slaverservice/apache-jmeter-5.3/bin";
//                String JmxPath = ProjectPath + "/slaverservice/servicejmxcase";
//                FunctionDispatchScheduleTask.log.info("功能任务-jmeter可执行路径  is:" + JmeterPath + " jmx文件路径  is:" + JmxPath);
//
//                List<Dispatch> dispatchAllList = dispatchMapper.getcasebyslaverid(SlaverId, "待分配", SlaverType, new Long(100));
//                FunctionDispatchScheduleTask.log.info("功能任务-获取待执行的功能用例数：。。。。。。。。。。。。。。。。。。。。。。。。" + dispatchAllList.size());
//                int FunctionJmeter = GetJmeterProcess("FunctionJmeterProcess", "功能");
//
//                HashMap<String, List<Dispatch>> ProtocolDispatchRun = GetProtocolDispatch(dispatchAllList);
//                for (String Protocol : ProtocolDispatchRun.keySet()) {
//                    int ProtocolJmeterNum=0;
//                    if(FunctionJmeter==1)
//                    {
//                        ProtocolJmeterNum=1;
//                    }
//                    else
//                    {
//                         ProtocolJmeterNum = FunctionJmeter / ProtocolDispatchRun.size();
//                    }
//                    FunctionDispatchScheduleTask.log.info("功能任务-ProtocolJmeterNum：。。。。。。。。" + ProtocolJmeterNum);
//
//                    List<List<Dispatch>> last = FunctionDispatch(ProtocolJmeterNum, ProtocolDispatchRun.get(Protocol));
//                    if (Protocol.equals(new String("http"))) {
//                        for (List<Dispatch> JmeterList : last) {
//                            String DispatchIDs = "";
//                            for (Dispatch dis : JmeterList) {
//                                DispatchIDs = dis.getId() + "," + DispatchIDs;
//                                dispatchMapper.updatedispatchstatus("已分配", dis.getSlaverid(), dis.getExecplanid(), dis.getBatchid(), dis.getTestcaseid());
//                                FunctionDispatchScheduleTask.log.info("功能任务-更新调度状态为已分配：。。。。。。。。" + dis.getId());
//                            }
//                            if(!DispatchIDs.isEmpty())
//                            {
//                                DispatchIDs=DispatchIDs.substring(0,DispatchIDs.length()-1);
//                                FunctionDispatchScheduleTask.log.info("功能任务-DispatchIDs:======================="+DispatchIDs);
//                                tpcservice.ExecuteHttpPlanFunctionCase(JmeterPath, JmxPath,DispatchIDs, url, username, password);
//                            }
//                        }
//                    }
//                    if (Protocol.equals(new String("rpc"))) {
//                        String JmeterClassName = "";
//                        String DeployUnitNameForJmeter = "";
//                    }
//                }
//
//                //TODO 执行任务结束后需要释放锁
//                //释放锁
//                redisUtils.deletekey(redisKey);
//                FunctionDispatchScheduleTask.log.info("功能任务-============释放分布式锁成功=======================");
//            } else {
//                FunctionDispatchScheduleTask.log.info("功能任务-============获得分布式锁失败=======================");
//                ip = (String) redisUtils.getkey(redisKey);
//                FunctionDispatchScheduleTask.log.info("功能任务-============{}机器上占用分布式锁，正在执行中=======================" + ip);
//                return;
//            }
//        } catch (Exception ex) {
//            FunctionDispatchScheduleTask.log.info("功能任务-调度定时器异常: " + ex.getMessage());
//        }
//
//    }
//
//
//    public void JmeterClassNotExist(Dispatch dis, String jmeterclassname, String casename) {
//        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
//        ApicasesReport ar = new ApicasesReport();
//        ar.setTestplanid(dis.getExecplanid());
//        ar.setCaseid(dis.getTestcaseid());
//        ar.setCasename(dis.getTestcasename());
//        ar.setErrorinfo("功能任务-执行用例：" + casename + " |未找到用例对应的jmeter-class类：" + jmeterclassname + " 请检查是否已经开发提交");
//        ar.setBatchname(dis.getBatchname());
//        ar.setExpect(dis.getExpect());
//        ar.setStatus("失败");
//        ar.setRuntime(new Long(0));
//        Long planid = dis.getExecplanid();
//
//        apicasereportservice.addcasereport(ar);
//        epservice.updatetestplanstatus(planid, "fail");
//        FunctionDispatchScheduleTask.log.info("功能任务-未找到用例对应的jmeter-class类......." + jmeterclassname);
//    }
//
//    //功能用例平均分配
//    public List<List<Dispatch>> FunctionDispatch(int Jmeternums, List<Dispatch> dispatchList) {
//        if (dispatchList.size() < Jmeternums) {
//            Jmeternums = dispatchList.size();
//        }
//        List<List<Dispatch>> LastDispatchList = new ArrayList<List<Dispatch>>();
//        List<Dispatch> splitdispatchList;
//        int sizemode = (dispatchList.size()) / Jmeternums;
//        int sizeleft = (dispatchList.size()) % Jmeternums;
//        int j = 0;
//        int x = 0;
//        for (int i = 0; i < Jmeternums; i++) {
//            splitdispatchList = new ArrayList<Dispatch>();
//            for (j = x; j < (sizemode + x); j++) {
//                Dispatch dis = dispatchList.get(j);
//                splitdispatchList.add(dis);
//            }
//            x = j;
//            LastDispatchList.add(splitdispatchList);
//        }
//        if (sizeleft != 0) {
//            for (int y = 1; y < sizeleft + 1; y++) {
//                Dispatch dis = dispatchList.get(dispatchList.size() - y);
//                LastDispatchList.get(LastDispatchList.size() - 1).add(dis);
//            }
//        }
//        return LastDispatchList;
//    }
//
//
//    public int GetJmeterProcess(String DictionaryCode, String DicType) {
//        List<Dictionary> slavermaxfunthreaddic = dictionaryMapper.findDicNameValueWithCode(DictionaryCode);
//
//        int JmeterProcess = 1;
//        //字典表未配置，默认取一条
//        if (slavermaxfunthreaddic.size() == 0) {
//            FunctionDispatchScheduleTask.log.info("功能任务-字典表未配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
//        } else {
//            String slavermaxthread = slavermaxfunthreaddic.get(0).getDicitmevalue();
//            try {
//                JmeterProcess = Integer.valueOf(slavermaxthread);
//            } catch (Exception ex) {
//                FunctionDispatchScheduleTask.log.error("功能任务-字典表未正确配置" + DicType + "slaver并发执行jmerter进程个数，默认为1");
//            }
//            FunctionDispatchScheduleTask.log.info("功能任务-获取字典表slaver并发执行jmerter进程个数：。。。。。。。。" + slavermaxthread);
//        }
//        return JmeterProcess;
//    }
//
//    public HashMap<String, List<Dispatch>> GetProtocolDispatch(List<Dispatch> dispatchList) {
//        List<Dispatch> dispatchResultList = new ArrayList<>();
//        HashMap<Long, List<Dispatch>> GroupDispatch = new HashMap<Long, List<Dispatch>>();
//
//        //获取计划id分组中的第一组列表
//        for (Dispatch dispatch : dispatchList) {
//            Long planid = dispatch.getExecplanid();
//            if (!GroupDispatch.containsKey(planid)) {
//                List<Dispatch> dispatchListtmp = new ArrayList<>();
//                dispatchListtmp.add(dispatch);
//                GroupDispatch.put(planid, dispatchListtmp);
//            } else {
//                GroupDispatch.get(planid).add(dispatch);
//            }
//        }
//        for (Long planid : GroupDispatch.keySet()) {
//            dispatchResultList = GroupDispatch.get(planid);
//            break;
//        }
//
//        //获取微服务分组列表
//        HashMap<String, List<Dispatch>> DeployUnitGroupDispatch = new HashMap<String, List<Dispatch>>();
//        for (Dispatch dispatch : dispatchResultList) {
//            String DeployUnit = dispatch.getDeployunitname();
//            if (!DeployUnitGroupDispatch.containsKey(DeployUnit)) {
//                List<Dispatch> dispatchListtmp = new ArrayList<>();
//                dispatchListtmp.add(dispatch);
//                DeployUnitGroupDispatch.put(DeployUnit, dispatchListtmp);
//            } else {
//                DeployUnitGroupDispatch.get(DeployUnit).add(dispatch);
//            }
//        }
//
//        //合并协议列表
//        HashMap<String, List<Dispatch>> ProtocolGroupDispatch = new HashMap<String, List<Dispatch>>();
//
//        for (String DeployUnit : DeployUnitGroupDispatch.keySet()) {
//            Deployunit deployunit = deployunitService.findDeployNameValueWithCode(DeployUnit);
//            String Protocal = deployunit.getProtocal();
//            if (Protocal.equals(new String("http")) || Protocal.equals(new String("https"))) {
//                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "http");
//            }
//            if (Protocal.equals(new String("rpc"))) {
//                ProtocolGroupDispatch = MergeCaseList(ProtocolGroupDispatch, DeployUnitGroupDispatch, DeployUnit, "rpc");
//            }
//        }
//        return ProtocolGroupDispatch;
//    }
//
//    public HashMap<String, List<Dispatch>> MergeCaseList(HashMap<String, List<Dispatch>> ProtocolGroupDispatch, HashMap<String, List<Dispatch>> DeployUnitGroupDispatch, String DeployUnit, String Protocol) {
//        HashMap<String, List<Dispatch>> ProtocolGroupResultDispatch = ProtocolGroupDispatch;
//        if (!ProtocolGroupResultDispatch.containsKey(Protocol)) {
//            List<Dispatch> dispatchListtmp = new ArrayList<>();
//            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
//                dispatchListtmp.add(dis);
//            }
//            ProtocolGroupResultDispatch.put(Protocol, dispatchListtmp);
//        } else {
//            for (Dispatch dis : DeployUnitGroupDispatch.get(DeployUnit)) {
//                ProtocolGroupResultDispatch.get(Protocol).add(dis);
//            }
//        }
//        return ProtocolGroupResultDispatch;
//    }
//
//    public boolean jmeterclassexistornot(String jmeterclassname, String JmeterPath) {
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
