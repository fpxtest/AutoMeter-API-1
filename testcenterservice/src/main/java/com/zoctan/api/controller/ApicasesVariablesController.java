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

        Condition con=new Condition(Testvariables.class);
        con.createCriteria().andCondition("variablesname = '" + apicasesVariables.getVariablesname() + "'").orCondition("casename = '" + apicasesVariables.getCasename() + "'");
        if(apicasesVariablesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该用例,变量已经存在绑定关系");
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
        Condition con=new Condition(Testvariables.class);
        con.createCriteria().andCondition("variablesname = '" + dic.getVariablesname() + "'").andCondition("casename = '" + dic.getCasename() + "'")
                .andCondition("id <> " + dic.getId());
        if(apicasesVariablesService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该用例变量名已经存在");
        }
        else {

            this.apicasesVariablesService.updateApicasesVariables(dic);
            return ResultGenerator.genOkResult();
        }
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
}
