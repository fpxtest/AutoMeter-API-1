package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesAssert;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.service.ApicasesAssertService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/10/29
 */
@RestController
@RequestMapping("/apicases/assert")
public class ApicasesAssertController {
    @Resource
    private ApicasesAssertService apicasesAssertService;

    @PostMapping
    public Result add(@RequestBody ApicasesAssert apicasesAssert) {
        apicasesAssertService.save(apicasesAssert);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apicasesAssertService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApicasesAssert apicasesAssert) {
        apicasesAssertService.update(apicasesAssert);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApicasesAssert apicasesAssert = apicasesAssertService.getById(id);
        return ResultGenerator.genOkResult(apicasesAssert);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApicasesAssert> list = apicasesAssertService.listAll();
        PageInfo<ApicasesAssert> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ApicasesAssert apicasesAssert) {
        this.apicasesAssertService.updateAssert(apicasesAssert);
        return ResultGenerator.genOkResult();
//        Condition con=new Condition(ApicasesAssert.class);
//        con.createCriteria().andCondition("asserttype = '" + apicasesAssert.getAsserttype() + "'")
//                .andCondition("caseid = "+apicasesAssert.getCaseid())
//                .andCondition("assertsubtype = '" + apicasesAssert.getAssertsubtype() + "'")
//                .andCondition("id <> " + apicasesAssert.getId());
//        if(apicasesAssertService.ifexist(con)>0)
//        {
//            return ResultGenerator.genFailedResult("断言类型已经存在");
//        }
//        else {
//            this.apicasesAssertService.updateAssert(apicasesAssert);
//            return ResultGenerator.genOkResult();
//        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ApicasesAssert> list = this.apicasesAssertService.findAssertWithName(param);
        final PageInfo<ApicasesAssert> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/getassertbycaseid")
    public Result searchbycaseid(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ApicasesAssert> list = this.apicasesAssertService.findAssertbycaseid(param);
        final PageInfo<ApicasesAssert> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
