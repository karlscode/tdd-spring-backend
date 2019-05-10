package com.example.demo.repository;

import com.example.demo.model.Person;
import com.example.demo.repository.helper.PersonRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author KarlsCode.
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryQueries {

    Optional<Person> findByCpf(String cpf);

    @Query("SELECT bean FROM Person bean JOIN bean.phones phone WHERE phone.ddd = :ddd AND phone.number = :numberPhone")
    Optional<Person> findByPhoneDddAndPhoneNumber(@Param("ddd") String ddd, @Param("numberPhone") String numberPhone);
}
