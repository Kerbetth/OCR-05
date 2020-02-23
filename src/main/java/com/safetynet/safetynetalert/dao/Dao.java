package com.safetynet.safetynetalert.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Dao {

    private Database database;
    private static final Logger logger = LogManager.getLogger("Dao");
    public String jsonFile;

    public Dao(@Value("${jsonFileName}") String jsonfile) {
        try {
            FileReader fileString = new FileReader(getClass().getClassLoader().getResource(jsonfile).getFile());
            database = new ObjectMapper()
                    .readValue(fileString,
                            Database.class
                    );
            this.jsonFile = jsonfile;
        } catch (IOException e) {
            e.printStackTrace();}
    }

    public void writer(Database database) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person findPersonByName(String name) {
        System.out.println(database);
        System.out.println(jsonFile);
        Optional<Person> person =
                database.getPersons()
                        .stream()
                        .filter(currentPerson -> Objects.equals(name, currentPerson.getFirstName() + currentPerson.getLastName()))
                        .findFirst();
        if (person.isEmpty()) {
            return null;
        }
        return person.get();
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

    public Medicalrecord findMedicalrecordByPerson(String name) {
        Optional<Medicalrecord> medicalrecord =
                database.getMedicalrecords()
                        .stream()
                        .filter(currentMed -> Objects.equals(name, currentMed.getFirstName() + currentMed.getLastName()))
                        .findFirst();
        if (medicalrecord.isEmpty()) {
            throw new NoEntryException(name);
        }
        return medicalrecord.get();
    }

    public Medicalrecord findMedicalrecordByID(Integer id) {
        return database.getMedicalrecords().get(id);
    }

    public Firestation findFirestationByAddress(String address) {
        Optional<Firestation> firestation1 =
                database.getFirestations()
                        .stream()
                        .filter(firestation -> Objects.equals(address, firestation.getAddress()))
                        .findFirst();
        if (firestation1.isEmpty()) {
            throw new NoEntryException(address);
        }
        return firestation1.get();
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
            if (stationAddressList.isEmpty()) {
                return null;
            }
            return stationAddressList;
        } else {
            return null;
        }
    }


    public Database getDtb() {
        return database;
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
