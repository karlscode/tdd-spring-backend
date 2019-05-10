package com.example.demo.repository.helper;

import com.example.demo.model.Person;
import com.example.demo.repository.filter.FilterPerson;

import java.util.List;

/**
 * @author KarlsCode.
 */
public interface PersonRepositoryQueries {

    List<Person> filter(FilterPerson filterPerson);
}
