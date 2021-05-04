package io.jxxchallenger.redis.spring.sample.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import io.jxxchallenger.redis.spring.sample.modal.Person;

public interface PersonRepository extends CrudRepository<Person, String>, QueryByExampleExecutor<Person> {

}
