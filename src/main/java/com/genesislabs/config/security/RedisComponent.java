package com.genesislabs.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getData(String _key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(_key);
    }

    public void setData(String _key, String _value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(_key, _value);
    }

    public void setDataExpire(String _key, String _value, long _duration){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(_duration);
        valueOperations.set(_key, _value, expireDuration);
    }

    public void deleteData(String _key){
        stringRedisTemplate.delete(_key);
    }

}