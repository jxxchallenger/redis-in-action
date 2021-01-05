package io.jxxchallenger.redis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import io.lettuce.core.RedisClient;

@TestMethodOrder(value = OrderAnnotation.class)
public abstract class AbstractClientTest {

    protected static RedisClient redisClient;
    
    @BeforeAll
    public static void setup() {
        redisClient = RedisClient.create("redis://123456789@localhost:6379/0");
    }
    
    @AfterAll
    public static void destory() {
        redisClient.shutdown();
    }
}
