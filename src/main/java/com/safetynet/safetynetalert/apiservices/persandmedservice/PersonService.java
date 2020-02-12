package com.safetynet.safetynetalert.apiservices.persandmedservice;

import com.safetynet.safetynetalert.apiservices.DTOFactory;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.ExistingDataException;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.exceptions.WrongFormatException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService extends PersAndMedService {

    public Logger logger = LogManager.getLogger("PersonDao");

    String regexEmail = "^(.+)@(.+)$";
    String regexPhone = "^[0-9-]{8,12}$";

    public List<Object> addPerson(Person newPersonData) {
        if (newPersonData.getPhone() == null || newPersonData.getPhone().matches(regexPhone)) {
            if (newPersonData.getEmail() == null || newPersonData.getEmail().matches(regexEmail)) {
                List<Person> persons = dao.getDtb().getPersons();
                List<Medicalrecord> medicalrecords = dao.getDtb().getMedicalrecords();
                if (medicalrecords.size() == persons.size()) {
                    if (dao.findPersonByName(newPersonData.getFirstName() + newPersonData.getLastName()) == null) {
                        List<Object> result = new ArrayList<>();
                        persons.add(newPersonData);
                        medicalrecords.add(DTOFactory.createdefaultMedicalRecord(newPersonData.getFirstName(), newPersonData.getLastName()));
                        dao.getDtb().setMedicalrecords(medicalrecords);
                        dao.getDtb().setPersons(persons);
                        dao.writer(dao.getDtb());
                        result.add(dao.getDtb().getMedicalrecords().get(dao.getDtb().getMedicalrecords().size() - 1));
                        result.add(dao.getDtb().getPersons().get(dao.getDtb().getPersons().size() - 1));
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
        Person personUpdated = dao.findPersonByName(name);
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
        dao.getDtb().getPersons().set(getIdByName(name), personUpdated);
        dao.writer(dao.getDtb());
        return personUpdated;
    }

    //***********Html Methods*************//

    public Person findPersonByName(String name){
        return dao.findPersonByName(name);
    }
}
