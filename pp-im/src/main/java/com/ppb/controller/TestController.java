package com.ppb.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ppb.client.redis.RedisHelper;
import com.ppb.model.user.ClientInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by daizy on 2017/5/27.
 */
@RestController
public class TestController {

    Gson gson = new GsonBuilder().serializeNulls().create();

    @RequestMapping("/testRedis")
    public String testRedis(String key){
        ClientInfo clientInfo = new ClientInfo();
        Date nowTime = new Date(System.currentTimeMillis());
        clientInfo.setClientid(key);
        clientInfo.setConnected((short) 1);
        clientInfo.setLastconnecteddate(nowTime);
        RedisHelper.getRedisInstance().set("",key, gson.toJson(clientInfo));
        return  RedisHelper.getRedisInstance().get("",key);

    }
}
