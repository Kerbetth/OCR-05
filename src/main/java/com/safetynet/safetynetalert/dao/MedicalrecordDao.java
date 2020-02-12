package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalrecordDao extends PersAndMedDao{

    public Logger logger = LogManager.getLogger("MedicalrecordDao");

    public List<Object> addMedicalrecord(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = database.getMedicalrecords();
        List<Person> persons = database.getPersons();
        if (medicalrecords.size() == persons.size()) {
            if (findPersonByName(medicalrecord.getFirstName() + medicalrecord.getLastName()) == null) {
                List<Object> result = new ArrayList<>();
                medicalrecords.add(medicalrecord);
                persons.add(DTOFactory.createdefaultPerson(medicalrecord.getFirstName(), medicalrecord.getLastName()));
                database.setMedicalrecords(medicalrecords);
                database.setPersons(persons);
                jsonWriter.writer(database, jsonPath);
                result.add(database.getMedicalrecords().get(database.getMedicalrecords().size() - 1));
                result.add(database.getPersons().get(database.getPersons().size() - 1));
                return result;
            } else {
                logger.error(LogArgs.getExistingNameMessage(medicalrecord.getFirstName() + medicalrecord.getLastName()));
                return null;
            }
        } else {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
    }

    public Medicalrecord setMedicalrecord(String firstNameLastName, Medicalrecord medrecEdit) {
        Integer id = getIdByName(firstNameLastName);
        Medicalrecord medicalRecordUpdated = findMedicalrecordByID(id);
        if (medrecEdit.getBirthdate() != null) {
            medicalRecordUpdated.setBirthdate(medrecEdit.getBirthdate());
        }
        if (medrecEdit.getMedications() != null) {
            medicalRecordUpdated.setMedications(medrecEdit.getMedications());
        }
        if (medrecEdit.getAllergies() != null) {
            medicalRecordUpdated.setAllergies(medrecEdit.getAllergies());
        }
        database.getMedicalrecords().set(id, medicalRecordUpdated);
        jsonWriter.writer(database, jsonPath);
        return database.getMedicalrecords().get(id);
    }

}
