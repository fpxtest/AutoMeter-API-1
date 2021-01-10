package com.zoctan.api.controller;

import com.zoctan.api.core.response.Result;
import com.zoctan.api.core.response.ResultGenerator;
import com.zoctan.api.core.rsa.RsaUtils;
import com.zoctan.api.dto.DbUserPassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/userPasswordShow")
@RestController
@Slf4j
@Api(tags = "数据库的信息")
public class DbUserPasswordShowController {

    @Resource(name = "myStringEncryptor")
    private StringEncryptor stringEncryptor;
    /** 前缀 */
    private static final String PREFIX = "MyEnc({";
    /** 后缀 */
    private static final String SUFFIX = "})";
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;


    // Base64 + RSA 加密的密码

    @GetMapping("/show")
    @ApiOperation(value = "显示密码", notes = "显示用户名密码")
    public Result<DbUserPassword> show() throws Exception {
        DbUserPassword dbUserPassword = new DbUserPassword();
        dbUserPassword.setUrl(url);
        dbUserPassword.setUserName(userName);
        dbUserPassword.setPassword(password);
        return ResultGenerator.genOkResult(dbUserPassword);
    }

}
