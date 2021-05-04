package io.jxxchallenger.redis.spring.sample;

import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.jxxchallenger.redis.spring.sample.config.RedisConfig;

@SpringJUnitConfig(value = {RedisConfig.class})
@TestMethodOrder(value = OrderAnnotation.class)
public class RedisTest {

    private ListOperations<String, String> listOperations;
    
    @Autowired
    public void setListOPerations(RedisTemplate<String, String> redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }
    
    @Test
    @Order(0)
    public void lpushTest() {
        this.listOperations.leftPushAll("list-key", "spring", "redis");
    }
    
    @Test
    @Order(1)
    public void lrangeTest() {
        
        List<String> result = this.listOperations.range("list-key", 0, -1);
        result.forEach(item -> {
            System.out.println(item);
        });
    }
    
    @Test
    @Order(2)
    public void del() {
        this.listOperations.getOperations().delete("list-key");
    }
}
