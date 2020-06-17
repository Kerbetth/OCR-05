package com.safetynet.safetynetalert.daodto.persondao;

import com.safetynet.safetynetalert.domain.Person;

import java.util.List;

/**
 * the get method retrieve all Persons belonging to the JSONfile
 * the find method retrieve some Persons in order to certain conditions
 * the update method send the edited data in order to be writing in the JSONfile
 */

public interface PersonDaoInterface {
    List<Person> getPersons();

    Person findPersonByName(String name);

    Integer getIdByName(String name);

    List<Person> findPersonsWithSameFirstNameOrLastName(String firstName, String lastName);

    List<Person> findPersonByCity(String city);

    List<Person> findPersonByAddress(String Address);

    void updateJson(List<Person> persons);
}
