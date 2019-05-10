package com.example.demo.model;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * @author KarlsCode.
 */

@EqualsAndHashCode

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String cpf;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Address> address;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Phone> phones;

    public Person() {
    }

    public Person(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
