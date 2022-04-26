package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.ApicasesDebugCondition;
import com.zoctan.api.entity.ConditionApi;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.service.ApicasesDebugConditionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.service.ApicasesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/04/06
 */
@RestController
@RequestMapping("/apicases/debug/condition")
public class ApicasesDebugConditionController {
    @Resource
    private ApicasesDebugConditionService apicasesDebugConditionService;

    @Resource
    private ApicasesService apicasesService;

    @PostMapping
    public Result add(@RequestBody ApicasesDebugCondition apicasesDebugCondition) {
        apicasesDebugConditionService.save(apicasesDebugCondition);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/addcases")
    public Result addcaseslist(@RequestBody final List<ApicasesDebugCondition> casedataList) {
        apicasesDebugConditionService.saveconditionscase(casedataList);
        return ResultGenerator.genOkResult();
    }

    @PostMapping("/deletacases")
    public Result deletacases(@RequestBody final List<ApicasesDebugCondition> casedataList) {
        apicasesDebugConditionService.deletacases(casedataList);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesDebugConditionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesDebugCondition apicasesDebugCondition) {
        apicasesDebugConditionService.update(apicasesDebugCondition);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesDebugCondition apicasesDebugCondition = apicasesDebugConditionService.getById(id);
        return ResultGenerator.genOkResult(apicasesDebugCondition);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesDebugCondition> list = apicasesDebugConditionService.listAll();
        PageInfo<ApicasesDebugCondition> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/searchexit")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ApicasesDebugCondition> list = this.apicasesDebugConditionService.finddebugcondition(param);
        final PageInfo<ApicasesDebugCondition> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/searchnotexit")
    public Result searchnotexit(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        long deployunitid=Long.parseLong(param.get("deployunitid").toString());
        long conditionid=Long.parseLong(param.get("conditionid").toString());
        PageHelper.startPage(page, size);
        List<Apicases> list = this.apicasesDebugConditionService.findnotexistcase(conditionid,deployunitid);
        final PageInfo<Apicases> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
