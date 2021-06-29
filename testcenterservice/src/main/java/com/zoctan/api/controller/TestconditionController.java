package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Testcondition;
import com.zoctan.api.service.TestconditionService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2021/05/31
 */
@RestController
@RequestMapping("/testcondition")
public class TestconditionController {
    @Resource
    private TestconditionService testconditionService;

    @PostMapping
    public Result add(@RequestBody Testcondition testcondition) {
        Condition con=new Condition(Testcondition.class);
        con.createCriteria().andCondition("conditionname = '" + testcondition.getConditionname() + "'");
        if(testconditionService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("该条件名已经存在");
        }
        else {
            testconditionService.save(testcondition);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        testconditionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Testcondition testcondition) {
        testconditionService.update(testcondition);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Testcondition testcondition = testconditionService.getById(id);
        return ResultGenerator.genOkResult(testcondition);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Testcondition> list = testconditionService.listAll();
        PageInfo<Testcondition> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Testcondition dic) {
        Condition con=new Condition(Testcondition.class);
        con.createCriteria().andCondition("conditionname = '" + dic.getConditionname() + "'").andCondition("id <> " + dic.getId());
        if(testconditionService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("已存在该条件名");
        }
        else {

            this.testconditionService.updateTestcondition(dic);
            return ResultGenerator.genOkResult();
        }
    }


    @GetMapping("/getalltestcondition")
    public Result getallexplan() {
        List<Testcondition> list = testconditionService.getallTestcondition();
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Testcondition> list = this.testconditionService.findtestconditionWithName(param);
        final PageInfo<Testcondition> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
