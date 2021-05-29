package com.zoctan.api.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by fanseasn on 2020/11/23.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/23
*/
@Component
public class RedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 上锁
     * 将键值对设定一个指定的时间timeout.
     *
     * @param key
     * @param timeout 键值对缓存的时间，单位是秒
     * @return 设置成功返回true，否则返回false
     */
    public boolean tryLock(String key, Object value, long timeout) {
        //底层原理就是Redis的setnx方法
        boolean isSuccess = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (isSuccess) {
            //设置分布式锁的过期时间
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
        return isSuccess;
    }


    public void deletekey(Object key)
    {
        redisTemplate.delete(key);
    }

    public String getkey(Object key)
    {
        return redisTemplate.opsForValue().get(key).toString();
    }
}
