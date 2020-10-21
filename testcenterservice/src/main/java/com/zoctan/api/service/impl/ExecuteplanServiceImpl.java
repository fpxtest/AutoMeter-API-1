package com.zoctan.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoctan.api.core.service.AbstractService;
import com.zoctan.api.core.service.HttpHeader;
import com.zoctan.api.core.service.HttpParamers;
import com.zoctan.api.core.service.Httphelp;
import com.zoctan.api.dto.TestplanCase;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.entity.Executeplan;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.mapper.DictionaryMapper;
import com.zoctan.api.mapper.ExecuteplanMapper;
import com.zoctan.api.mapper.ExecuteplanTestcaseMapper;
import com.zoctan.api.service.ExecuteplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/09/27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExecuteplanServiceImpl extends AbstractService<Executeplan> implements ExecuteplanService {
    @Resource
    private ExecuteplanMapper executeplanMapper;
    @Autowired
    private ExecuteplanTestcaseMapper executeplanTestcaseMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;


    @Override
    public List<Executeplan> findexplanWithName(Map<String, Object> params) {
        return this.executeplanMapper.findexplanWithName(params);
    }

    @Override
    public void executeplancase(List<Executeplan> testplanlist) {
        for (Executeplan plan : testplanlist) {
            Long testplanid = plan.getId();
            List<ExecuteplanTestcase> caselist = executeplanTestcaseMapper.findcasebytestplanid(testplanid);
            System.out.println(caselist);
            List<TestplanCase> execcasedata = new ArrayList<TestplanCase>();
            HttpHeader header = new HttpHeader();
            HttpParamers paramers = HttpParamers.httpPostParamers();

            List<Dictionary> jmeterpathdic = dictionaryMapper.findDicNameValueWithCode("jmeterpath");
            List<Dictionary> jmxpathdic = dictionaryMapper.findDicNameValueWithCode("jmxpath");
            String jmeterpath =jmeterpathdic.get(0).getDicitmevalue();
            String jmxpath =jmxpathdic.get(0).getDicitmevalue();
            System.out.println("jmeterpath  is:" +jmeterpath  );

            System.out.println("jmxpath  is:" +jmxpath  );


            for (ExecuteplanTestcase testcase : caselist
                    ) {
                TestplanCase tc = new TestplanCase();
                tc.setTestplanid(testplanid);
                tc.setCaseid(testcase.getTestcaseid());
                tc.setJmeterpath(jmeterpath);
                tc.setJmxpath(jmxpath);
                tc.setDeployname(testcase.getDeployunitname());
                execcasedata.add(tc);
            }
            String params = JSON.toJSONString(execcasedata);
            System.out.println("request json is:" +params  );
            List<Dictionary> dic = dictionaryMapper.findDicNameValueWithCode("dispatchservice");
            String url = dic.get(0).getDicitmevalue();
            System.out.println("request url json is:" +url  );

            try {
                Httphelp.doPost(url, params, header, 10, 10);
            } catch (IOException e) {
                System.out.println("IOException  is:" +e.getMessage()  );
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateexecuteplanname(Executeplan params) {
        this.executeplanMapper.updateexecuteplanname(params);
    }
}
