package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.service.TestPlanCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author Zoctan
 * @date 2020/04/17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanCaseServiceImpl extends AbstractService<TestplanCase> implements TestPlanCaseService {
    @Resource
    private ApicasesMapper apicaseMapper;


    @Override
    public void ExecuteHttpPerformancePlanCase(String CaseType, long SlaverId, long BatchId, long PlanId, long CaseId, Long Thread, Long Loop, String DeployName, String JmeterPath, String JmxPath, String JmxCaseName, String BatchName, String JmeterPerformanceReportPath, String MysqlUrl, String MysqlUserName, String MysqlPassword) {
        String CaseReportFolder = JmeterPerformanceReportPath + "/" + PlanId + "-" + CaseId + "-" + BatchName;
        File dir = new File(CaseReportFolder);
        if (!dir.exists()) {// 判断目录是否存在
            dir.mkdir();
            TestPlanCaseServiceImpl.log.info("创建性能报告目录完成 :" + CaseReportFolder);
        }
        String JmeterCmd = JmeterPath + "/jmeter -n -t " + JmxPath + "/HttpPerformance.jmx  -Jmysqlurl=" + MysqlUrl + " -Jmysqlusername=" + MysqlUserName + " -Jmysqlpassword=" + MysqlPassword + " -Jthread=" + Thread + " -Jloops=" + Loop + " -Jtestplanid=" + PlanId + " -Jcaseid=" + CaseId + " -Jslaverid=" + SlaverId + " -Jbatchid=" + BatchId + " -Jbatchname=" + BatchName + " -Jtestdeployunit=" + DeployName + " -Jcasereportfolder=" + CaseReportFolder + " -Jtestclass=" + JmxCaseName + " -l  " + CaseReportFolder + "/" + CaseId + ".jtl -e -o " + CaseReportFolder;
        TestPlanCaseServiceImpl.log.info("性能JmeterCmd is :" + JmeterCmd);
        ExecShell(JmeterCmd);
        TestPlanCaseServiceImpl.log.info("性能JmeterCmd finish。。。。。。。。。。。。。。。。。。。。。。。。。。。。。 :");
    }

    @Override
    public void ExecuteHttpPlanFunctionCase(Long Slaverid, String JmeterPath, String JmxPath, String DispatchIds, String MysqlUrl, String MysqlUserName, String MysqlPassword,int JmeterLogFileNum) {
        String JmeterCmd = JmeterPath + "/jmeter -n -t " + JmxPath + "/HTTPFunction.jmx -Jmysqlurl=" + MysqlUrl + " -Jmysqlusername=" + MysqlUserName + " -Jmysqlpassword=" + MysqlPassword + " -Jthread=1 -Jloops=1 -JDispatchIds=" + DispatchIds+" -JSlaverid="+Slaverid+ " -j jmeter"+JmeterLogFileNum+".log ";
        TestPlanCaseServiceImpl.log.info("功能JmeterCmd  is :" + JmeterCmd);
        ExecShell(JmeterCmd);
        TestPlanCaseServiceImpl.log.info("功能JmeterCmd finish。。。。。。。。。。。。。。。。。。。。。。。。。。。。。 :");
    }

    public void ExecShell(String ShellCmd) {
        try {
            Process pro = Runtime.getRuntime().exec(ShellCmd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            TestPlanCaseServiceImpl.log.info("调用jmeter命令异常: " + e.getMessage());
        }
    }
}


