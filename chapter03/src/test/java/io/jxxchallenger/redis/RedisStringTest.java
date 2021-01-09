package io.jxxchallenger.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisStringTest extends AbstractRedisTest {

    private String key = "string-key";
    
    @Override
    protected void doInit() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(key);
    }
    
    @Override
    protected void doClose() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(key);
    }
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "数值自增")
    public void incr() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long value = redisCommands.incr(key);
        Assertions.assertEquals(1L, value);
        value = redisCommands.incrby(key, 9);
        Assertions.assertEquals(10L, value);
        
    }
    
    @Test
    @Order(value = 1)
    @DisplayName(value = "数值自减")
    public void decr() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long value = redisCommands.decr(key);
        Assertions.assertEquals(-1L, value);
        value = redisCommands.decrby(key, 9);
        Assertions.assertEquals(-10L, value);
    }
    
    @Test
    @Order(value = 2)
    @DisplayName(value = "浮点数自增")
    public void incrByFloat() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Double value = redisCommands.incrbyfloat(key, 1.012);
        Assertions.assertEquals(1.012, value);
        
    }
}
