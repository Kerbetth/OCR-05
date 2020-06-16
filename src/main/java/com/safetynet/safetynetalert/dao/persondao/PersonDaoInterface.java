package com.safetynet.safetynetalert.dao.persondao;

import com.safetynet.safetynetalert.domain.Person;

import java.util.List;

public interface PersonDaoInterface {
    List<Person> getPersons();

    Person findPersonByName(String name);

    Integer getIdByName(String name);

    List<Person> findPersonsWithSameFirstNameOrLastName(String firstName, String lastName);

    List<Person> findPersonByCity(String city);

    List<Person> findPersonByAddress(String Address);

    void updateJson(List<Person> persons);
}
