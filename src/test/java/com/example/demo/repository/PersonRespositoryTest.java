package com.example.demo.repository;

import com.example.demo.model.Person;
import com.example.demo.repository.filter.FilterPerson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KarlsCode.
 */

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PersonRespositoryTest {
    
    @Autowired
    private PersonRepository sut;

    private FilterPerson filter;

    @Before
    public void setUp() throws Exception {
        filter = new FilterPerson();
    }

    @Test
    public void must_find_person_by_cpf() {
        Optional<Person> optional = sut.findByCpf("38767897100");

        Person person = optional.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void should_not_find_a_person_a_nonexistent_cpf() {
        Optional<Person> optional = sut.findByCpf("38765597199");
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void must_find_a_person_by_ddd_and_phone_number() {
        Optional<Person> personByPhoneDddAndPhoneNumber = sut.findByPhoneDddAndPhoneNumber("86", "35006330");
        assertThat(personByPhoneDddAndPhoneNumber.isPresent()).isTrue();

        Person person = personByPhoneDddAndPhoneNumber.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void should_not_find_a_person_whith_ddd_and_phone_nonexistent() {
        Optional<Person> personByPhoneDddAndPhoneNumber = sut.findByPhoneDddAndPhoneNumber("86", "35006300");
        assertThat(personByPhoneDddAndPhoneNumber.isPresent()).isFalse();
    }

    @Test
    public void deve_encontrar_person_pelo_ddd_e_numero_de_telefone() {
        Optional<Person> optional = sut.findByPhoneDddAndPhoneNumber("86", "35006330");

        assertThat(optional.isPresent()).isTrue();

        Person person = optional.get();
        assertThat(person.getId()).isEqualTo(3L);
        assertThat(person.getName()).isEqualTo("Cauê");
        assertThat(person.getCpf()).isEqualTo("38767897100");
    }

    @Test
    public void nao_deve_encontrar_person_cujo_ddd_e_telefone_nao_estejam_cadastrados() {
        Optional<Person> optional = sut.findByPhoneDddAndPhoneNumber("11", "324516731");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void deve_filtrar_person_por_parte_do_nome() {
        filter.setName("a");

        List<Person> person = sut.filter(filter);

        assertThat(person.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_person_por_parte_do_cpf() {
        filter.setCpf("78");

        List<Person> person = sut.filter(filter);

        assertThat(person.size()).isEqualTo(3);
    }

    @Test
    public void deve_filtrar_person_por_filter_composto() {
        filter.setName("a");
        filter.setCpf("78");

        List<Person> person = sut.filter(filter);

        assertThat(person.size()).isEqualTo(2);
    }

    @Test
    public void deve_filtrar_person_pelo_ddd_do_telefone() {
        filter.setDdd("21");

        List<Person> person = sut.filter(filter);

        assertThat(person.size()).isEqualTo(1);
    }

    @Test
    public void deve_filtrar_person_pelo_numero_do_telefone() {
        filter.setPhone("997504");

        List<Person> person = sut.filter(filter);

        assertThat(person.size()).isEqualTo(0);
    }
}
