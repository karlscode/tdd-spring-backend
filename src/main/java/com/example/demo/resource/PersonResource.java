package com.example.demo.resource;

import com.example.demo.event.ResourceCreateEvent;
import com.example.demo.model.Person;
import com.example.demo.model.Phone;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.filter.FilterPerson;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author KarlsCode.
 */

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        List<Person> allPeople = personService.getAll();
        return ResponseEntity.ok().body(allPeople);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        Person personById = personService.findById(id);
        return ResponseEntity.ok().body(personById);
    }

    @GetMapping("/{ddd}/{number}")
    public ResponseEntity<Person> findByDddAndPhone(@PathVariable String ddd,
                                                    @PathVariable String number) {
        final Person personByPhone = personService.findByPhone(new Phone(ddd, number));
        return ResponseEntity.ok().body(personByPhone);
    }

    @PostMapping
    public ResponseEntity<Person> savePeople(@RequestBody Person person, HttpServletResponse response) {
        final Person savePerson = personService.save(person);
        publisher.publishEvent(new ResourceCreateEvent(this, response, savePerson.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savePerson);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<Person>> filter(@RequestBody FilterPerson filter) {
        final List<Person> filterPeople = personRepository.filter(filter);
        return ResponseEntity.ok().body(filterPeople);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
        final Person personUpdate = personService.update(id, person);
        return ResponseEntity.accepted().body(personUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
