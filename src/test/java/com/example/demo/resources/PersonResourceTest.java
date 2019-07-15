package com.example.demo.resources;

import com.example.demo.DemoApplicationTests;
import com.example.demo.model.Person;
import com.example.demo.model.Phone;
import com.example.demo.repository.filter.FilterPerson;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author KarlsCode.
 */

public class PersonResourceTest extends DemoApplicationTests {

    private Person person;
    private Phone phone;
    private FilterPerson filterPerson;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        person = new Person();
        phone = new Phone();
        filterPerson = new FilterPerson();
    }

    @Test
    public void must_find_a_person_by_ddd_and_phone_number() {
        given()
                .pathParam("ddd", "86")
                .pathParam("number", "35006330")
        .when()
                .get("/people/{ddd}/{number}")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
//                .body("id", is(3))
                .body("id", is(3))
                .body("name", is("Cauê"))
                .body("cpf", is("38767897100"))
        ;
    }

    @Test
    public void should_return_all_people() {
        given()
        .when()
                .get("/people")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", containsInAnyOrder(1, 2, 3, 4, 5))
                .body("name", containsInAnyOrder("Thiago", "Iago", "Cauê", "Breno", "Pedro"))
                .body("cpf", containsInAnyOrder("72788740417", "38767897100", "86730543540", "55565893569", "78673781620"))
        ;
    }

    @Test
    public void should_return_a_person_by_id() {
        given()
                .pathParam("id", 1)
        .when()
                .get("/people/{id}")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(1))
                .body("name", is("Iago"))
                .body("cpf", is("86730543540"))
        ;
    }

    @Test
    public void should_return_not_found_phone_nonexistent() {
        given()
                .pathParam("ddd", "99")
                .pathParam("number", "987654321")
        .when()
                .get("/people/{ddd}/{number}")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("error", is("Phone not found: (99) 987654321."))
        ;
    }

    @Test
    public void save_new_person() {
        person.setName("Lorenzo");
        person.setCpf("62461410720");
        phone.setDdd("79");
        phone.setNumber("36977168");

        person.setPhones(Arrays.asList(phone));

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(person)
        .when()
                .post("/people")
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", is("http://localhost:" + port + "/people/6"))
                .body("id", is(6))
                .body("name", is("Lorenzo"))
                .body("cpf", is("62461410720"))
        ;
    }

    @Test
    public void should_not_save_two_people_with_the_same_cpf() {
        person.setName("Cauê");
        person.setCpf("38767897100");
        phone.setDdd("99");
        phone.setNumber("55977168");
        person.setPhones(Arrays.asList(phone));

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(person)
        .when()
                .post("/people")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", is("CPF informed is already registered: 38767897100."))
        ;
    }

    @Test
    public void should_not_save_two_people_with_the_same_phone() {
        person.setName("Kaike");
        person.setCpf("38767897109");
        phone.setDdd("41");
        phone.setNumber("999570146");
        person.setPhones(Arrays.asList(phone));

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(person)
        .when()
                .post("/people")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", is("Phone informed is already registered: (41) 999570146."))
        ;
    }

    @Test
    public void must_filter_person_by_name_or_part() {
        filterPerson.setName("a");

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(filterPerson)
        .when()
                .post("/people/filter")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", containsInAnyOrder(1, 3, 5))
                .body("name", containsInAnyOrder("Thiago", "Iago", "Cauê"))
                .body("cpf", containsInAnyOrder("72788740417", "38767897100", "86730543540"))
        ;
    }

    @Test
    public void must_filter_person_by_ddd() {
        filterPerson.setDdd("41");

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(filterPerson)
        .when()
                .post("/people/filter")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", containsInAnyOrder(1))
                .body("name", containsInAnyOrder("Iago"))
                .body("cpf", containsInAnyOrder("86730543540"))
        ;
    }

    @Test
    public void must_filter_person_by_phone() {
        filterPerson.setPhone("38416516");

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(filterPerson)
        .when()
                .post("/people/filter")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", containsInAnyOrder(5))
                .body("name", containsInAnyOrder("Thiago"))
                .body("cpf", containsInAnyOrder("72788740417"))
        ;
    }

    @Test
    public void must_filter_person_by_cpf_or_part() {
        filterPerson.setCpf("8767");

        given()
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(filterPerson)
        .when()
                .post("/people/filter")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_OK)
                .body("id", containsInAnyOrder(3))
                .body("name", containsInAnyOrder("Cauê"))
                .body("phones.ddd", hasItem(Arrays.asList("86")))
        ;
    }

    @Test
    public void delete_person_by_id() {
        given()
                .pathParam("id", 1)
        .when()
                .delete("/people/{id}")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_NO_CONTENT)
        ;
    }

    @Test
    public void should_person_update_by_id() {
        person.setName("Kaike");
        person.setCpf("01383938504");

        given()
                .pathParam("id", 1)
                .request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(person)
        .when()
                .put("/people/{id}")
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .body("id", is(1))
                .body("name", is("Kaike"))
                .body("cpf", is("01383938504"))
        ;
    }
}
