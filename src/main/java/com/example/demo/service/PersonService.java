package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.model.Phone;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author KarlsCode.
 */

@Service
public interface PersonService {

    Person save(Person person);

    Person findByPhone(Phone phone);

    void deleteById(Long id);

    Person update(Long id, Person person);

    List<Person> getAll();

    Person findById(Long id);
}
