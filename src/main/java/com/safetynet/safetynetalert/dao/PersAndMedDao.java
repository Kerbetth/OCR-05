package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersAndMedDao extends Dao{

    private static final Logger logger = LogManager.getLogger("PersAndMedDao");

    public void deleteMedicalRecordAndPersonEntry(String name) {
        int id = getIdByName(name);
        database.getMedicalrecords().remove(database.getMedicalrecords().get(id));
        database.getPersons().remove(database.getPersons().get(id));
        if (database.getPersons().size() != database.getMedicalrecords().size()) {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
        jsonWriter.writer(database, jsonPath);
    }

    public Integer getIdByName(String name) {
        int id = 0;
        List<Person> persons = database.getPersons();
        for (Person person : persons) {
            if ((person.getFirstName() + person.getLastName()).equals(name)) {
                break;
            } else id++;
        }
        if (id >= persons.size()) {
            throw new NoEntryException(name);
        }
        return id;
    }

}
