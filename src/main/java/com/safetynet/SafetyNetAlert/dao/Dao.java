package com.safetynet.SafetyNetAlert.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.beans.Database;
import com.safetynet.SafetyNetAlert.beans.Firestation;
import com.safetynet.SafetyNetAlert.beans.Medicalrecord;
import com.safetynet.SafetyNetAlert.beans.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
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
        daoWriter(database);
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
        daoWriter(database);
    }

    public void addFirestation(Firestation firestation) {
        database.getFirestations().add(firestation);
        daoWriter(database);
    }

    public void setMedicalrecord(Medicalrecord medicalrecord) {
        database.getMedicalrecords().set(getIdByName(medicalrecord.getFirstName()+medicalrecord.getLastName()), medicalrecord);
        daoWriter(database);
    }

    public void setPerson(Person person) {
        database.getPersons().set(getIdByName(person.getFirstName()+person.getLastName()), person);
        daoWriter(database);
    }

    public void setFirestation(Firestation firestation) {
        database.getFirestations().set(getIdByAddress(firestation.getAddress()), firestation);
        daoWriter(database);
    }

    public void deleteMedicalRecordAndPersonEntry(Integer id){
        database.getMedicalrecords().remove(database.getMedicalrecords().get(id));
        database.getPersons().remove(database.getPersons().get(id));
        if (database.getPersons().size() != database.getMedicalrecords().size()) {
            logger.error("Fatal Error, the number of persons and medicalRecords are not the same, please check the JSON file");
        }
        daoWriter(database);
    }

    public void deleteFirestation(Integer id) {
        database.getFirestations().remove(database.getFirestations().get(id));
        daoWriter(database);
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

    public List<Medicalrecord> loadMedicalRecords() {
        return database.getMedicalrecords();
    }

    public List<Firestation> loadFirestions() {
        return database.getFirestations();
    }


}
