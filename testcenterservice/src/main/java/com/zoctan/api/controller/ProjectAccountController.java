package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApicasesAssert;
import com.zoctan.api.entity.ExecuteplanTestcase;
import com.zoctan.api.entity.ProjectAccount;
import com.zoctan.api.service.ProjectAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/11/16
 */
@RestController
@RequestMapping("/project/account")
public class ProjectAccountController {
    @Resource
    private ProjectAccountService projectAccountService;

    @PostMapping
    public Result add(@RequestBody ProjectAccount projectAccount) {
        projectAccountService.save(projectAccount);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        projectAccountService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ProjectAccount projectAccount) {
        projectAccountService.update(projectAccount);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ProjectAccount projectAccount = projectAccountService.getById(id);
        return ResultGenerator.genOkResult(projectAccount);
    }

    @PostMapping("/addprojectaccounts")
    public Result addcase(@RequestBody final List<ProjectAccount> projectAccountList) {
        projectAccountService.saveprojectaccount(projectAccountList);
        return ResultGenerator.genOkResult();
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectAccount> list = projectAccountService.listAll();
        PageInfo<ProjectAccount> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/findaccountbyprojectid")
    public Result findaccountbyprojectid(@RequestBody final Map<String, Object> param) {
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<ProjectAccount> list = this.projectAccountService.findaccountbyprojectid(param);
        final PageInfo<ProjectAccount> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
