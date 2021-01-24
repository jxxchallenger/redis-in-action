package io.jxxchallenger.redis;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.Range;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisZSetTest extends AbstractRedisTest {

    private String zsetKey = "zset-key";
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "zadd命令")
    public void zadd(){
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.zadd(zsetKey, 1.0D, "hello", 2.0D, "redis", 3.0D, "java", 4.0D, "python", 5.0D, "c", 6.0D, "c++", 7.0D, "go", 8.0D, "jdk", 9.0D, "gravvl");
        Assertions.assertEquals(9, count);
    }
    
    @Test
    @Order(value = 1)
    @DisplayName(value = "zrem命令")
    public void zrem() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.zrem(zsetKey, "jdk", "gravvl", "world");
        Assertions.assertEquals(2, count);
    }
    
    @Test
    @Order(value = 2)
    @DisplayName(value = "zcard命令")
    public void zcard() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.zcard(zsetKey);
        Assertions.assertEquals(7, count);
    }
    
    @Test
    @Order(value = 3)
    @DisplayName(value = "zincrby命令")
    public void zincrby() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Double score = redisCommands.zincrby(zsetKey, 99, "java");
        Assertions.assertEquals(102.0, score);
        
    }
    
    @Test
    @Order(value = 4)
    @DisplayName(value = "zcount命令")
    public void zcount() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.zcount(zsetKey, Range.create(0, 200));
        
        Assertions.assertEquals(7, count);
    }
    
    @Test
    @Order(value = 5)
    @DisplayName(value = "zrank命令")
    public void zrank() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long rank = redisCommands.zrank(zsetKey, "java");
        Assertions.assertEquals(6, rank);
    }
    
    @Test
    @Order(value = 6)
    @DisplayName(value = "zscore命令")
    public void zscore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Double score = redisCommands.zscore(zsetKey, "java");
        Assertions.assertEquals(102.0, score);
    }
    
    @Test
    @Order(value = 7)
    @DisplayName(value = "zrange命令")
    public void zrange() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        List<String> members = redisCommands.zrange(zsetKey, 0, -1);
        members.forEach(item -> {
            System.out.println(item);
        });
    }
    
    @Test
    @Order(value = 8)
    @DisplayName(value = "zrevrank命令")
    public void zrevrank() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long rank = redisCommands.zrevrank(zsetKey, "java");
        Assertions.assertEquals(0, rank);
    }
    
    @Test
    @Order(value = 9)
    @DisplayName(value = "zrevrange命令")
    public void zrevrange() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.zrevrange(zsetKey, 0, -1);
        
    }
    
    @Test
    @Order(value = 10)
    @DisplayName(value = "zrangebyscore命令")
    public void zrangebyscore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
    }
    
    @Test
    @Order(value = 11)
    @DisplayName(value = "zrevrangebyscore命令")
    public void zrevrangebyscore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.zrevrangebyscore(zsetKey, Range.create(0, 200));
    }
    
    @Test
    @Order(value = 12)
    @DisplayName(value = "zremrangebyrank命令")
    public void zremrangebyrank() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.zremrangebyrank(zsetKey, 5, -1);
    }
    
    @Test
    @Order(value = 13)
    @DisplayName(value = "zremrangebyscore命令")
    public void zremrangebyscore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.zremrangebyscore(zsetKey, Range.create(100, 200));
    }
    
    @Test
    @Order(value = 14)
    @DisplayName(value = "zinterstore命令")
    public void zinterstore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
    }
    
    @Test
    @Order(value = 15)
    @DisplayName(value = "zunionstore命令")
    public void zunionstore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
    }
    
    @Test
    @Order(value = Integer.MAX_VALUE)
    //@DisplayName(value = "")
    public void clearData() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(zsetKey);
    }
}
