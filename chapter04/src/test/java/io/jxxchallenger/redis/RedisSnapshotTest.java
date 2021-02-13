package io.jxxchallenger.redis;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.api.sync.RedisCommands;

@DisplayName(value = "RDB快照")
public class RedisSnapshotTest extends AbstractRedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSnapshotTest.class);
    
    @Test
    @Order(0)
    @DisplayName(value = "bgsave命令")
    public void bgsave() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String flag = redisCommands.bgsave();
        
        LOGGER.info(flag);
    }
    
    @Test
    @Order(1)
    @DisplayName(value = "save命令")
   public void save() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String result = redisCommands.save();
        LOGGER.info(result);
        Date time = redisCommands.lastsave();
        LOGGER.info(DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss"));
    }
   
    @Test
    @Order(2)
    @DisplayName(value = "bgrewriteaof命令")
    public void bgrewriteaof() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String result = redisCommands.bgrewriteaof();
        LOGGER.info(result);
    }
}
