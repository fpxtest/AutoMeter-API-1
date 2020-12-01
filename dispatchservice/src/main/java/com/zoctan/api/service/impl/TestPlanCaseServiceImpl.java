package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.service.TestPlanCaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
* @author Zoctan
* @date 2020/04/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanCaseServiceImpl extends AbstractService<ExecuteplanTestcase> implements TestPlanCaseService {
@Resource
private ApicasesMapper apicaseMapper;


    @Override
    public void executeplancase(long planid, long caseid, String deployname, String jmeterpath, String jmxpath, String jmxcasename, String batchname) {
        //Apicases apicase = apicaseMapper.getjmetername(caseid);
        //String jmx = jmxcasename;
        String jmetercmd = jmeterpath + "/jmeter -n -t " + jmxpath  + "/apitest.jmx -Jthread=1 -Jloops=1 -Jtestplanid=" + planid + " -Jcaseid=" + caseid+" -Jbatchname=" +batchname+ " -Jcasenum=1"+" -Jtestdeployunit=" +deployname+" -Jtestclass=" +jmxcasename;
        System.out.println("jmetercmd is :"+jmetercmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.getRuntime().exec(jmetercmd);
//            try {
//                //process.waitFor();
//            } catch (InterruptedException e) {
//                System.out.println("jmetercmd InterruptedException is :"+e.getMessage());
//                e.printStackTrace();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
