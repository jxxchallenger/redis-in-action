package io.jxxchallenger.redis;

import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import io.jxxchallenger.redis.test.AbstractRedisTest;

public class LonginCookieTest extends AbstractRedisTest {

    @Test
    public void loginCookie() {
        
        
        Chapter02.CleanSessionsThread target = new Chapter02.CleanSessionsThread(this.connection, 15);
        Thread thread = new Thread(target);
        thread.start();
        
        
        Chapter02 chapter02 = new Chapter02();
        IntStream.rangeClosed(1, 100).forEach(num -> {
            chapter02.updateToken(this.connection, UUID.randomUUID().toString(), "User" + num, "itemX");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        try {
            Thread.sleep(5000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        target.quit();
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
