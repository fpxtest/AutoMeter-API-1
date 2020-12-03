package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.entity.Dispatch;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.service.ApicasesReportService;
import com.zoctan.api.service.DispatchService;
import com.zoctan.api.service.ExecuteplanService;
import com.zoctan.api.service.TestPlanCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Zoctan
 * @date 2020/04/17
 */
@RestController
@RequestMapping("/exectestplancase")
public class TestPlanCaseController {
    @Resource
    private TestPlanCaseService tpcservice;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ExecuteplanService epservice;
    @Autowired
    private ApicasesReportService apicasereportservice;
    @Autowired
    private DispatchService dispatchService;


    @PostMapping("/exec")
    public Result exec(@RequestBody List<TestplanCase> plancaseList) {
        // 调用testcenter需要模拟下admin登录，调用Request URL: http://localhost:8080/account/token  {name: "admin", password: "admin123"}
        // 在请求头里面加上Authorization = token

        //需要执行的用例，先进入调度，由调度定时器统一执行
        for (TestplanCase pla:plancaseList)
        {
            Dispatch dis=new Dispatch();
            dis.setExpect(pla.getExpect());
            dis.setExecplanid(pla.getExecplanid());
            dis.setTestcaseid(pla.getTestcaseid());
            dis.setDeployunitname(pla.getDeployunitname());
            dis.setStatus("待分配");
            dis.setBatchname(pla.getBatchname());
            dis.setBatchid(pla.getBatchid());
            dis.setCasejmxname(pla.getCasejmxname());
            dis.setExecplanname(pla.getPlanname());
            dis.setSlaverid(pla.getSlaverid());
            dis.setSlavername(pla.getSlavername());
            dis.setTestcasename(pla.getCasename());
            dispatchService.save(dis);
        }
//        List<Long> planlist=new ArrayList<>();
//        for (Dispatch tc:dispatchList)
//        {
//            if(!planlist.contains(tc.getExecplanid()))
//            {
//                planlist.add(tc.getExecplanid());
//            }
//        }
//        for (Long planid: planlist)
//        {
//            //更新执行计划状态为运行中
//            //epservice.updatetestplanstatus(planid,"running");
//            for (Dispatch dis:dispatchList)
//            {


//                if(planid.equals(tc.getTestplanid()))
//                {
//                    // 判断用例调用的jmx文件是否存在，如果未找到，返回客户端
//                    String jmxcasename=tc.getCasejmxname();
//                    String casename =tc.getCasename();
//                    System.out.println("用例名 is......."+casename);
//                    //String jmxcasepath=tc.getJmxpath()+"/"+tc.getDeployname()+"/"+jmxcasename+".jmx";
//                    //System.out.println("jmx文件 is......."+jmxcasepath);
//
//                    String jmeterpath=tc.getJmeterpath();
//                    jmeterpath=jmeterpath.replace("bin","lib");
//                    String jarpath=jmeterpath+"/ext/api-jmeter-autotest-1.0.jar";
//                    System.out.println("jarpath is......."+jarpath);
//                    String jmeterclassname="com.api.autotest.test."+tc.getDeployname()+"."+jmxcasename;
//                    System.out.println("jmeterclassname is......."+jmeterclassname);
//                    if(!jmeterclassexistornot(jarpath,jmeterclassname))
//                    {
//                        // 未找到用例对应的jmeter-class文件，当前用例失败，并且记录计划状态
//                        ApicasesReport ar=new ApicasesReport();
//                        ar.setTestplanid(tc.getTestplanid());
//                        ar.setCaseid(tc.getCaseid());
//                        ar.setCasename(tc.getCasename());
//                        ar.setErrorinfo("执行用例："+casename+" |未找到用例对应的jmeter-class类："+jmeterclassname+" 请检查是否已经开发提交");
//                        ar.setBatchname(tc.getBatchname());
//                        ar.setExpect(tc.getExpect());
//                        ar.setStatus("失败");
//                        ar.setRuntime(new Long(0));
//
//                        apicasereportservice.addcasereport(ar);
//                        epservice.updatetestplanstatus(planid,"fail");
//                        //return ResultGenerator.genFailedResult("执行用例："+casename+" |未找到用例对应的jmeter-class类："+jmeterclassname+" 请检查是否已经开发提交");
//                    }
//                    else
//                    {
//                        // 增加逻辑 获取计划的当前状态，如果为stop，放弃整个循环执行,return 掉
//                        tpcservice.executeplancase(tc.getTestplanid(),tc.getCaseid(),tc.getDeployname(),tc.getJmeterpath(),tc.getJmxpath(),jmxcasename,tc.getBatchname());
//                    }
//                }
            //}
            //更新执行计划状态为已完成
           // epservice.updatetestplanstatus(planid,"finish");
        //}
        return ResultGenerator.genOkResult();
    }

    public boolean jmeterclassexistornot(String jarpath,String jmeterclassname)
    {
        boolean flag=false;
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
                        if (className.equals(jmeterclassname))
                        {
                            flag=true;
                        }
                        //打印类名
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~打印类名:~~~~~~~~~~~~~~~~~~~~~~~~~" + className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @GetMapping("/test")
    public Result gettest(@RequestBody ExecuteplanTestcase plancase) {
        //tpcservice.executeplancase(plancase.getExecuteplanid(),plancase.getTestcaseid());
        return ResultGenerator.genOkResult();
    }

}
