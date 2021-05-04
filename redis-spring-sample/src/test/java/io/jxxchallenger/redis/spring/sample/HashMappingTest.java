package io.jxxchallenger.redis.spring.sample;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import io.jxxchallenger.redis.spring.sample.config.RedisConfig;
import io.jxxchallenger.redis.spring.sample.modal.Address;
import io.jxxchallenger.redis.spring.sample.modal.Person;
import io.jxxchallenger.redis.spring.sample.service.HashMapping;

@SpringJUnitConfig(classes = {RedisConfig.class})
@TestMethodOrder(value = OrderAnnotation.class)
public class HashMappingTest {

    @Autowired
    private HashMapping hashMapping;
    
    @Test
    @Order(0)
    public void hset() {
        this.hashMapping.writeHash("person-hash:1", new Person("spring", "redis", new Address("深圳", "中国"), LocalDate.now()));
    }
    
    @Test
    @Order(1)
    public void hget() {
        Person person = this.hashMapping.loadHash("person-hash:1");
        System.out.println(person.getFirstName() + " : " + person.getLastName());
    }
}
