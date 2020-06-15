package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.dao.DaoFirestation;
import com.safetynet.safetynetalert.dao.DaoMedicalRecord;
import com.safetynet.safetynetalert.dao.DaoPerson;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersAndMedService {

    private static final Logger logger = LogManager.getLogger("PersAndMedDao");

    /**
     * deleteMedicalRecordAndPersonEntryn Delete the two corresponding entry of the same person
     * getIdByName get the id according to the firstname followed by the lastname
     *
     */

    @Autowired
    DaoPerson daoPerson;
    @Autowired
    DaoMedicalRecord daoMedicalRecord;

    public void deleteMedicalRecordAndPersonEntry(String name) {
        int id = getIdByName(name);
        List<Medicalrecord> medicalrecords = daoMedicalRecord.getMedicalrecords();
        List<Person> persons = daoPerson.getPersons();
        medicalrecords.remove(medicalrecords.get(id));
        persons.remove(persons.get(id));
        if (persons.size() != medicalrecords.size()) {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
        daoPerson.updateJson(persons);
        logger.info("A Person/Medical Delete request has been sent for the Person with the name "+ name +" which has been deleted.");
    }

    public Integer getIdByName(String name) {
        int id = 0;
        List<Person> persons = daoPerson.getPersons();
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
