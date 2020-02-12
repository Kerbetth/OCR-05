package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.exceptions.ExistingDataException;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.exceptions.WrongFormatException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDao extends PersAndMedDao {

    private static final Logger logger = LogManager.getLogger("PersonDao");
    String regexEmail = "^(.+)@(.+)$";
    String regexPhone = "^[0-9-]{8,12}$";

    public List<Object> addPerson(Person newPersonData) {
        if (newPersonData.getPhone() == null || newPersonData.getPhone().matches(regexPhone)) {
            if (newPersonData.getEmail() == null || newPersonData.getEmail().matches(regexEmail)) {
                List<Person> persons = database.getPersons();
                List<Medicalrecord> medicalrecords = database.getMedicalrecords();
                if (medicalrecords.size() == persons.size()) {
                    if (findPersonByName(newPersonData.getFirstName() + newPersonData.getLastName()) == null) {
                        List<Object> result = new ArrayList<>();
                        persons.add(newPersonData);
                        medicalrecords.add(DTOFactory.createdefaultMedicalRecord(newPersonData.getFirstName(), newPersonData.getLastName()));
                        database.setMedicalrecords(medicalrecords);
                        database.setPersons(persons);
                        jsonWriter.writer(database, jsonPath);
                        result.add(database.getMedicalrecords().get(database.getMedicalrecords().size() - 1));
                        result.add(database.getPersons().get(database.getPersons().size() - 1));
                        return result;
                    } else {
                        logger.error(LogArgs.getExistingNameMessage(newPersonData.getFirstName() + " " + newPersonData.getLastName()));
                        throw new ExistingDataException(newPersonData.getFirstName() + " " + newPersonData.getLastName());
                    }
                } else {
                    logger.error(LogArgs.getNotEqualSizeMessage());
                    throw new NotEqualSizeListException();
                }
            } else {
                logger.error(LogArgs.getWrongFormatMessage(newPersonData.getEmail()));
                throw new WrongFormatException(newPersonData.getEmail());
            }
        } else {
            logger.error(LogArgs.getWrongFormatMessage(newPersonData.getPhone()));
            throw new WrongFormatException(newPersonData.getPhone());
        }
    }

    public Person setPerson(String name, Person newPersonData) {
        Person personUpdated = findPersonByName(name);
        if (personUpdated == null) {
            throw new NoEntryException(name);
        }
        if (newPersonData.getAddress() != null) {
            personUpdated.setAddress(newPersonData.getAddress());
        }
        if (newPersonData.getCity() != null) {
            personUpdated.setCity(newPersonData.getCity());
        }
        if (newPersonData.getZip() != null) {
            personUpdated.setZip(newPersonData.getZip());
        }
        if (newPersonData.getPhone() != null) {
            if (newPersonData.getPhone().matches(regexPhone)) {
                personUpdated.setPhone(newPersonData.getPhone());
            } else {
                logger.error(LogArgs.getWrongFormatMessage(newPersonData.getPhone()));
                throw new WrongFormatException(newPersonData.getPhone());
            }
        }
        if (newPersonData.getEmail() != null) {
            if (newPersonData.getEmail().matches(regexEmail)) {
                personUpdated.setEmail((newPersonData.getEmail()));
            } else {
                logger.error(LogArgs.getWrongFormatMessage(newPersonData.getEmail()));
                throw new WrongFormatException(newPersonData.getEmail());
            }
        }
        database.getPersons().set(getIdByName(name), newPersonData);
        jsonWriter.writer(database, jsonPath);
        return newPersonData;
    }
}
