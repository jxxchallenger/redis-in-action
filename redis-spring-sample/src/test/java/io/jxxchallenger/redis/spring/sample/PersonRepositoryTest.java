package io.jxxchallenger.redis.spring.sample;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jxxchallenger.redis.spring.sample.config.RedisConfig;
import io.jxxchallenger.redis.spring.sample.data.PersonRepository;
import io.jxxchallenger.redis.spring.sample.modal.Address;
import io.jxxchallenger.redis.spring.sample.modal.Person;

@SpringJUnitConfig(classes = {RedisConfig.class})
@TestMethodOrder(value = OrderAnnotation.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    
    @Test
    @Order(0)
    public void addPerson() {
        //this.personRepository.save(new Person(UUID.randomUUID().toString(), "spring", "redis", new Address("深圳", "中国"), LocalDate.now()));
        this.personRepository.save(new Person("spring", "redis", new Address("深圳", "中国"), LocalDate.now()));
    }
    
    @Test
    @Order(1)
    public void count() {
        System.out.println(this.personRepository.count());
    }
    
    //13.6. Query by Example
    @Test
    @Order(2)
    public void queryByExampleTest() {
        Person person = new Person();
        person.setFirstName("spring");
        
        Iterable<Person> persons = this.personRepository.findAll(Example.of(person));
        
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
