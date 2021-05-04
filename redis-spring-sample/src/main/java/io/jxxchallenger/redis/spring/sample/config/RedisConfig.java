package io.jxxchallenger.redis.spring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.jxxchallenger.redis.spring.sample.data.PersonRepository;
import io.jxxchallenger.redis.spring.sample.service.HashMapping;
import io.lettuce.core.ReadFrom;

@Configuration
@ComponentScan(basePackageClasses = {HashMapping.class})
@EnableRedisRepositories(basePackageClasses = {PersonRepository.class})
public class RedisConfig {

    //======================================== Standalone ========================================//
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost", 6379);
        configuration.setPassword(RedisPassword.of("123456789"));
        
        //Write to Master, Read from Replica
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().readFrom(ReadFrom.REPLICA_PREFERRED).build();
        
        
        return new LettuceConnectionFactory(configuration, clientConfiguration);
    }
    
    //======================================== sentinel ========================================//
//    @Bean
//    public RedisConnectionFactory sentinelConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration().master("mymaster").sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 26380).sentinel("127.0.0.1", 26381);
//        sentinelConfiguration.setPassword("123456789");
//        
//        //Write to Master, Read from Replica
//        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().readFrom(ReadFrom.REPLICA_PREFERRED).build();
//        
//        return new LettuceConnectionFactory(sentinelConfiguration, clientConfiguration);
//    }
    
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
        //redisTemplate.setEnableTransactionSupport(true);
        
        return redisTemplate;
    }
    
    //======================================== Redis Cache ========================================//
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }
}
