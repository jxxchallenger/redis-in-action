package io.jxxchallenger.redis.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(value = OrderAnnotation.class)
@TestInstance(value = Lifecycle.PER_CLASS)
public abstract class AbstractRedisTest {

    protected static RedisClient redisClient;
    
    protected StatefulRedisConnection<String, String> connection;
    
    /**
     * 测试类带有 @TestInstance(Lifecycle.PER_CLASS)注解，被@BeforeAll注解的方法无需声明为static
     */
    @BeforeAll
    public void setup() {
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
        doClose();
        this.connection.close();
        
    }
    
    protected void doClose() {
        
    }
    
    @AfterAll
    public static void destory() {
        redisClient.shutdown();
    }
}
