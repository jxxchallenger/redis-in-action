package io.jxxchallenger.redis.spring.sample.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import io.jxxchallenger.redis.spring.sample.modal.Person;

@Component
public class HashMapping {

    private HashOperations<String, String, Object> hashOperations;
    
    //private ObjectHashMapper mapper = new ObjectHashMapper();
    //private BeanUtilsHashMapper<Person> mapper = new BeanUtilsHashMapper<Person>(Person.class);
    private Jackson2HashMapper mapper = new Jackson2HashMapper(true);
    
    @Autowired
    public HashMapping(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashValueSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.afterPropertiesSet();
        this.hashOperations = redisTemplate.opsForHash();
    }
    
    public void writeHash(String key, Person person) {

        Map<String, Object> mappedHash = mapper.toHash(person);
        hashOperations.putAll(key, mappedHash);
    }

    public Person loadHash(String key) {

        Map<String, Object> loadedHash = hashOperations.entries(key);
        return (Person) mapper.fromHash(loadedHash);
    }
}
