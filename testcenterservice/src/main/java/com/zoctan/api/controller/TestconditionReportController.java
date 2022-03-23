package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.TestconditionReport;
import com.zoctan.api.entity.Testvariables;
import com.zoctan.api.service.TestconditionReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/07/17
 */
@RestController
@RequestMapping("/testcondition/report")
public class TestconditionReportController {
    @Resource
    private TestconditionReportService testconditionReportService;

    @PostMapping
    public Result add(@RequestBody TestconditionReport testconditionReport) {
        testconditionReportService.save(testconditionReport);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testconditionReportService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody TestconditionReport testconditionReport) {
        testconditionReportService.update(testconditionReport);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        TestconditionReport testconditionReport = testconditionReportService.getById(id);
        return ResultGenerator.genOkResult(testconditionReport);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TestconditionReport> list = testconditionReportService.listAll();
        PageInfo<TestconditionReport> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<TestconditionReport> list = this.testconditionReportService.findTestconditionReportWithName(param);
        final PageInfo<TestconditionReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/findconditionreport")
    public Result findconditionreport(@RequestBody final Map<String, Object> param) {
        Integer page = Integer.parseInt(param.get("page").toString());
        Integer size = Integer.parseInt(param.get("size").toString());
        Long executeplanid = Long.parseLong(param.get("executeplanid").toString());
        String batchname = param.get("batchname").toString();
        PageHelper.startPage(page, size);
        final List<TestconditionReport> list = this.testconditionReportService.findconditionreport(executeplanid,batchname);
        final PageInfo<TestconditionReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
