package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalrecordDao extends Dao{

    private static final Logger logger = LogManager.getLogger("MedicalrecordDao");

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
        jsonWriter.writer(database, jsonPath);
    }

    public void setMedicalrecord(Medicalrecord medicalrecord) {
        database.getMedicalrecords().set(getIdByName(medicalrecord.getFirstName()+medicalrecord.getLastName()), medicalrecord);
        jsonWriter.writer(database, jsonPath);
    }

    public void deleteMedicalRecordAndPersonEntry(Integer id){
        database.getMedicalrecords().remove(database.getMedicalrecords().get(id));
        database.getPersons().remove(database.getPersons().get(id));
        if (database.getPersons().size() != database.getMedicalrecords().size()) {
            logger.error("Fatal Error, the number of persons and medicalRecords are not the same, please check the JSON file");
        }
        jsonWriter.writer(database, jsonPath);
    }
}
