package com.zoctan.api.core.Scheduled;

import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.SlaverMapper;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by fanseasn on 2020/11/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/21
*/
@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Component
public class AutoFixSlaverScheduleTask {
    @Autowired(required = false)
    RedisUtils redisUtils;
    @Autowired(required = false)
    private SlaverMapper slaverMapper;
    //private String redisKey = "";

    //3.添加定时任务
    @Scheduled(cron = "0/3 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String ip = null;
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            String redisKey = "AutoFixSlaver-RedisLock" + new Date() + ip ;
            long redis_default_expire_time = 2000;
            //默认上锁时间为五小时
            //此key存放的值为任务执行的ip，
            // redis_default_expire_time 不能设置为永久，避免死锁
            boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
            if (lock) {
                //TODO 执行任务结束后需要释放锁
                try {
                    String MacAddress=getMacByIP(ip);
                    List<Slaver> slaverList= slaverMapper.findslaverbymac(MacAddress);
                    if(slaverList.size()>0)
                    {
                        Slaver slaver=slaverList.get(0);
                        if(!slaver.getIp().equalsIgnoreCase(ip))
                        {
                            slaver.setIp(ip);
                            slaver.setSlavername("执行机"+ip);
                            slaverMapper.updateSlaver(slaver);
                            AutoFixSlaverScheduleTask.log.info("自动修复Slaver更新IP为======================="+ip);
                        }
                        else
                        {
                            if(slaver.getStatus().equals("已下线"))
                            {
                                slaver.setStatus("空闲");
                                slaverMapper.updateSlaver(slaver);
                                AutoFixSlaverScheduleTask.log.info("自动修复Slaver为空闲状态完成=======================");
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    AutoFixSlaverScheduleTask.log.info("自动修复Slaver任务-异常======================="+ex.getMessage());
                }
                finally {
                    //释放锁
                    redisUtils.deletekey(redisKey);
                }
                AutoFixSlaverScheduleTask.log.info("自动修复Slaver任务-============释放分布式锁成功=======================");
            } else {
                AutoFixSlaverScheduleTask.log.info("自动修复Slaver-============获得分布式锁失败=======================");
                ip = redisUtils.getkey(redisKey);
                AutoFixSlaverScheduleTask.log.info("自动修复Slaver-============{}机器上占用分布式锁，正在执行中======================="+redisKey +" ip :" +ip);
                return;
            }
        } catch (Exception ex) {
            AutoFixSlaverScheduleTask.log.info("自动修复Slaver-调度定时器异常: " + ex.getMessage());
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
