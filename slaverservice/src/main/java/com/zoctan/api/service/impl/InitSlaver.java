package com.zoctan.api.service.impl;

import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.SlaverMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by fanseasn on 2020/11/26.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/26
*/
@Component
@Slf4j
public class InitSlaver {

    @Value("${server.port}")
    private String port;

    @Autowired
    private SlaverMapper slaverMapper;

    @PostConstruct
    public void Init() {
        InitSlaver.log.info("启动开始注册测试执行机。。。。。。。。。。。。。。");
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            String MacAddredss = "";
            try {
                MacAddredss = getMacByIP(ip);
            } catch (Exception ex) {
                InitSlaver.log.info("启动注册slaver获取mac地址失败......................................................." + ex.getMessage());
            }
            Slaver sa = new Slaver();
            sa.setMacaddredss(MacAddredss);
            sa.setIp(ip);
            sa.setSlavername("执行机" + ip);
            sa.setStype("功能");
            sa.setPort(port);
            sa.setStatus("空闲");
            sa.setMemo("执行机" + ip);
            List<Slaver> slaverList = slaverMapper.findslaverbymac(MacAddredss);
            if (slaverList.size() == 0) {
                slaverMapper.addslaver(sa);
                InitSlaver.log.info("启动注册slaver完成.......................................................");
            } else {
                Slaver slaver = slaverList.get(0);
                if(!slaver.getIp().equalsIgnoreCase(ip))
                {
                    //如果已存在此mac地址，并且ip不一样，更新为新的IP，服务器可能改过host的ip
                    slaverMapper.updateSlaver(slaver);
                    InitSlaver.log.info("slaver已存在此mac地址，并且ip不一样，更新为新的IP，服务器可能改过host的ip.......................................................");
                }
                else
                {
                    //如果IP也相同，如果是下线状态则更新为上线空闲
                    if (slaver.getStatus().equals("已下线")) {
                        slaver.setStatus("空闲");
                        slaverMapper.updateSlaver(slaver);
                        InitSlaver.log.info("slaver的mac和IP相同，如果是下线状态则更新为上线空闲.......................................................");
                    }
                }
                InitSlaver.log.info("slaver已注册.......................................................");
            }
        } catch (UnknownHostException e) {
            InitSlaver.log.info("启动注册测试执行机异常，请检查当前服务器host文件是否正常配置本机IP：" + e.getMessage());
        }

    }

    private String getMacByIP(String IP) throws Exception {
        InetAddress ia = InetAddress.getByName(IP);
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String hexString = Integer.toHexString(mac[i] & 0xFF);
            sb.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return sb.toString().toUpperCase();
    }

}
