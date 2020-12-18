package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
* @author Zoctan
* @date 2020/04/17
*/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanCaseServiceImpl extends AbstractService<ExecuteplanTestcase> implements TestPlanCaseService {
@Resource
private ApicasesMapper apicaseMapper;


    @Override
    public void executeplancase(String casetype, long slaverid,long batchid,long planid, long caseid, Long thread,Long loop,String deployname, String jmeterpath, String jmxpath, String jmxcasename, String batchname,String jmeterperformancereportpath) {
        //Apicases apicase = apicaseMapper.getjmetername(caseid);
        //String jmx = jmxcasename;
        String jmetercmd="";
        if(casetype.equals(new String("功能")))
        {
             jmetercmd = jmeterpath + "/jmeter -n -t " + jmxpath  + "/apitest.jmx -Jthread=1 -Jloops=1 -Jtestplanid=" + planid + " -Jcaseid=" + caseid+ " -Jslaverid=" + slaverid+ " -Jbatchid=" + batchid+" -Jbatchname=" +batchname+" -Jtestdeployunit=" +deployname+" -Jtestclass=" +jmxcasename;
            TestPlanCaseServiceImpl.log.info("jmetercmd 功能 is :"+jmetercmd);
        }
        if(casetype.equals(new String("性能")))
        {
            String casereportfolder=jmeterperformancereportpath+"/"+planid+"-"+caseid+"-"+batchname;
            File dir = new File(casereportfolder);
            if (!dir.exists()) {// 判断目录是否存在
                TestPlanCaseServiceImpl.log.info("创建目录 :"+casereportfolder);
                dir.mkdir();
            }
            jmetercmd = jmeterpath + "/jmeter -n -t " + jmxpath  + "/apitest.jmx -Jthread="+thread+" -Jloops="+loop+" -Jtestplanid=" + planid + " -Jcaseid=" + caseid+ " -Jslaverid=" + slaverid+ " -Jbatchid=" + batchid+" -Jbatchname=" +batchname+" -Jtestdeployunit=" +deployname+" -Jcasereportfolder=" +casereportfolder+" -Jtestclass=" +jmxcasename+ " -l  "+casereportfolder+"/"+caseid+".jtl -e -o "+casereportfolder;
            TestPlanCaseServiceImpl.log.info("jmetercmd 性能 is :"+jmetercmd);
        }
        TestPlanCaseServiceImpl.log.info("jmetercmd is :"+jmetercmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.getRuntime().exec(jmetercmd);
        } catch (IOException e) {
            TestPlanCaseServiceImpl.log.info("调用jmeter异常 is :"+e.getMessage());
        }
    }
}
