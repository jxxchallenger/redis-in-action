package io.jxxchallenger.redis.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(value = OrderAnnotation.class)
public abstract class AbstractRedisTest {

    protected static RedisClient redisClient;
    
    protected StatefulRedisConnection<String, String> connection;
    
    @BeforeAll
    public static void setup() {
        redisClient = RedisClient.create("redis://123456789@localhost:6379/0");
    }
    
    @BeforeEach
    public void initConnection() {
        this.connection = redisClient.connect();
        doInit();
    }
    
    protected void doInit() {
        
    }
    
    @AfterEach
    public void closeConnection() {
        this.connection.close();
        doClose();
    }
    
    protected void doClose() {
        
    }
    
    @AfterAll
    public static void destory() {
        redisClient.shutdown();
    }
}
