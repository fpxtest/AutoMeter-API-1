package com.zoctan.api.service.impl;

import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.ApicasesMapper;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.ApicasesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Zoctan
* @date 2020/09/11
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApicasesServiceImpl extends AbstractService<Apicases> implements ApicasesService {
@Resource
private ApicasesMapper apicasesMapper;
    @Resource
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;



    @Override
    public List<Apicases> findApiCaseWithName(Map<String, Object> params) {
        return this.apicasesMapper.findApiCaseWithName(params);
    }

    @Override
    public List<Apicases> findApiCaseleft(Map<String, Object> params) {
        String  casetype= params.get("casetype").toString();
        Long executeplanid= Long.parseLong(params.get("executeplanid").toString());
        Long deployunitid= Long.parseLong(params.get("deployunitid").toString());
        List<Apicases> apicasesPlanList= executeplanTestcaseMapper.findcasebyplanid(executeplanid,deployunitid,casetype);
        return apicasesPlanList;

//        List<Apicases> last=new ArrayList<>();
//        List<Apicases> apicasesList= apicasesMapper.getcasebydeployunitid(deployunitid);
//        List<ExecuteplanTestcase> apicasesPlanList= executeplanTestcaseMapper.findcasebyplanid(executeplanid);
//        HashMap<Long,ExecuteplanTestcase> executeplanTestcaseHashMap=new HashMap<>();
//
//        for (ExecuteplanTestcase executeplanTestcase :apicasesPlanList) {
//            executeplanTestcaseHashMap.put(executeplanTestcase.getTestcaseid(),executeplanTestcase);
//        }
//
//        if(executeplanTestcaseHashMap.size()>0)
//        {
//            for (Apicases apicases :apicasesList) {
//                if(!executeplanTestcaseHashMap.containsKey(apicases.getId()))
//                {
//                    last.add(apicases);
//                }
//            }
//        }
//        else
//        {
//            last=apicasesList;
//        }
//        return last;
    }

    @Override
    public List<Apicases> findApiCasebynameandcasetype(Map<String, Object> params) {
        return apicasesMapper.findApiCasebynameandcasetype(params);
    }

    @Override
    public void updateApicase(Apicases params) {
        apicasesMapper.updateApicase(params);

    }

    @Override
    public int ifexist(Condition condition) {
        return countByCondition(condition);
    }

    @Override
    public List<Apicases> forupdateifexist(Apicases apicase) {
        return apicasesMapper.forupdateifexist(apicase);
    }

    @Override
    public List<Apicases> getapicasebyName(String deployunitname, String apiname) {
        return apicasesMapper.getapicasebyName(deployunitname,apiname);
    }

    @Override
    public Integer getcasenum(String casetype) {
        return apicasesMapper.getcasenum(casetype);
    }

    @Override
    public List<Apicases> getstaticsdeployunitcases() {
        return apicasesMapper.getstaticsdeployunitcases();
    }

    @Override
    public List<Apicases> getcasebydeployunitid(Long deployunitid) {
        return apicasesMapper.getcasebydeployunitid(deployunitid);
    }

    @Override
    public List<Apicases> getcasebyapiid(Long apiid) {
        return apicasesMapper.getcasebyapiid(apiid);
    }
}
