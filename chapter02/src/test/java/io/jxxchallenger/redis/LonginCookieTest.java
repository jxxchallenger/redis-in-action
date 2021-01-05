package io.jxxchallenger.redis;

import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import io.lettuce.core.api.StatefulRedisConnection;

public class LonginCookieTest extends AbstractClientTest {

    @Test
    public void loginCookie() {
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        
        Chapter02.CleanSessionsThread target = new Chapter02.CleanSessionsThread(connection, 15);
        Thread thread = new Thread(target);
        thread.start();
        
        
        Chapter02 chapter02 = new Chapter02();
        IntStream.rangeClosed(1, 100).forEach(num -> {
            chapter02.updateToken(connection, UUID.randomUUID().toString(), "User" + num, "itemX");
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
        connection.close();
    }
}
