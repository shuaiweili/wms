package com.bq.wms.admin.config.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Shiro的缓存实现之Redis
 * @author: 李帅伟
 * @date: 2018/4/25
 **/
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {

    private final static String PREFIX = "shiro-cache:";
    private RedisTemplate<K, V> redisTemplate;
    private String cacheKey;
    private long globalExpire = 30; //过期时间

    public RedisCache(final String name, final RedisTemplate redisTemplate) {
        this.cacheKey = PREFIX + name + ":";
        this.redisTemplate = redisTemplate;
    }


    @Override
    public V get(K k) throws CacheException {
        log.debug("Shiro从Redis缓存中获取数据, Key={}", k);
        K key = getCacheKey(k);
        redisTemplate.boundValueOps(key).expire(globalExpire, TimeUnit.MINUTES);
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.boundValueOps(getCacheKey(k)).set(v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete(getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>(set.size());
        for (K k : set) {
            list.add(get(k));
        }
        return list;
    }

    private K getCacheKey(Object key) {
        return (K) (this.cacheKey + key);
    }
}
