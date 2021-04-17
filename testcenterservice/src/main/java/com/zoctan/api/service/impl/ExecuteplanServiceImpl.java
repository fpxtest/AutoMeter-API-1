package com.zoctan.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.exception.ServiceException;
import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.dto.Testplanandbatch;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.*;
import com.zoctan.api.service.ExecuteplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/27
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanServiceImpl extends AbstractService<Executeplan> implements ExecuteplanService {
    @Resource
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;
    @Autowired
    private ExecuteplanbatchMapper executeplanbatchMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
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
    public void executeplancase(List<Testplanandbatch> testplanlist) {
        for (Testplanandbatch plan : testplanlist) {
            Long execplanid = plan.getPlanid();
            //String batchname=plan.getBatchname();
            //Executeplanbatch epb=executeplanbatchMapper.getbatchidbyplanidandbatchname(execplanid,batchname);
            // 检查plan当前的状态，如果状态为new，stop，finish继续执行
            Executeplan ep = executeplanMapper.findexplanWithid(execplanid);
            if (!ep.getStatus().equals(new String("running"))) {
                HttpHeader header = new HttpHeader();
                String serverurl="";
                String plantype=ep.getUsetype();
                List<Slaver> slaverlist=slaverMapper.findslaverWithType(plantype);
                if(slaverlist.size()==0)
                {
                    System.out.println("未找到类型为："+plantype+"的执行机，请先完成部署slaver");
                    ExecuteplanServiceImpl.log.info("未找到类型为："+plantype+"的执行机，请先完成部署slaver");
                    throw new ServiceException("未找到类型为："+plantype+"的执行机，请先完成部署slaver");
                }
                else
                {
                    List<Dictionary> dic = dictionaryMapper.findDicNameValueWithCode("dispatchservice");
                    if(dic.size()==0) //表示没有调度服务，直接调用单机slaver
                    {
                        ExecuteplanServiceImpl.log.info("字典表未配置dispatchservice，则为单slaver模式，根据计划类型获取slaver");
                        System.out.println("字典表未配置dispatchservice，则为单slaver模式，根据计划类型获取slaver");
                        serverurl ="http://"+slaverlist.get(0).getIp()+":"+slaverlist.get(0).getPort()+"/exectestplancase/exec";
                        ExecuteplanServiceImpl.log.info("单slaver模式 slaverserverurl地址："+serverurl);
                        System.out.println("slaver server url is："+serverurl);
                    }
                    else
                    {
                        System.out.println("字典表配置dispatchservice，通过dispatchservice调度执行");
                        serverurl ="http://"+ dic.get(0).getDicitmevalue()+"/exectestplancase/exec";
                        ExecuteplanServiceImpl.log.info("字典表配置dispatchservice，调度服务地址slaverserverurl地址："+serverurl);
                    }
                }
                String params = JSON.toJSONString(plan);
                ExecuteplanServiceImpl.log.info("请求参数："+params);
                System.out.println("request json is:" + params);
                System.out.println("request url  is:" + serverurl);
                try {
                    String respon= Httphelp.doPost(serverurl, params, header, 10, 10);
                } catch (Exception e) {
                    ExecuteplanServiceImpl.log.info("发送请求异常："+e.getMessage());
                    throw new ServiceException(e.getMessage());
                }
            }
        }
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
