package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.model.Phone;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.exception.PhoneNotFoundException;
import com.example.demo.service.exception.UniqueCpfException;
import com.example.demo.service.exception.UniquePhoneException;
import com.example.demo.service.impl.PersonServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.example.demo.builders.PersonBuilder.aPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author KarlsCode.
 */

@RunWith(SpringRunner.class)
public class PersonServiceTest {
    private static final String NAME = aPerson().getName();
    private static final String CPF = aPerson().getCpf();
    private static final String DDD = aPerson().getDdd();
    private static final String NUMBER_PHONE = aPerson().getNumberPhone();

    @MockBean
    private PersonRepository personRepository;

    private PersonService sut;

    private Person person;
    private Phone phone;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        sut = new PersonServiceImpl(personRepository);
        person = aPerson().withPhone().build();
        phone = aPerson().getPhone();
    }

    @Test
    public void save_person_in_repository() throws UniqueCpfException, UniquePhoneException {
        sut.save(person);

        verify(personRepository).save(person);
    }

    @Test
    public void should_not_save_two_people_with_the_same_cpf() throws UniqueCpfException, UniquePhoneException {
        when(personRepository.findByCpf(CPF)).thenReturn(Optional.of(person));

        expectedException.expect(UniqueCpfException.class);
        expectedException.expectMessage("CPF informed is already registered: " + CPF + ".");

        sut.save(person);
    }

    @Test()
    public void should_not_save_two_people_with_the_same_phone() throws UniqueCpfException, UniquePhoneException {
        when(personRepository.findByPhoneDddAndPhoneNumber(DDD, NUMBER_PHONE)).thenReturn(Optional.of(person));

        expectedException.expect(UniquePhoneException.class);
        expectedException.expectMessage("Phone informed is already registered: (" + DDD + ") " + NUMBER_PHONE + ".");

        sut.save(person);
    }

    @Test
    public void find_a_person_by_ddd_and_phone() {
        when(personRepository.findByPhoneDddAndPhoneNumber(DDD, NUMBER_PHONE)).thenReturn(Optional.of(person));

        Person personFind = sut.findByPhone(phone);

        verify(personRepository).findByPhoneDddAndPhoneNumber(DDD, NUMBER_PHONE);

        assertThat(personFind).isNotNull();
        assertThat(personFind.getName()).isEqualTo(NAME);
        assertThat(personFind.getCpf()).isEqualTo(CPF);
    }

    @Test(expected = PhoneNotFoundException.class)
    public void should_return_phone_and_ddd_exception_not_found() {
        sut.findByPhone(phone);
    }

    @Test
    public void should_return_phone_data_in_PhoneNotFoundException() {
        expectedException.expect(PhoneNotFoundException.class);
        expectedException.expectMessage("Phone not found: ("+ DDD +") "+ NUMBER_PHONE);
        sut.findByPhone(phone);

    }
}
