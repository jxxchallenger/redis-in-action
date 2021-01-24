package io.jxxchallenger.redis;

import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisExppireTest extends AbstractRedisTest {

    @Test
    public void expire() {
        RedisCommands<String, String> commands = this.connection.sync();
        String key = "expire-key";
        commands.set(key, "hello Redis");
        commands.expire(key, 20);
        //commands.expireat(key, new Date(2021, 2, 1, 0, 0));
        //commands.pexpire(key, 20000);
        //commands.pexpireat(key, 1233456789116544541L);
        //commands.persist(key);
        Long time = commands.ttl(key);
        //time = commands.pttl(key);
        System.out.println(time.longValue());
    }
}
