package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Enviroment;
import com.zoctan.api.service.EnviromentService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/04/18
 */
@RestController
@RequestMapping("/enviroment")
public class EnviromentController {
    @Resource
    private EnviromentService enviromentService;

    @PostMapping
    public Result add(@RequestBody Enviroment enviroment) {
        Condition con=new Condition(Enviroment.class);
        con.createCriteria().andCondition("enviromentname = '" + enviroment.getEnviromentname().replace("'","''") + "'");
        if(enviromentService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("环境名已经存在");
        }
        else {
            enviromentService.save(enviroment);
            return ResultGenerator.genOkResult();
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        enviromentService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Enviroment enviroment) {
            enviromentService.update(enviroment);
            return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Enviroment enviroment = enviromentService.getById(id);
        return ResultGenerator.genOkResult(enviroment);
    }

    @GetMapping("/getenviromentnum")
    public Result getenviromentnum() {
        Integer enviromentnum = enviromentService.getenviromentnum();
        return ResultGenerator.genOkResult(enviromentnum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Enviroment> list = enviromentService.listAll();
        PageInfo<Enviroment> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/ens")
    public Result listall() {
        List<Enviroment> list = enviromentService.listAll();
        return ResultGenerator.genOkResult(list);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Enviroment dic) {
        Condition con=new Condition(Enviroment.class);
        con.createCriteria().andCondition("enviromentname = '" + dic.getEnviromentname().replace("'","''") + "'").andCondition("id <> " + dic.getId());
        if(enviromentService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult("环境名已经存在");
        }
        else {

            this.enviromentService.updateEnviroment(dic);
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
        final List<Enviroment> list = this.enviromentService.findEnviromentWithName(param);
        final PageInfo<Enviroment> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
