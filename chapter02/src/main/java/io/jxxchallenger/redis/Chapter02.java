package io.jxxchallenger.redis;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Chapter02 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter02.class);
    
    public String checkToken(StatefulRedisConnection<String, String> connection, String token) {
        RedisCommands<String, String> syncCommands = connection.sync();
        return syncCommands.hget("login:", token);
    }
    
    public void updateToken(StatefulRedisConnection<String, String> connection, String token, String user, String item) {
        LOGGER.info("更新登录信息, {}, {}, {}", token, user, item);
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.hset("login:", token, user);
        long score = System.currentTimeMillis() / 1000;
        syncCommands.zadd("recent:", score, token);
        if(StringUtils.isNotBlank(item)) {
            syncCommands.zadd("viewed:" + token, score, item);
            syncCommands.zremrangebyrank("viewed:" + token, 0, -26);
            syncCommands.zincrby("viewed:", -1, item);
        }
    }
    
    public static class CleanSessionsThread implements Runnable {
        
        private static final Logger LOGGER = LoggerFactory.getLogger(CleanSessionsThread.class);
        
        private StatefulRedisConnection<String, String> connection;
        
        private int limit;
        
        private boolean quit;
        
        public CleanSessionsThread(StatefulRedisConnection<String, String> connection, int limit) {
            this.connection = connection;
            
            this.limit = limit;
        }

        public void quit() {
            this.quit = true;
        }

        @Override
        public void run() {
            RedisCommands<String, String> syncCommands = connection.sync();
            while(!quit) {
                
                long size = syncCommands.zcard("recent:");
                if(size <= this.limit) {
                    LOGGER.info("未达到上限");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
                LOGGER.info("达到上限");
                long endIndex = Math.min(size - limit, 5);
                List<String> tokenSet = syncCommands.zrange("recent:", 0, endIndex - 1);
                
                List<String> viewedKeys = tokenSet.stream().map(item -> {
                    return "viewed:" + item;
                }).collect(Collectors.toList());
                
                
                syncCommands.del(viewedKeys.toArray(new String[viewedKeys.size()]));
                syncCommands.zrem("recent:", tokenSet.toArray(new String[tokenSet.size()]));
                syncCommands.hdel("login:", tokenSet.toArray(new String[tokenSet.size()]));
            }
            
        }
        
    }
}
