package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.entity.Mockmodel;
import com.zoctan.api.service.MockmodelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author SeasonFan
 * @date 2022/08/03
 */
@RestController
@RequestMapping("/mockmodel")
public class MockmodelController {
    @Resource
    private MockmodelService mockmodelService;

    @PostMapping
    public Result add(@RequestBody Mockmodel mockmodel) {
        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("modelname = '" + mockmodel.getModelname().replace("'","''") + "'");
        if(mockmodelService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("模块名已经存在");
        }
        else {
            mockmodelService.save(mockmodel);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        mockmodelService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Mockmodel mockmodel) {
        mockmodelService.update(mockmodel);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Mockmodel mockmodel = mockmodelService.getById(id);
        return ResultGenerator.genOkResult(mockmodel);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Mockmodel> list = mockmodelService.listAll();
        PageInfo<Mockmodel> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Mockmodel dic) {
        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("modelname = '" + dic.getModelname().replace("'","''") + "'").andCondition("id <> " + dic.getId());
        if(mockmodelService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("模块名已经存在");
        }
        else {

            this.mockmodelService.updateMockmodel(dic);
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
        final List<Mockmodel> list = this.mockmodelService.findMockmodelWithName(param);
        final PageInfo<Mockmodel> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
