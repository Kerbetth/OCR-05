package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class PersonDao extends Dao{

    private static final Logger logger = LogManager.getLogger("PersonDao");

    public PersonDao() throws IOException {
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

    public void setPerson(Person person) {
        database.getPersons().set(getIdByName(person.getFirstName()+person.getLastName()), person);
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
}
