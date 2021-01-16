/**
 * 
 */
package io.jxxchallenger.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author jxxchallenger
 *
 */
public class RedisStringTest2 extends AbstractRedisTest {

    private String appendKey = "append-key";
    
    private String rangeKey = "get-range";
    
    @Test
    @Order(value = 0)
    @DisplayName(value = "字符串追加")
    public void append() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.del(appendKey);
        Long length = redisCommands.append(appendKey, "Are you ok?");
        Assertions.assertEquals(11L, length);
        
    }
    
    @Test
    @Order(value = 1)
    @DisplayName(value = "获取/设置指定范围子字符串")
    public void range() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        redisCommands.set(rangeKey, "Are xxx xx?");
        redisCommands.setrange(rangeKey, 4, "you ok?");
        String value = redisCommands.getrange(rangeKey, 4, 10);
        Assertions.assertEquals("you ok?", value);
        
    }
    
    
}
