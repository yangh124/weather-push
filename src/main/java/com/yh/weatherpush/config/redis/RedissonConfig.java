package com.yh.weatherpush.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yh
 * @date : 2022/3/31 21:23
 */
@EnableCaching
@Configuration
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
        config.useSingleServer().setAddress(address).setPingConnectionInterval(60000).setPassword(redisProperties.getPassword());
        FastJson2Codec fastJson2Codec = new FastJson2Codec();
        config.setCodec(fastJson2Codec);
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient, "classpath:/cache-config.yaml");
    }
}
