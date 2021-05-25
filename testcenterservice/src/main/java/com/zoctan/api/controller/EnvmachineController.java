package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Envmachine;
import com.zoctan.api.service.EnvmachineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Zoctan
 * @date 2020/05/21
 */
@RestController
@RequestMapping("/envmachine")
public class EnvmachineController {
    @Resource
    private EnvmachineService envmachineService;

    @PostMapping
    public Result add(@RequestBody Envmachine envmachine) {
        if(envmachineService.findexist(envmachine.getEnviromentname(),envmachine.getMachinename())!=null)
        {
            return ResultGenerator.genFailedResult("此环境下服务器已经存在");
        }
        else {
            envmachineService.save(envmachine);
            return ResultGenerator.genOkResult();
        }

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        envmachineService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Envmachine envmachine) {
        envmachineService.update(envmachine);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Envmachine envmachine = envmachineService.getById(id);
        return ResultGenerator.genOkResult(envmachine);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Envmachine> list = envmachineService.listAllEnvAndMac();
        PageInfo<Envmachine> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Envmachine envmachine) {
        if(envmachineService.findexistwithoutself(envmachine.getEnviromentname(),envmachine.getMachinename(),envmachine.getId())!=null)
        {
            return ResultGenerator.genFailedResult("此环境下服务器已经存在");
        }
        else
        {
            this.envmachineService.updateEnvAndMac(envmachine);
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
        final List<Envmachine> list = this.envmachineService.findEnvAndMacWithName(param);
        final PageInfo<Envmachine> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
