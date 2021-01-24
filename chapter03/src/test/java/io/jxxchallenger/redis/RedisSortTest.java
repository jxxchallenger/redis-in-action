package io.jxxchallenger.redis;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.SortArgs;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisSortTest extends AbstractRedisTest {

    private String sortKey = "sort-input";
    
    private String hashKey = "";
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "sort命令")
    public void sort() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.rpush(sortKey, "23", "15", "110", "7");
        List<String> sorts = redisCommands.sort(sortKey);
        redisCommands.hset("d-7", "field", "5");
        redisCommands.hset("d-15", "field", "1");
        redisCommands.hset("d-23", "field", "9");
        redisCommands.hset("d-110", "field", "3");
        System.out.println("=============================== 默认排序 ===============================");
        sorts.stream().forEach(item -> {
            System.out.println(item);
        });
        
        System.out.println("=============================== alpha排序 ===============================");
        sorts = redisCommands.sort(sortKey, new SortArgs().alpha());
        sorts.stream().forEach(item -> {
            System.out.println(item);
        });
        
        System.out.println("=============================== desc排序 ===============================");
        sorts = redisCommands.sort(sortKey, SortArgs.Builder.desc());
        sorts.stream().forEach(item -> {
            System.out.println(item);
        });
        
        System.out.println("=============================== pattern排序 ===============================");
        sorts = redisCommands.sort(sortKey, SortArgs.Builder.by("d-*->field"));
        sorts.stream().forEach(item -> {
            System.out.println(item);
        });
        
        sorts = redisCommands.sort(sortKey, new SortArgs().by("d-*->field").get("d-*->field"));
        System.out.println("=============================== pattern get排序 ===============================");
        sorts.stream().forEach(item -> {
            System.out.println(item);
        });
    }
    
    @Test
    @Order(value = Integer.MAX_VALUE)
    //@DisplayName(value = "")
    public void cleanData() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(sortKey, "d-7", "d-15", "d-23", "d-110");
    }
}
