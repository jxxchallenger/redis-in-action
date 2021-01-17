package io.jxxchallenger.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.KeyValue;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisHashTest extends AbstractRedisTest {

    private String hashKey = "hash-key";
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "hset命令")
    public void hset() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.hset(hashKey, "hello", "redis");
    }
    
    @Test
    @Order(value = 1)
    @DisplayName(value = "hget命令")
    public void hget() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String value = redisCommands.hget(hashKey, "hello");
        Assertions.assertEquals("redis", value);
    }
    
    @Test
    @Order(value = 2)
    @DisplayName(value = "hmset命令")
    public void hmset() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Map<String, String> kv = new HashMap<String, String>();
        kv.put("key1", "val1");
        kv.putIfAbsent("key2", "val2");
        kv.put("key3", "val3");
        kv.put("key4", "val4");
        kv.put("key5", "val5");
        redisCommands.hmset(hashKey, kv);
    }
    
    @Test
    @Order(value = 3)
    @DisplayName(value = "hmget命令")
    public void hmget() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        List<KeyValue<String, String>> vals = redisCommands.hmget(hashKey, "key1", "key2", "key3");
        Assertions.assertEquals(3, vals.size());
        vals.forEach(item -> {
            System.out.println(item.getKey() + ":" + item.getValue());
        });
    }
    
    @Test
    @Order(value = 4)
    @DisplayName(value = "hdel命令")
    public void hdel() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.hdel(hashKey, "key4", "key5", "key6");
        Assertions.assertEquals(2, count);
    }
    
    @Test
    @Order(value = 5)
    @DisplayName(value = "hlen命令")
    public void hlen() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.hlen(hashKey);
        Assertions.assertEquals(4, count);
    }
    
    @Test
    @Order(value = 6)
    @DisplayName(value = "hexists命令")
    public void hexists() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Boolean exists = redisCommands.hexists(hashKey, "abc");
        Assertions.assertFalse(exists.booleanValue());
        exists = redisCommands.hexists(hashKey, "hello");
        Assertions.assertTrue(exists.booleanValue());
    }
    
    @Test
    @Order(value = 7)
    @DisplayName(value = "hkeys命令")
    public void hkeys() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        List<String> keys = redisCommands.hkeys(hashKey);
        Assertions.assertEquals(4, keys.size());
        keys.forEach(key -> {
            System.out.println(key);
        });
    }
    
    @Test
    @Order(value = 8)
    @DisplayName(value = "hvals命令")
    public void hvals() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        List<String> vals = redisCommands.hvals(hashKey);
        vals.forEach(val -> {
            System.out.println(val);
        });
    }
    
    @Test
    @Order(value = 9)
    @DisplayName(value = "hgetall命令")
    public void hgetall() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Map<String, String> kvs = redisCommands.hgetall(hashKey);
        kvs.entrySet().forEach(kv -> {
            System.out.println(kv.getKey() + ":" + kv.getValue());
        });
    }
    
    @Test
    @Order(value = 10)
    @DisplayName(value = "hincrby命令")
    public void hincrby() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long val = redisCommands.hincrby(hashKey, "key100", 100);
        Assertions.assertEquals(100, val.longValue());
    }
    
    @Test
    @Order(value = 11)
    @DisplayName(value = "hincrbyfloat命令")
    public void hincrbyfloat() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Double val = redisCommands.hincrbyfloat(hashKey, "key1.01", 1.01d);
        Assertions.assertEquals(1.01d, val);
    }
    
    @Test
    @Order(value = Integer.MAX_VALUE)
    //@DisplayName(value = "")
    public void cleanData() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(hashKey);
    }
}
