package com.safetynet.SafetyNetAlert.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.beans.Database;
import com.safetynet.SafetyNetAlert.beans.Firestation;
import com.safetynet.SafetyNetAlert.beans.Medicalrecord;
import com.safetynet.SafetyNetAlert.beans.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class Dao {

    private static final Logger logger = LogManager.getLogger("Dao");
    private Database database;

    public Dao() throws IOException {
        database = new ObjectMapper()
                .readerFor(Database.class)
                .readValue(
                        Optional.ofNullable(
                                Dao.class.getClassLoader().getResourceAsStream("data.json")
                        ).orElseThrow()
                );
    }

    public List<Person> findPersonByName(String fname, String lname) {
        return database.getPersons()
                .stream()
                .filter(person -> Objects.equals(fname, person.getFirstName()) || Objects.equals(lname, person.getLastName()))
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

    public Person findPersonByID(Integer id) {
        return database.getPersons().get(id);
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


    public void addMedicalrecord(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = database.getMedicalrecords();
        List<Person> persons = database.getPersons();
        if (medicalrecords.size() == persons.size()) {
            medicalrecords.add(medicalrecord);
            persons.add(DTOFactory.createdefaultPerson(medicalrecord.getFirstName(), medicalrecord.getLastName()));
            database.setMedicalrecords(medicalrecords);
            database.setPersons(persons);
        } else {
            logger.error("Fatal Error, the number of persons and of medicalRecords are not the same, please check the JSON file");
        }
    }

    public void addPerson(Person person) {
        List<Person> persons = database.getPersons();
        List<Medicalrecord> medicalrecords = database.getMedicalrecords();
        if (medicalrecords.size() == persons.size()) {
            persons.add(person);
            medicalrecords.add(DTOFactory.createdefaultMedicalRecord(person.getFirstName(), person.getLastName()));
            database.setMedicalrecords(medicalrecords);
            database.setPersons(persons);
        } else {
            logger.error("Fatal Error, the number of persons and medicalRecords are not the same, please check the JSON file");
        }
    }

    public void addFirestation(Firestation firestation) {
            database.getFirestations().add(firestation);
    }

    public void setMedicalrecord(Integer id, Medicalrecord medicalrecord) {
        database.getMedicalrecords().set(id, medicalrecord);
    }

    public void setPerson(Integer id, Person person) {
        database.getPersons().set(id, person);
    }

    public void setFirestation(Integer id, Firestation firestation) {
        database.getFirestations().set(id, firestation);
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

    public void deleteMedicalRecordAndPersonEntry(Integer id){
        database.getMedicalrecords().remove(id);
        database.getPersons().remove(id);
        if (database.getPersons().size() != database.getMedicalrecords().size()) {
            logger.error("Fatal Error, the number of persons and medicalRecords are not the same, please check the JSON file");
        }
    }

    public Firestation findFirestationByAddress(String address) {
        return database.getFirestations()
                .stream()
                .filter(firestation -> Objects.equals(address, firestation.getAddress()))
                .findFirst().get();
    }

    public Integer getIdByAddress(String address) {
        Integer id = 0;
        List<Firestation> firestations = database.getFirestations();
        for (Firestation firestation :firestations) {
            if ((firestation.getAddress()).equals(address)){
                break;
            } else id++;
        }
        return id;
    }

    public void deleteFirestation(Integer id) {
        database.getFirestations().remove(id);
    }


    //***********Html Methods*************//

    public List<Person> loadPersons() {
        return database.getPersons();
    }

    public List<Medicalrecord> loadMedicalRecords() {
        return database.getMedicalrecords();
    }

    public List<Firestation> loadFirestions() {
        return database.getFirestations();
    }

}
