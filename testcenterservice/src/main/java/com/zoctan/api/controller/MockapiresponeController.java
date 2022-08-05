package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Mockapi;
import com.zoctan.api.entity.Mockapirespone;
import com.zoctan.api.entity.Mockmodel;
import com.zoctan.api.service.MockapiresponeService;
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
@RequestMapping("/mockapirespone")
public class MockapiresponeController {
    @Resource
    private MockapiresponeService mockapiresponeService;

    @PostMapping
    public Result add(@RequestBody Mockapirespone dic) {
        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("apiid = " + dic.getApiid())
                .andCondition("responecode = '" + dic.getResponecode().replace("'","''") + "'");
        if(mockapiresponeService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此接口已经存在此响应码");
        }
        else {
            dic.setStatus("未使用");
            mockapiresponeService.save(dic);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        mockapiresponeService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Mockapirespone mockapirespone) {
        mockapiresponeService.update(mockapirespone);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Mockapirespone mockapirespone = mockapiresponeService.getById(id);
        return ResultGenerator.genOkResult(mockapirespone);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Mockapirespone> list = mockapiresponeService.listAll();
        PageInfo<Mockapirespone> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Mockapirespone dic) {
        Condition con=new Condition(Mockmodel.class);
        con.createCriteria().andCondition("apiid = " + dic.getApiid())
                .andCondition("responecode = '" + dic.getResponecode().replace("'","''") + "'")
                .andCondition("id <> " + dic.getId());
        if(mockapiresponeService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("此接口已经存在此响应码");
        }
        else {
            this.mockapiresponeService.updateMockapirespone(dic);
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
        final List<Mockapirespone> list = this.mockapiresponeService.findMockapiresponeWithName(param);
        final PageInfo<Mockapirespone> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PostMapping("/enablerespone")
    public Result enablerespone(@RequestBody final Map<String, Object> param) {
        Integer apiid= Integer.parseInt(param.get("apiid").toString());
        Integer id= Integer.parseInt(param.get("id").toString());
        mockapiresponeService.enableapirespone(apiid,"未使用");
        mockapiresponeService.enablerespone(id,"使用中");
        return ResultGenerator.genOkResult();
    }
}
