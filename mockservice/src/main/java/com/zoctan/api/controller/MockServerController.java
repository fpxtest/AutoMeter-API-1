package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.entity.Apicases;
import com.zoctan.api.entity.Mockapi;
import com.zoctan.api.entity.Mockapirespone;
import com.zoctan.api.entity.Mockmodel;
import com.zoctan.api.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author SeasonFan
 * @date 2021/05/31
 */
@Slf4j
@RestController
@RequestMapping("/MockServer")
public class MockServerController {
    @Resource
    private MockmodelService mockmodelService;
    @Resource
    private MockapiresponeService mockapiresponeService;
    @Resource
    private MockapiService mockapiService;

    @PostMapping("/{model}/{type}/{url}")
    public ResponseEntity mockpost(@PathVariable String model, @PathVariable String type, @PathVariable String url, HttpServletRequest httpServletRequest) throws InterruptedException {
        return mock(model,type,url);
//        String apitype = "";
//        String Modelname = "";
//        if (type.equals("function")) {
//            apitype = "功能";
//        } else if (type.equals("performance")) {
//            apitype = "性能";
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MockServer-Url中必须定义function或者performance，当前为：" + type + " 请先修改！");
//        }
//        Mockmodel mockmodel = mockmodelService.getBy("modelcode", model);
//        if (mockmodel == null) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MockServer中不存在此模块：" + model);
//        } else {
//            Modelname = mockmodel.getModelname();
//        }
//
//        Condition con = new Condition(Mockapi.class);
//        con.createCriteria().andCondition("modelname = '" + Modelname + "'")
//                .andCondition("apiurl = '" + url + "'").andCondition("apitype = '" + apitype.replace("'", "''") + "'");
//        List<Mockapi> mockapiList = mockapiService.listByCondition(con);
//        if (mockapiList.size() > 0) {
//            Mockapi mockapi = mockapiList.get(0);
//            long apiid = mockapi.getId();
//            Condition responecon = new Condition(Mockapi.class);
//            responecon.createCriteria().andCondition("apiid = " + apiid).andCondition("status = '使用中'");
//            List<Mockapirespone> mockapiresponeList = mockapiresponeService.listByCondition(responecon);
//            if (mockapiresponeList.size() > 0) {
//                Mockapirespone mockapirespone = mockapiresponeList.get(0);
//                String responecontent = mockapirespone.getResponecontent();
//                String code = mockapirespone.getResponecode();
//                long timeout = mockapi.getTimeout();
//                Thread.sleep(timeout * 1000);
//                switch (code) {
//                    case "200":
//                        return ResponseEntity.ok().body(responecontent);
//                    case "400":
//                        return ResponseEntity.badRequest().body("Bad Request");
//                    case "404":
//                        return ResponseEntity.notFound().build();
//                    case "500":
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responecontent);
//                    default:
//                        return ResponseEntity.ok().body(responecontent);
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前Mock接口：" + mockapi.getApiname() + "不存在响应信息，请先完成配置");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前模块：" + Modelname + "不存在类型为：" + apitype + " url地址为：" + url + "的mock接口，请先完成配置");
//        }
    }


    @GetMapping ("/{model}/{type}/{url}")
    public ResponseEntity mockget(@PathVariable String model, @PathVariable String type, @PathVariable String url, HttpServletRequest httpServletRequest) throws InterruptedException {
        return mock(model,type,url);
    }

    private ResponseEntity mock( String model,  String type,  String url) throws InterruptedException {
        String apitype = "";
        String Modelname = "";
        if (type.equals("function")) {
            apitype = "功能";
        } else if (type.equals("performance")) {
            apitype = "性能";
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MockServer-Url中必须定义function或者performance，当前为：" + type + " 请先修改！");
        }
        Mockmodel mockmodel = mockmodelService.getBy("modelcode", model);
        if (mockmodel == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MockServer中不存在此模块：" + model);
        } else {
            Modelname = mockmodel.getModelname();
        }

        Condition con = new Condition(Mockapi.class);
        con.createCriteria().andCondition("modelname = '" + Modelname + "'")
                .andCondition("apiurl = '" + url + "'").andCondition("apitype = '" + apitype.replace("'", "''") + "'");
        List<Mockapi> mockapiList = mockapiService.listByCondition(con);
        if (mockapiList.size() > 0) {
            Mockapi mockapi = mockapiList.get(0);
            long apiid = mockapi.getId();
            Condition responecon = new Condition(Mockapi.class);
            responecon.createCriteria().andCondition("apiid = " + apiid).andCondition("status = '使用中'");
            List<Mockapirespone> mockapiresponeList = mockapiresponeService.listByCondition(responecon);
            if (mockapiresponeList.size() > 0) {
                Mockapirespone mockapirespone = mockapiresponeList.get(0);
                String responecontent = mockapirespone.getResponecontent();
                String code = mockapirespone.getResponecode();
                long timeout = mockapi.getTimeout();
                Thread.sleep(timeout * 1000);
                //保存日志表

                switch (code) {
                    case "200":
                        return ResponseEntity.ok().body(responecontent);
                    case "400":
                        return ResponseEntity.badRequest().body("Bad Request");
                    case "404":
                        return ResponseEntity.notFound().build();
                    case "500":
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responecontent);
                    default:
                        return ResponseEntity.ok().body(responecontent);
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前Mock接口：" + mockapi.getApiname() + "不存在响应信息，请先完成配置");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前模块：" + Modelname + "不存在类型为：" + apitype + " url地址为：" + url + "的mock接口，请先完成配置");
        }
    }
}
