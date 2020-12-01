package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesCondition;
import com.zoctan.api.service.ApicasesConditionService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/11/03
 */
@RestController
@RequestMapping("/apicases_condition")
public class ApicasesConditionController {
    @Resource
    private ApicasesConditionService apicasesConditionService;

    @PostMapping
    public Result add(@RequestBody ApicasesCondition apicasesCondition) {
        Condition con = new Condition(ApicasesCondition.class);
        con.createCriteria().andCondition("caseid = '" + apicasesCondition.getCaseid() + "'")
                .andCondition("basetype = '" + apicasesCondition.getBasetype() + "'")
                .andCondition("conditionbasetype = '" + apicasesCondition.getConditionbasetype() + "'");
        if (apicasesConditionService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("用例已存在基础类型的前后置条件");
        } else {
            apicasesConditionService.save(apicasesCondition);
            return ResultGenerator.genOkResult();
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesConditionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }


    @PutMapping("/detail")
    public Result update(@RequestBody ApicasesCondition apicasesCondition) {
        Condition con = new Condition(ApicasesCondition.class);
        con.createCriteria().andCondition("caseid = '" + apicasesCondition.getCaseid() + "'")
                .andCondition("basetype = '" + apicasesCondition.getBasetype() + "'")
                .andCondition("id <> " + apicasesCondition.getId())
                .andCondition("conditionbasetype = '" + apicasesCondition.getConditionbasetype() + "'");
        if (apicasesConditionService.ifexist(con) > 0) {
            return ResultGenerator.genFailedResult("用例已存在基础类型的前后置条件");
        }
        else
        {
            apicasesConditionService.update(apicasesCondition);
            return ResultGenerator.genOkResult();
        }
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesCondition apicasesCondition = apicasesConditionService.getById(id);
        return ResultGenerator.genOkResult(apicasesCondition);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesCondition> list = apicasesConditionService.listAll();
        PageInfo<ApicasesCondition> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<ApicasesCondition> list = this.apicasesConditionService.findconditionWithName(param);
        final PageInfo<ApicasesCondition> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
