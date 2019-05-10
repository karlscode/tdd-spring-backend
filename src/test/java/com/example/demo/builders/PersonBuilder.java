package com.example.demo.builders;

import com.example.demo.model.Person;
import com.example.demo.model.Phone;

import java.util.Arrays;

/**
 * @author KarlsCode.
 */

public class PersonBuilder {

    private Person person;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        PersonBuilder builder = new PersonBuilder();
        builder.person = new Person();
        builder.person.setName("Bruno Meirelles");
        builder.person.setCpf("46465465335");

        return builder;
    }

    public String getCpf() {
        return build().getCpf();
    }

    public String getName() {
        return build().getName();
    }

    public String getDdd() {
        return withPhone().build().getPhones().get(0).getDdd();
    }

    public String getNumberPhone() {
        return withPhone().build().getPhones().get(0).getNumber();
    }

    public Phone getPhone() {
        return withPhone().build().getPhones().get(0);
    }

    public PersonBuilder addName(String name) {
        person.setName(name);
        return this;
    }

    public PersonBuilder withPhone() {
        person.setPhones(
                Arrays.asList(
                        new Phone("71", "98545245")));
        return this;
    }

    public PersonBuilder addCpf(String cpf) {
        person.setCpf(cpf);
        return this;
    }

    public PersonBuilder addPhone(String ddd, String numberPhone) {
        person.setPhones(
                Arrays.asList(
                        new Phone(ddd, numberPhone)));
        return this;
    }

    public Person build() {
        return person;
    }
}
