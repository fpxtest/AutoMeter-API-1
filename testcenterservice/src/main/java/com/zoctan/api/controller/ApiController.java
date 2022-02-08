package com.zoctan.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.dto.StaticsDataForPie;
import com.zoctan.api.entity.Api;
import com.zoctan.api.entity.ApiParams;
import com.zoctan.api.service.ApiParamsService;
import com.zoctan.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private ApiParamsService apiParamsService;


    @PostMapping("/exportpostman")
    public Result exportpostman(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 设置文件存储路径         *************************************************
            String filePath = "./FILE/KING/";
            String path = filePath + fileName;
            File dest = new File(new File(path).getAbsolutePath());// dist为文件，有多级目录的文件
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genOkResult();
    }

    @PostMapping
    public Result add(@RequestBody Api api) {
        Condition con=new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname().replace("'","''") + "'");
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
        apiParamsService.deletebyApiid(id);
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

    @GetMapping("/getapinum")
    public Result getapinum() {
        Integer apinum = apiService.getapinum();
        return ResultGenerator.genOkResult(apinum);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Api> list = apiService.listAll();
        PageInfo<Api> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    /**
     * 根据发布单元id获取apis
     */
    @PostMapping("/getapibydeployunitid")
    public Result getapibydeployunitid(@RequestBody final Map<String, Object> param) {
        String deployunitid=param.get("sourcedeployunitid").toString();
        final List<Api> list = this.apiService.getapibydeployunitid(Long.parseLong(deployunitid));
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getstaticsdeployapi")
    public Result getstaticsdeployapi() {
        List<Api> list = apiService.getstaticsdeployapi();
        List<StaticsDataForPie> result=new ArrayList<>();
        for (Api api: list) {
            StaticsDataForPie staticsDataForPie =new StaticsDataForPie();
            staticsDataForPie.setValue(api.getId());
            staticsDataForPie.setName(api.getDeployunitname());
            result.add(staticsDataForPie);
        }
        return ResultGenerator.genOkResult(result);
    }


    @GetMapping("/apibydeploy")
    public Result listbydeploy(@RequestParam String deployunitname) {
        List<Api> list = apiService.listAllbydeploy(deployunitname);
        return ResultGenerator.genOkResult(list);
    }

    @GetMapping("/getresponetypebydeployandapiname")
    public Result getresponetypebydeployandapiname(@RequestParam String deployunitname,@RequestParam String apiname) {
        Api apilist = apiService.getresponetypebydeployandapiname(deployunitname,apiname);
        return ResultGenerator.genOkResult(apilist);
    }

    /**
     * 更新自己的资料
     */
    @PutMapping("/detail")
    public Result updateDeploy(@RequestBody final Api api) {
        Condition con=new Condition(Api.class);
        con.createCriteria().andCondition("deployunitname = '" + api.getDeployunitname() + "'")
                .andCondition("apiname = '" + api.getApiname().replace("'","''") + "'")
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
        Integer page= Integer.parseInt(param.get("page").toString());
        Integer size= Integer.parseInt(param.get("size").toString());
        PageHelper.startPage(page, size);
        final List<Api> list = this.apiService.findApiWithName(param);
        final PageInfo<Api> pageInfo = new PageInfo<>(list);
        return ResultGenerator.genOkResult(pageInfo);
    }


    @PostMapping("/copyapi")
    public Result copyapi(@RequestBody final Map<String, Object> param) {
        String sourceapiid=param.get("sourceapiid").toString();
        String sourcedeployunitid=param.get("sourcedeployunitid").toString();
        String sourcedeployunitname=param.get("sourcedeployunitname").toString();
        String objectdeployunitid=param.get("objectdeployunitid").toString();
        String objectdeployunitname=param.get("objectdeployunitname").toString();
        String newapiname=param.get("newapiname").toString();

        Condition con=new Condition(Api.class);
        con.createCriteria().andCondition("deployunitid = " + objectdeployunitid)
                .andCondition("apiname = '" + newapiname + "'");
        if(apiService.ifexist(con)>0)
        {
            return ResultGenerator.genFailedResult(objectdeployunitname+"已存在存在此API名");
        }
        else
        {
            //复制api
            Api api =apiService.getBy("id",Long.parseLong(sourceapiid));
            Condition apiparamscon=new Condition(ApiParams.class);
            apiparamscon.createCriteria().andCondition("apiid = " + Long.parseLong(sourceapiid));
            List<ApiParams> apiParamsList= apiParamsService.listByCondition(apiparamscon);
            api.setDeployunitid(Long.parseLong(objectdeployunitid));
            api.setDeployunitname(objectdeployunitname);
            api.setApiname(newapiname);
            api.setId(null);
            Date date=new Date();
            api.setCreateTime(date);
            api.setLastmodifyTime(date);
            apiService.save(api);
            Long ApiId= api.getId();
            //复制api参数
            for (ApiParams apiParams:apiParamsList)
            {
                apiParams.setApiid(ApiId);
                apiParams.setApiname(newapiname);
                apiParams.setDeployunitid(Long.parseLong(objectdeployunitid));
                apiParams.setDeployunitname(objectdeployunitname);
                apiParams.setId(null);
                apiParams.setCreateTime(date);
                apiParams.setLastmodifyTime(date);
                apiParamsService.save(apiParams);
            }
            return ResultGenerator.genOkResult();
        }
    }
}
