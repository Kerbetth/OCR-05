package com.safetynet.safetynetalert.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Dao{

    public Database database;
    public String jsonPath = "data.json";
    public JsonWriter jsonWriter = new JsonWriter();

    public Dao() {
        try {
            database = new ObjectMapper()
                    .readerFor(Database.class)
                    .readValue(
                            Optional.ofNullable(
                                    PersonDao.class.getClassLoader().getResourceAsStream(jsonPath)
                            ).orElseThrow()
                    );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Person findPersonByName(String fnamelname) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(fnamelname, person.getFirstName()+person.getLastName()))
                .findFirst().get();
    }

    public List<Person> findPersonsWithSameFirstNameOrLastName(String firstName, String lastName) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(firstName, person.getFirstName()) || Objects.equals(lastName, person.getLastName()))
                .collect(Collectors.toList());
    }

    public List<Person> findPersonByCity(String city) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(city, person.getCity()))
                .collect(Collectors.toList());
    }

    public List<Person> findPersonByAddress(String Address) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(Address, person.getAddress()))
                .collect(Collectors.toList());
    }

    public Medicalrecord findMedicalrecordByPerson(Person person) {
        return database.getMedicalrecords()
                .stream()
                .filter(medicalrecord -> Objects.equals(medicalrecord.getFirstName(), person.getFirstName()) && Objects.equals(medicalrecord.getLastName(), person.getLastName()))
                .findFirst().get();
    }

    public Medicalrecord findMedicalrecordByID(Integer id) {
        return database.getMedicalrecords().get(id);
    }

    public Firestation findFirestationByAddress(String address) {
        return database.getFirestations()
                .stream()
                .filter(firestation -> Objects.equals(address, firestation.getAddress()))
                .findFirst().get();
    }

    public List<Firestation> findFirestationsByNumber(String stationNumbers) {
        List<Firestation> stationAddressList = new ArrayList<>();
        if (stationNumbers != null) {
            Set<String> stationNumbersSet = new HashSet<>((Arrays.asList(stationNumbers.split(","))));
            for (String stationNumber : stationNumbersSet) {
                stationAddressList.addAll(database.getFirestations()
                        .stream()
                        .filter(firestation -> Objects.equals(firestation.getStation(), Integer.parseInt(stationNumber)))
                        .collect(Collectors.toList()));
            }
            return stationAddressList;
        } else {
            return null;
        }
    }

    public Integer getIdByName(String firstNameLastName) {
        Integer id = 0;
        List<Person> persons = database.getPersons();
        for (Person person : persons) {
            if ((person.getFirstName()+person.getLastName()).equals(firstNameLastName)){
                break;
            } else id++;
        }
        return id;
    }
    //***********Html Methods*************//

    public List<Person> loadPersons() {
        return database.getPersons();
    }
    public List<Firestation> loadFirestions() {
        return database.getFirestations();
    }
    public List<Medicalrecord> loadMedicalRecords() {
        return database.getMedicalrecords();
    }


}
