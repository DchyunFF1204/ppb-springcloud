package com.ppb.client.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by daizy on 2017/5/27.
 */
@Component
public class RedisHelper {

    @Autowired
    private  RedisClient redisClient;

    private static RedisClient redisStaticClient;

    @PostConstruct
    public void init() {
        redisStaticClient = redisClient;
    }

    /**
     * 获取redis实例
     * @return
     */
    public static RedisClient getRedisInstance(){
        return redisStaticClient;
    }
}
