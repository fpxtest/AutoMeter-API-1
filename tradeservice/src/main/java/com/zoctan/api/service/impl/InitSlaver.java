package com.zoctan.api.service.impl;

import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.SlaverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by fanseasn on 2020/11/26.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/26
*/
@Component
public class InitSlaver {

    @Autowired
    private SlaverMapper slaverMapper;

    @PostConstruct
    public void Init()
    {
        System.out.println("启动注册slaver");
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("启动注册slaver异常"+e.getMessage());
            e.printStackTrace();
        }
        String ip=address.getHostAddress();
        Slaver sa=new Slaver();
        sa.setIp(ip);
        sa.setSlavername("");
        sa.setPort("");
        sa.setStatus("");
        sa.setStype("");
        sa.setMemo("slaver");
        if(slaverMapper.findslaverbyip(ip).size()==0)
        {
            slaverMapper.addslaver(sa);
            System.out.println("启动注册slaver完成");
        }
        else
        {
            System.out.println("启动注册slaver已注册");
        }
    }
}
