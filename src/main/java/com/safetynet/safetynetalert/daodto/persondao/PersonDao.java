package com.safetynet.safetynetalert.daodto.persondao;

import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriterInterface;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PersonDao implements PersonDaoInterface {

    @Autowired
    private JsonReaderWriterInterface jsonReaderWriter;


    @Override
    public List<Person> getPersons() {
        return jsonReaderWriter.getDtb().getPersons();
    }

    @Override
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

    @Override
    public Integer getIdByName(String name) {
        int id = 0;
        List<Person> persons = getPersons();
        for (Person person : persons) {
            if ((person.getFirstName() + person.getLastName()).equals(name)) {
                break;
            } else id++;
        }
        if (id >= persons.size()) {
            throw new NoEntryException(name);
        }
        return id;
    }

    @Override
    public List<Person> findPersonsWithSameFirstNameOrLastName(String firstName, String lastName) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(firstName, person.getFirstName()) || Objects.equals(lastName, person.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findPersonByCity(String city) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(city, person.getCity()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findPersonByAddress(String Address) {
        return getPersons()
                .stream()
                .filter(person -> Objects.equals(Address, person.getAddress()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateJson(List<Person> persons){
        jsonReaderWriter.updatePersons(persons);
    }

}
