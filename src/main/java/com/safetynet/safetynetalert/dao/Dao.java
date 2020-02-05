package com.safetynet.safetynetalert.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Dao {

    Database database;

    public Dao() throws IOException {
        database = new ObjectMapper()
                .readerFor(Database.class)
                .readValue(
                        Optional.ofNullable(
                                PersonDao.class.getClassLoader().getResourceAsStream("data.json")
                        ).orElseThrow()
                );
    }

    public void daoWriter(Database database){
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = Obj.writeValueAsString(database);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("src/main/resources/data.json")) {
            file.write(jsonStr);
        } catch (IOException e) {
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

    public Set<Firestation> getStationAddresses(String stationNumbers) {
        Set<Firestation> stationAddressList = new HashSet<>();
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
