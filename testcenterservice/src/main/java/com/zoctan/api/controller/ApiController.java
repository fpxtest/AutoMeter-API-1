package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Api;
import com.zoctan.api.service.ApiService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/04/24
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @Resource
    private ApiService apiService;

    @PostMapping
    public Result add(@RequestBody Api api) {
        Condition con=new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname() + "'");
        if(apiService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此发布单元下已经存在此API");
        }
        else
        {
            apiService.save(api);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        apiService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Api api) {
        apiService.update(api);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Api api = apiService.getById(id);
        return ResultGenerator.genOkResult(api);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Api> list = apiService.listAll();
        PageInfo<Api> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @GetMapping("/apibydeploy")
    public Result listbydeploy(@RequestParam String deployunitname) {
        List<Api> list = apiService.listAllbydeploy(deployunitname);
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Api api) {
        Condition con=new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname() + "'")
                .andCondition("id <> " + api.getId());
        if(apiService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此发布单元下已经存在此API");
        }
        else {

            this.apiService.updateApi(api);
            return ResultGenerator.genOkResult();
        }
    }

    /**
     * 输入框查询
     */
    @PostMapping("/search")
    public Result search(@RequestBody final Map<String, Object> param) {
        PageHelper.startPage((Integer) param.get("page"), (Integer) param.get("size"));
        final List<Api> list = this.apiService.findApiWithName(param);
        final PageInfo<Api> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
