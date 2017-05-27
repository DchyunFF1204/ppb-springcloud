package com.ppb.client.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daizy
 * 
 * redis 缓存配置
 *
 */
@Configuration
@EnableCaching // 启用缓存
public class JedisClusterConfig  extends CachingConfigurerSupport{

    @Autowired
    private RedisProperties redisProperties;
	

    /**
     * 这里返回的ShardedJedisPool是单例的，并且可以直接注入到其他类中去使用
     * @return
     */
    @Bean
    public ShardedJedisPool shardedJedisPoolFactory() {
        List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = redisProperties.getClusterNodes().split(",");
		for (String str : addressArr) {
			String[] split = str.split(":");
			String ip = split[0];
			String port = split[1];
			list.add(new JedisShardInfo(ip, Integer.parseInt(port)));
		}
		ShardedJedisPool pool = new ShardedJedisPool(redisProperties.getJedisPoolConfig(), list);
        return pool;
    }
    
    /**
     * redis key的生成策略
     * 
     * @return
     */
    @Bean  
    public KeyGenerator redisKeyGenerator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                System.out.println("redis key:"+sb.toString());
                return sb.toString();  
            }  
        };  
    }  
}