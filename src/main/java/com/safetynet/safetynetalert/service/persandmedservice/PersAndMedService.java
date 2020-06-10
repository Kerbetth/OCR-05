package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    Dao dao;

    public void deleteMedicalRecordAndPersonEntry(String name) {
        int id = getIdByName(name);
        dao.getDtb().getMedicalrecords().remove(dao.getDtb().getMedicalrecords().get(id));
        dao.getDtb().getPersons().remove(dao.getDtb().getPersons().get(id));
        if (dao.getDtb().getPersons().size() != dao.getDtb().getMedicalrecords().size()) {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
        dao.writer(dao.getDtb());
    }

    public Integer getIdByName(String name) {
        int id = 0;
        List<Person> persons = dao.getDtb().getPersons();
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

    public Dao getDao() {
        return dao;
    }

    public Database getDtb() {
        return dao.getDtb();
    }
}
