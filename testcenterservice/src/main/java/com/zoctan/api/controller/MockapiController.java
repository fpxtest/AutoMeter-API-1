package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Mockapi;
import com.zoctan.api.entity.Mockmodel;
import com.zoctan.api.service.MockapiService;
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
@RequestMapping("/mockapi")
public class MockapiController {
    @Resource
    private MockapiService mockapiService;

    @PostMapping
    public Result add(@RequestBody Mockapi dic) {

        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("apiname = '" + dic.getApiname().replace("'","''") + "'")
        .andCondition("modelname = '" + dic.getModelname().replace("'","''") + "'");
        if(mockapiService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此模块Mock接口名已经存在");
        }
        else {
            mockapiService.save(dic);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        mockapiService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Mockapi mockapi) {
        mockapiService.update(mockapi);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Mockapi mockapi = mockapiService.getById(id);
        return ResultGenerator.genOkResult(mockapi);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Mockapi> list = mockapiService.listAll();
        PageInfo<Mockapi> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Mockapi dic) {
        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("apiname = '" + dic.getApiname().replace("'","''") + "'")
                .andCondition("modelname = '" + dic.getModelname().replace("'","''") + "'")
                .andCondition("id <> " + dic.getId());
        if(mockapiService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此模块Mock接口名已经存在");
        }
        else {
            this.mockapiService.updateMockapi(dic);
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
        final List<Mockapi> list = this.mockapiService.findMockapiWithName(param);
        final PageInfo<Mockapi> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
