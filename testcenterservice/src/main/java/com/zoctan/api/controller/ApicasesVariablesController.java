package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesVariables;
import com.zoctan.api.entity.Testvariables;
import com.zoctan.api.service.ApicasesVariablesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/07/14
 */
@RestController
@RequestMapping("/apicases/variables")
public class ApicasesVariablesController {
    @Resource
    private ApicasesVariablesService apicasesVariablesService;

    @PostMapping
    public Result add(@RequestBody ApicasesVariables apicasesVariables) {
//        Condition caseidcon=new Condition(ApicasesVariables.class);
//        caseidcon.createCriteria().andCondition("caseid = " + apicasesVariables.getCaseid());
//        if(apicasesVariablesService.ifexist(caseidcon)>0)
//        {
//            ApicasesVariables apicasesVariables1= apicasesVariablesService.getBy("caseid",apicasesVariables.getCaseid());
//            return ResultGenerator.genFailedResult("该接口已经存在绑定的变量:"+apicasesVariables1.getVariablesname()+"，可以直接使用");
//        }

        Condition con=new Condition(ApicasesVariables.class);
        con.createCriteria().andCondition("variablesname = '" + apicasesVariables.getVariablesname().replace("'","''") + "'");
        if(apicasesVariablesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该接口变量已存在，请修改");
        }
        else {
            apicasesVariablesService.save(apicasesVariables);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesVariablesService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesVariables apicasesVariables) {
        apicasesVariablesService.update(apicasesVariables);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesVariables apicasesVariables = apicasesVariablesService.getById(id);
        return ResultGenerator.genOkResult(apicasesVariables);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesVariables> list = apicasesVariablesService.listAll();
        PageInfo<ApicasesVariables> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ApicasesVariables dic) {
        this.apicasesVariablesService.updateApicasesVariables(dic);
        return ResultGenerator.genOkResult();
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ApicasesVariables> list = this.apicasesVariablesService.findApicasesVariablesWithName(param);
        final PageInfo<ApicasesVariables> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/getbyvariablesid")
    public Result getbyvariablesid(@RequestBody final Map<String, Object> param) {
        final List<ApicasesVariables> apicasesVariablesList  = this.apicasesVariablesService.getbyvariablesid(param);
        return ResultGenerator.genOkResult(apicasesVariablesList);
    }
}
