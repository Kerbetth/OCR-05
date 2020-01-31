package com.safetynet.SafetyNetAlert.mentorat.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.mentorat.db.Database;
import com.safetynet.SafetyNetAlert.mentorat.db.Medicalrecord;
import com.safetynet.SafetyNetAlert.mentorat.db.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MentoratDao {

    private Database database;

    public MentoratDao() throws IOException {
        database = new ObjectMapper()
                .readerFor(Database.class)
                .readValue(
                        Optional.ofNullable(
                                MentoratDao.class.getClassLoader().getResourceAsStream("data.json")
                        ).orElseThrow()
                );
    }

    public List<Person> findPerson(String firstname, String lastname) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(firstname, person.getFirstName()) && Objects.equals(lastname, person.getLastName()))
                .collect(Collectors.toList());
    }

    public List<Medicalrecord> findMedicalrecord(Person person) {
        return database.getMedicalrecords()
                .stream()
                .filter(medicalrecord -> Objects.equals(medicalrecord.getFirstName(), person.getFirstName()) || Objects.equals(medicalrecord.getLastName(), person.getLastName()))
                .collect(Collectors.toList());
    }
}
