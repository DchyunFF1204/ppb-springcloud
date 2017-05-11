package com.ppb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RedisController {
	
	@Autowired
	private RedisClient redisClient;
	
	@Cacheable(value = "usercache",keyGenerator="redisKeyGenerator")
	@RequestMapping("/getInfo")
	public String getInfo(String key){
		System.out.println("不执行redis");
		return "sss";
	}
	
	
	
	
}
