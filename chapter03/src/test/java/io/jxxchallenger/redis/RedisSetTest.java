package io.jxxchallenger.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisSetTest extends AbstractRedisTest {
    
    private String setKey = "set-key";
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "sadd命令")
    public void sadd() {
        String[] items = {"Spring Framework", "Spring Data", "Spring Boot", "Spring Cloud", "Mybatis", "Hibernate", "Redis", "MySQL", "ElasticSearch", "Hbase", "Maven"};
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.sadd(setKey, items);
        Assertions.assertEquals(items.length, count);
    }
    
    
    @Test
    @Order(value = 1)
    @DisplayName(value = "srem命令")
    public void srem() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.srem(setKey, "hello", "world");
        Assertions.assertEquals(0, count);
        count = redisCommands.srem(setKey, "Maven", "Hbase");
        Assertions.assertEquals(2L, count);
    }
    
    @Test
    @Order(value = 2)
    @DisplayName(value = "sismember命令")
    public void sismember() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Boolean exists = redisCommands.sismember(setKey, "hello world");
        Assertions.assertFalse(exists.booleanValue());
        exists = redisCommands.sismember(setKey, "Spring Framework");
        Assertions.assertTrue(exists.booleanValue());
        
    }
    
    @Test
    @Order(value = 3)
    @DisplayName(value = "scard命令")
    public void scard() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.scard(setKey);
        Assertions.assertEquals(9L, count);
    }
    
    @Test
    @Order(value = 4)
    @DisplayName(value = "smembers命令")
    public void smembers() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Set<String> members = redisCommands.smembers(setKey);
        members.stream().forEach(item -> {
            System.out.println(item);
        });
    }
    
    @Test
    @Order(value = 5)
    @DisplayName(value = "srandmember")
    public void srandmember() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        List<String> members = redisCommands.srandmember(setKey, 2);
        
    }
    
    @Test
    @Order(value = 6)
    @DisplayName(value = "spop命令")
    public void spop() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String member = redisCommands.spop(setKey);
        System.out.println(member);
    }
    
    @Test
    @Order(value = 7)
    @DisplayName(value = "smove命令")
    public void smove() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Boolean flag = redisCommands.smove(setKey, "set-key2", "MySQL");
        Assertions.assertTrue(flag.booleanValue());
    }
    
    @Test
    @Order(value = 8)
    @DisplayName(value = "sdiff命令")
    public void sdiff() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.sadd("set-key3", "java", "c", "c++", "go", "python", "javascript", "MySQL", "redis");
        redisCommands.sadd("set-key4", "Spring Framework", "Spring boot", "Spring cloud", "java", "MySQL", "redis");
        Set<String> diffs = redisCommands.sdiff("set-key3", "set-key4");
        
        Assertions.assertEquals(5, diffs.size());
    }
    
    @Test
    @Order(value = 9)
    @DisplayName(value = "sdiffstore命令")
    public void sdiffstore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.sdiffstore("set-key5", "set-key3", "set-key4");
        Assertions.assertEquals(5, count);
    }
    
    @Test
    @Order(value = 10)
    @DisplayName(value = "sinter命令")
    public void sinter() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Set<String> inters = redisCommands.sinter("set-key3", "set-key4");
        Assertions.assertEquals(3, inters.size());
    }
    
    @Test
    @Order(value = 11)
    @DisplayName(value = "sinterstore命令")
    public void sinterstore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.sinterstore("set-key6", "set-key3", "set-key4");
        Assertions.assertEquals(3, count);
        
    }
    
    @Test
    @Order(value = 12)
    @DisplayName(value = "sunion命令")
    public void sunion() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Set<String> unions = redisCommands.sunion("set-key3", "set-key4");
        Assertions.assertEquals(11, unions.size());
    }
    
    @Test
    @Order(value = 13)
    @DisplayName(value = "sunionstore命令")
    public void sunionstore() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        Long count = redisCommands.sunionstore("set-key7", "set-key3", "set-key4");
        Assertions.assertEquals(11, count);
    }
    
    @Test
    @Order(value = Integer.MAX_VALUE)
    public void cleanData() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(setKey);
        redisCommands.del("set-key2", "set-key3", "set-key4", "set-key5", "set-key6", "set-key7");
    }

}
