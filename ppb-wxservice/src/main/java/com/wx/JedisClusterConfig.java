package com.wx;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
public class JedisClusterConfig {

	@Inject
    private RedisProperties redisProperties;

    /**
     * 注意：
     * 这里返回的ShardedJedisPool是单例的，并且可以直接注入到其他类中去使用
     * @return
     */
    @Bean
    public ShardedJedisPool getJedisCluster() {
        List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
		String[] addressArr = redisProperties.getClusterNodes().split(",");
		for (String str : addressArr) {
			String[] split = str.split(":");
			String ip = split[0];
			String port = split[1];
			list.add(new JedisShardInfo(ip, Integer.parseInt(port)));
		}
		JedisPoolConfig jcg = new JedisPoolConfig();
		ShardedJedisPool pool = new ShardedJedisPool(jcg, list);
        return pool;
    }

}