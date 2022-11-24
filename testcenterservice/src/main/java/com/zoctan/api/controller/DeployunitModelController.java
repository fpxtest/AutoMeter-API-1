package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Deployunit;
import com.zoctan.api.entity.DeployunitModel;
import com.zoctan.api.service.DeployunitModelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/11/24
 */
@RestController
@RequestMapping("/deployunit/model")
public class DeployunitModelController {
    @Resource
    private DeployunitModelService deployunitModelService;

    @PostMapping
    public Result add(@RequestBody DeployunitModel deployunitModel) {

        Condition con=new Condition(DeployunitModel.class);
        con.createCriteria().andCondition("deployunitid = "+deployunitModel.getDeployunitid())
                .andCondition("modelname = '" + deployunitModel.getModelname().replace("'","''") + "'");
        if(deployunitModelService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此微服务模块已经存在");
        }
        else
        {
            deployunitModelService.save(deployunitModel);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        deployunitModelService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody DeployunitModel deployunitModel) {
        deployunitModelService.update(deployunitModel);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        DeployunitModel deployunitModel = deployunitModelService.getById(id);
        return ResultGenerator.genOkResult(deployunitModel);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<DeployunitModel> list = deployunitModelService.listAll();
        PageInfo<DeployunitModel> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final DeployunitModel deployunit) {
        Condition con=new Condition(DeployunitModel.class);
        con.createCriteria().andCondition("deployunitid = "+deployunit.getDeployunitid())
                .andCondition("modelname = '" + deployunit.getModelname().replace("'","''") + "'")
                .andCondition("id <> " + deployunit.getId());
        if(deployunitModelService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此微服务模块已经存在");
        }
        else
        {
            this.deployunitModelService.updateDeploy(deployunit);
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
        final List<DeployunitModel> list = this.deployunitModelService.findDeployModelWithName(param);
        PageInfo<DeployunitModel> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
