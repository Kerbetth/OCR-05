package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonDao {

    /**
     * the Dao method deploy the content of the jsonfile in a database composed with corresponding object
     * the @Value give the name of the file, which is variable for test purposes
     * writer method is the one who edited the jsonfile in order to registered the modifications
     * All the find methods get all sort of data from the constructed database
     *
     */

    @Autowired
    private JsonReaderWriter jsonReaderWriter;


    public List<Person> getPersons() {
        return jsonReaderWriter.getDtb().getPersons();
    }

    public Person findPersonByName(String name) {
        Optional<Person> person =
               getPersons()
                        .stream()
                        .filter(currentPerson -> Objects.equals(name, currentPerson.getFirstName() + currentPerson.getLastName()))
                        .findFirst();
        if (person.isEmpty()) {
            return null;
        }
        return person.get();
    }

    public List<Person> findPersonsWithSameFirstNameOrLastName(String firstName, String lastName) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(firstName, person.getFirstName()) || Objects.equals(lastName, person.getLastName()))
                .collect(Collectors.toList());
    }

    public List<Person> findPersonByCity(String city) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(city, person.getCity()))
                .collect(Collectors.toList());
    }

    public List<Person> findPersonByAddress(String Address) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(Address, person.getAddress()))
                .collect(Collectors.toList());
    }

    public void updateJson(List<Person> persons){
        jsonReaderWriter.updatePersons(persons);
    }

}
