package io.jxxchallenger.redis;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

public class Demo extends AbstractRedisTest {

    @Test
    @Order(0)
    public void demo() {
        
        RedisCommands<String, String> syncCommands = this.connection.sync();
        syncCommands.set("key", "Hello, Redis!");
        
    }
}
