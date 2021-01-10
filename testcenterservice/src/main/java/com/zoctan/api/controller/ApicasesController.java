package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.service.ApiCasedataService;
import com.zoctan.api.service.ApicasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/09/11
 */
@RestController
@RequestMapping("/apicases")
public class ApicasesController {
    @Resource
    private ApicasesService apicasesService;
    @Autowired
    private ApiCasedataService apiCasedataService;


    @PostMapping
    public Result add(@RequestBody Apicases apicases) {

        Condition con=new Condition(Apicases.class);
        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
                .andCondition("apiname = '" + apicases.getApiname() + "'").andCondition("casename = '" + apicases.getCasename() + "'")
                ;
        //.orCondition("casejmxname = '" + apicases.getCasejmxname() + "'")
        if(apicasesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            apicasesService.save(apicases);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesService.deleteById(id);
        apiCasedataService.deletcasedatabyid(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Apicases apicases) {
        apicasesService.update(apicases);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Apicases apicases = apicasesService.getById(id);
        return ResultGenerator.genOkResult(apicases);
    }

    @GetMapping("/getcasenum")
    public Result getcasenum(@RequestParam String casetype) {
        Integer apicasesnum = apicasesService.getcasenum(casetype);
        return ResultGenerator.genOkResult(apicasesnum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Apicases> list = apicasesService.listAll();
        PageInfo<Apicases> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Apicases apicases) {
//        Condition con=new Condition(Apicases.class);
//        con.createCriteria().andCondition("deployunitname = '" + apicases.getDeployunitname() + "'")
//                .andCondition("apiname = '" + apicases.getApiname() + "'")
//                .andCondition("id <> " + apicases.getId())
//                .andCondition("casename = '" + apicases.getCasename() + "'")
//                .orCondition("casejmxname = '" + apicases.getCasejmxname() + "'");
        if(apicasesService.forupdateifexist(apicases).size() >0)
        {
            return ResultGenerator.genFailedResult("用例名或者jmx名已存在");
        }
        else
        {
            this.apicasesService.updateApicase(apicases);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Apicases> list = this.apicasesService.findApiCaseWithName(param);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchbyname")
    public Result searchbyname(@RequestBody final Map<String, Object> param) {
        String deployunitname=(String)param.get("casedeployunitname");
        String apiname=(String)param.get("caseapiname");
        final List<Apicases> list = this.apicasesService.getapicasebyName(deployunitname,apiname);
        return ResultGenerator.genOkResult(list);
    }
}
