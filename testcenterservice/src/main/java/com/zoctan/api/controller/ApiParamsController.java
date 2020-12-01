package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.service.ApiParamsService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/05/20
 */
@RestController
@RequestMapping("/api/params")
public class ApiParamsController {
    @Resource
    private ApiParamsService apiParamsService;

    @PostMapping
    public Result add(@RequestBody ApiParams apiParams) {
        Condition con=new Condition(ApiParams.class);
        con.createCriteria().andCondition("deployunitname = '" + apiParams.getDeployunitname() + "'")
                .andCondition("apiname = '" + apiParams.getApiname() + "'")
                .andCondition("propertytype = '" + apiParams.getPropertytype() + "'");
        if(apiParamsService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("发布单元下已经存在此类型的API参数");
        }
        else
        {
            apiParamsService.save(apiParams);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apiParamsService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ApiParams apiParams) {
        apiParamsService.update(apiParams);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ApiParams apiParams = apiParamsService.getById(id);
        return ResultGenerator.genOkResult(apiParams);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ApiParams> list = apiParamsService.listAll();
        PageInfo<ApiParams> pageInfo = PageInfo.of(list);
        System.out.println(ResultGenerator.genOkResult(pageInfo));
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final ApiParams apiParams) {
        Condition con=new Condition(ApiParams.class);
        con.createCriteria().andCondition("deployunitname = '" + apiParams.getDeployunitname() + "'")
                .andCondition("apiname = '" + apiParams.getApiname() + "'")
                .andCondition("propertytype = '" + apiParams.getPropertytype() + "'")
                .andCondition("id <> " + apiParams.getId());
        if(apiParamsService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("发布单元下已经存在此类型的API参数");
        }

        else
        {
            this.apiParamsService.updateApiParams(apiParams);
            return ResultGenerator.genOkResult();
        }

    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<ApiParams> list = this.apiParamsService.findApiParamsWithName(param);
        final PageInfo<ApiParams> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 输入框查询
     */
    @PostMapping("/searchid")
    public Result searchid(@RequestBody Map<String, Object> param) {
        final List<ApiParams> list = this.apiParamsService.getApiParamsbyname(param);
        final PageInfo<ApiParams> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);    }
}
