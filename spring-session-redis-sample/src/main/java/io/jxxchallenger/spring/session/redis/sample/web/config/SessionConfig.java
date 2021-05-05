package io.jxxchallenger.spring.session.redis.sample.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import io.lettuce.core.ReadFrom;

@EnableRedisHttpSession
public class SessionConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration("localhost", 6379);
        standaloneConfig.setPassword("123456789");
        
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().readFrom(ReadFrom.REPLICA_PREFERRED).build();
        
        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }
}
