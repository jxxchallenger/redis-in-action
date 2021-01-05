package io.jxxchallenger.redis;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Demo extends AbstractClientTest {

    @Test
    @Order(0)
    public void demo() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("key", "Hello, Redis!");
        connection.close();
    }
}
