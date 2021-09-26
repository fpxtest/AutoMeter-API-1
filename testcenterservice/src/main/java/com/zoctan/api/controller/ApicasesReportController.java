package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.CaseReportStatics;
import com.zoctan.api.entity.ApicasesReport;
import com.zoctan.api.service.ApicasesReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2020/10/16
 */
@Slf4j
@RestController
@RequestMapping("/apicases/report")
public class ApicasesReportController {
    @Resource
    private ApicasesReportService apicasesReportService;

    @PostMapping
    public Result add(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.save(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesReportService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesReport apicasesReport) {
        apicasesReportService.update(apicasesReport);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesReport apicasesReport = apicasesReportService.getById(id);
        return ResultGenerator.genOkResult(apicasesReport);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesReport> list = apicasesReportService.listallresult();
        PageInfo<ApicasesReport> pageInfo = PageInfo.of(list);
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
        final List<ApicasesReport> list = this.apicasesReportService.findApicasereportWithName(param);
        final PageInfo<ApicasesReport> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    /**
     * 输入框查询
     */
    @PostMapping("/getstaticsreport")
    public Result getstaticsreport(@RequestBody final Map<String, Object> param) {
        //ApicasesReportController.log.info(param);
        if(param.get("batchname")==null||param.get("testplanname")==null)
        {
            return ResultGenerator.genOkResult("请选中测试计划和批次");
        }
        CaseReportStatics caseReportStatics=new CaseReportStatics();
        Long casetotals = this.apicasesReportService.getApicasetotalsWithName(param);
        Map<String, Object> statusparams=param;
        statusparams.put("status","成功");
        Long passcasetotals = this.apicasesReportService.getApicasenumbystatus(statusparams);
        Long costtimes = this.apicasesReportService.getApicasecosttimes(param);
        caseReportStatics.setBatchname(param.get("batchname").toString());
        caseReportStatics.setPlanname(param.get("testplanname").toString());
        caseReportStatics.setTestcasenums(casetotals);
        caseReportStatics.setPassnums(passcasetotals);
        caseReportStatics.setFailnums(casetotals-passcasetotals);
        caseReportStatics.setCosttimes(costtimes);

        final List<CaseReportStatics> list=new ArrayList<>();
        list.add(caseReportStatics);
        final PageInfo<CaseReportStatics> pageInfo = new PageInfo<>(list);

        return ResultGenerator.genOkResult(pageInfo);
    }
}
