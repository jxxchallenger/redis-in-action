package io.jxxchallenger.redis;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisTransationTest extends AbstractRedisTest {

    @Test
    @Order(value = 0)
    public void transation() {
        RedisCommands<String, String> redisCommands = this.connection.sync();
        String val = redisCommands.get("test");
        System.out.println(val);
        
        RedisCommands<String, String> redisCommands2 = RedisClient.create("redis://123456789@localhost:6379/0").connect().sync();
        SetThread setThread = new SetThread(redisCommands);
        GetThread getThread = new GetThread(redisCommands2);
        Thread thread = new Thread(setThread, "set-thread");
        
        Thread thread2 = new Thread(getThread, "get-thread");
        
        thread.start();
        thread2.start();
        
        try {
            Thread.sleep(10 * 2000);
            setThread.setRun(false);
            getThread.setRun(false);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static class SetThread implements Runnable {
        
        private boolean run = true;
        
        private RedisCommands<String, String> redisCommands;
        
        public SetThread(RedisCommands<String, String> redisCommands) {
            this.redisCommands = redisCommands;
        }
        
        public void setRun(boolean run) {
            this.run = run;
        }

        @Override
        public void run() {
            while(run) {
                redisCommands.multi();
                redisCommands.incr("test");
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisCommands.decr("test");
                redisCommands.exec();
            }
        }
    }
    
    private static class GetThread implements Runnable {
        
        private boolean run = true;
        
        private RedisCommands<String, String> redisCommands;
        
        public GetThread(RedisCommands<String, String> redisCommands) {
            this.redisCommands = redisCommands;
        }
        
        public void setRun(boolean run) {
            this.run = run;
        }

        @Override
        public void run() {
            while(run) {
                System.out.println(redisCommands.get("test"));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
