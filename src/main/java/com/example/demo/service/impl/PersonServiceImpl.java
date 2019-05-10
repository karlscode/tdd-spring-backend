package com.example.demo.service.impl;

import com.example.demo.model.Person;
import com.example.demo.model.Phone;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import com.example.demo.service.exception.PersonNotFoundException;
import com.example.demo.service.exception.PhoneNotFoundException;
import com.example.demo.service.exception.UniqueCpfException;
import com.example.demo.service.exception.UniquePhoneException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author KarlsCode.
 */

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        Optional<Person> optionalPerson = personRepository.findByCpf(person.getCpf());

        if ( optionalPerson.isPresent() ) {
            throw new UniqueCpfException("CPF informed is already registered: " + person.getCpf() + ".");
        }

        final String ddd = person.getPhones().get(0).getDdd();
        final String numberPhone = person.getPhones().get(0).getNumber();
        optionalPerson = personRepository.findByPhoneDddAndPhoneNumber(ddd, numberPhone);

        if ( optionalPerson.isPresent() ) {
            throw new UniquePhoneException("Phone informed is already registered: (" + ddd + ") " + numberPhone + ".");
        }

        return personRepository.save(person);
    }

    @Override
    public Person findByPhone(Phone phone) {
        Optional<Person> optional = personRepository.findByPhoneDddAndPhoneNumber(phone.getDdd(), phone.getNumber());
        return optional.orElseThrow( () -> new PhoneNotFoundException("Phone not found: (" + phone.getDdd() + ") " + phone.getNumber() + "."));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        personRepository.deleteById(id);
    }

    @Override
    public Person update(Long id, Person person) {
        Person savePerson = findById(id);

        BeanUtils.copyProperties(person, savePerson, "id");
        return personRepository.save(savePerson);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findById(Long id) {
        Optional<Person> personById = personRepository.findById(id);
        return personById.orElseThrow( () -> new PersonNotFoundException("Person not found."));
    }
}
