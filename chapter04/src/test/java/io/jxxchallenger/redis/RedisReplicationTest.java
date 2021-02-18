package io.jxxchallenger.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisReplicationTest extends AbstractRedisTest {

    @Test
    @Order(0)
    @DisplayName(value = "info命令")
    public void info() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String result = redisCommands.info("Persistence");
        System.out.println(result);
    }
}
