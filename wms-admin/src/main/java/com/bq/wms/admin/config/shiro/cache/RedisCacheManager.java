package com.bq.wms.admin.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Shiro缓存管理器实现之Redis
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(s, redisTemplate);
    }
}
