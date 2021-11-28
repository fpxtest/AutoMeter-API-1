package com.zoctan.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.exception.ServiceException;
import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.*;
import com.zoctan.api.service.ExecuteplanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/27
 */
@Service
@Slf4j
@Transactional  //(rollbackFor = Exception.class)
public class ExecuteplanServiceImpl extends AbstractService<Executeplan> implements ExecuteplanService {
    @Value("${spring.dispatchserver.serverurl}")
    private String dispatchserver;
    @Resource
    private ExecuteplanMapper executeplanMapper;
    @Resource
    private SlaverMapper slaverMapper;


    @Override
    public List<Executeplan> findexplanWithName(Map<String, Object> params) {
        return this.executeplanMapper.findexplanWithName(params);
    }

    @Override
    public int ifexist(Condition con) {
        return countByCondition(con);
    }

    @Override
    public List<Executeplan> getallexplan() {
        return executeplanMapper.getallexplan();
    }

    @Override
    public List<Executeplan> getallexplanbytype(String usetype) {
        return executeplanMapper.getallexplanbytype(usetype);
    }

    @Override
    public Integer getexecuteplannum() {
        return executeplanMapper.getexecuteplannum();
    }

    @Override
    public List<String> getstaticsplan() {
        return executeplanMapper.getstaticsplan();
    }

    @Override
    @Transactional(noRollbackFor =Exception.class )
    public void executeplancase(List<Testplanandbatch> testplanlist) {
        for (Testplanandbatch plan : testplanlist) {
            Long execplanid = plan.getPlanid();
            Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
            HttpHeader header = new HttpHeader();
            String DispatchServerurl=dispatchserver+"/exectestplancase/exec";
            String plantype=ep.getUsetype();
            List<Slaver> slaverlist=slaverMapper.findslaverWithType(plantype);
            slaverlist=GetAliveSlaver(slaverlist);
            if(slaverlist.size()==0)
            {
                ExecuteplanServiceImpl.log.info("未找到可用的："+plantype+"的测试执行机，或者执行机已下线，请检查部署");
                throw new ServiceException("未找到可用的："+plantype+"的测试执行机，或者执行机已下线，请检查部署");
            }
            else
            {
                String params = JSON.toJSONString(plan);
                ExecuteplanServiceImpl.log.info("计划请求调度参数："+params);
                try {
                    ExecuteplanServiceImpl.log.info("计划开始请求调度。。。。。。。。。。。。。。。。。。。。。。。。");
                    String respon= Httphelp.doPost(DispatchServerurl, params, header, 30000, 30000);
                    ExecuteplanServiceImpl.log.info("计划发送调度请求响应。。。。。。。。。。。。。。。。。。。。。。。。："+respon);
                } catch (Exception e) {
                    ExecuteplanServiceImpl.log.info("计划发送调度请求异常："+e.getMessage());
                    throw new ServiceException(e.getMessage());
                }
            }
        }
    }

    @Transactional(noRollbackFor =Exception.class )
    public List<Slaver> GetAliveSlaver(List<Slaver> SlaverList)
    {
        List<Slaver> AliveList=new ArrayList<>();
        for (Slaver slaver:SlaverList) {
            String IP=slaver.getIp();
            String Port=slaver.getPort();
            String ServerUrl = "http://" + IP + ":" + Port + "/exectestplancase/test";
            ExecuteplanTestcase plancase=new ExecuteplanTestcase();
            String params = JSON.toJSONString(plancase);
            HttpHeader header = new HttpHeader();
            String respon="";
            try {
                respon = Httphelp.doPost(ServerUrl, params, header, 5000,5000);
            } catch (Exception e) {
                ExecuteplanServiceImpl.log.info("检测："+ServerUrl+"请求响应结果。。。。。。。。。。。。。。。。。。。。。。。。："+e.getMessage());
                slaverMapper.updateSlaverStatus(slaver.getId(),"已下线");
                respon=e.getMessage();
            }
            if(respon.contains("200"))
            {
                AliveList.add(slaver);
            }
        }
        return AliveList;
    }

    @Override
    public void updatetestplanstatus(Long id, String status) {
        this.executeplanMapper.updatetestplanstatus(id, status);
    }

    @Override
    public Executeplan findexplanWithid(Long id) {
        return null;
    }

    @Override
    public void updateexecuteplanname(Executeplan params) {
        this.executeplanMapper.updateexecuteplanname(params);
    }
}
