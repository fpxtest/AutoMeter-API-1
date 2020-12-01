package com.zoctan.api.core.Scheduled;

import com.zoctan.api.core.config.RedisUtils;
import com.zoctan.api.entity.Dictionary;
import com.zoctan.api.mapper.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by fanseasn on 2020/11/21.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/21
*/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Component
public class ScheduleTask {
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired(required = false)
    RedisUtils redisUtils;

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String ip = "test";
        String redisKey = "demo-RedisLockTest-isRun";
        long redis_default_expire_time = 20;
        //默认上锁时间为五小时
        //此key存放的值为任务执行的ip，
        // redis_default_expire_time 不能设置为永久，避免死锁
        boolean lock = redisUtils.tryLock(redisKey, ip, redis_default_expire_time);
        System.out.println("============本次聚类定时任务开始==============");
        if (lock) {
            System.out.println("============获得分布式锁成功=======================");


            List<Dictionary> jmeterpathdic = dictionaryMapper.findDicNameValueWithCode("jmeterpath");
            List<Dictionary> jmxpathdic = dictionaryMapper.findDicNameValueWithCode("jmxpath");
            String jmeterpath = jmeterpathdic.get(0).getDicitmevalue();
            String jmxpath = jmxpathdic.get(0).getDicitmevalue();
            System.out.println("jmeterpath  is:" + jmeterpath);
            System.out.println("jmxpath  is:" + jmxpath);

            //TODO 开始执行任务 执行结束后需要释放锁
            //释放锁
            //redisUtils.deletekey(redisKey);
            //System.out.println("============释放分布式锁成功=======================");

        } else {
            System.out.println("============获得分布式锁失败=======================");
            ip = (String) redisUtils.getkey(redisKey);
            System.out.println("============{}机器上占用分布式锁，聚类任务正在执行=======================");
            System.out.println("============本次聚类定时任务结束==============");
            return;
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
