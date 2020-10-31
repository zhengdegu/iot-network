package com.gu.redis.service.impl;

import com.gu.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作实现类
 * @author g130016
 */
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public RedisConnectionFactory getConnectionFactory() {
        return this.redisTemplate.getConnectionFactory();
    }


    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }


    @Override
    public void flushDB(RedisClusterNode node) {
        this.redisTemplate.opsForCluster().flushDb(node);
    }


    @Override
    public void setExpire(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.setEx(key, time, value);
            return 1L;
        });
    }


    @Override
    public void setExpire(final String key, final Object value, final long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }


    @Override
    public void setExpire(final String[] keys, final Object[] values, final long time) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i], time, TimeUnit.SECONDS);
        }
    }



    @Override
    public void set(final String[] keys, final Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i]);
        }
    }



    @Override
    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }


    @Override
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.keys(keyPatten + "*");
    }


    @Override
    public byte[] get(final byte[] key) {
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key));
    }


    @Override
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public HashOperations<String, String, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }


    @Override
    public void putHashValue(String key, String hashKey, Object hashValue) {
        opsForHash().put(key, hashKey, hashValue);
    }


    @Override
    public Object getHashValues(String key, String hashKey) {
        return opsForHash().get(key, hashKey);
    }


    @Override
    public void delHashValues(String key, Object... hashKeys) {
        opsForHash().delete(key, hashKeys);
    }


    @Override
    public Map<String, Object> getHashValue(String key) {
        return opsForHash().entries(key);
    }


    @Override
    public void putHashValues(String key, Map<String, Object> map) {
        opsForHash().putAll(key, map);
    }


    @Override
    public long dbSize() {
        return redisTemplate.execute(RedisServerCommands::dbSize);
    }


    @Override
    public String flushDB() {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }



    @Override
    public boolean del(final List<String> keys) {
        boolean result = false;
        for (String key : keys) {
            result = redisTemplate.delete(key);
        }
        return result;
    }


    @Override
    public long incr(final String key) {
        return redisTemplate.opsForValue().increment(key);
    }


    @Override
    public ListOperations<String, Object> opsForList() {
        return redisTemplate.opsForList();
    }


    @Override
    public Long leftPush(String key, Object value) {
        return opsForList().leftPush(key, value);
    }


    @Override
    public Object leftPop(String key) {
        return opsForList().leftPop(key);
    }


    @Override
    public Long in(String key, Object value) {
        return opsForList().rightPush(key, value);
    }


    @Override
    public Object rightPop(String key) {
        return opsForList().rightPop(key);
    }



    @Override
    public Long length(String key) {
        return opsForList().size(key);
    }



    @Override
    public void remove(String key, long i, Object value) {
        opsForList().remove(key, i, value);
    }


    @Override
    public void set(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }


    @Override
    public List<Object> getList(String key, int start, int end) {
        return opsForList().range(key, start, end);
    }


    @Override
    public Long leftPushAll(String key, List<String> list) {
        return opsForList().leftPushAll(key, list);
    }


    @Override
    public void insert(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

}
