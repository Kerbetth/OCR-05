package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.dao.DTOFactory;
import com.safetynet.safetynetalert.dao.MedicalRecordDao;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.ExistingDataException;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.exceptions.WrongFormatException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService extends PersAndMedService {
    
    /**
     *  addPerson add a new Person entry with a corresponding new addMedicalrecord entry
     *  setPerson set address, city, zip, phone and email if a corresponding param exist in the request
     *
     */
    @Autowired
    private MedicalRecordDao medicalRecordDao;

    @Autowired
    private PersonDao personDao;

    public Logger logger = LogManager.getLogger("PersonDao");

    String regexEmail = "^(.+)@(.+)$";
    String regexPhone = "^[0-9-]{8,12}$";

    public List<Object> addPerson(Person newPersonData) {
        if (newPersonData.getPhone() == null || newPersonData.getPhone().matches(regexPhone)) {
            if (newPersonData.getEmail() == null || newPersonData.getEmail().matches(regexEmail)) {
                List<Person> persons = personDao.getPersons();
                List<Medicalrecord> medicalrecords = medicalRecordDao.getMedicalrecords();
                if (medicalrecords.size() == persons.size()) {
                    if (personDao.findPersonByName(newPersonData.getFirstName() + newPersonData.getLastName()) == null) {
                        List<Object> result = new ArrayList<>();
                        persons.add(newPersonData);
                        medicalrecords.add(DTOFactory.createdefaultMedicalRecord(newPersonData.getFirstName(), newPersonData.getLastName()));
                        personDao.updateJson(persons);
                        medicalRecordDao.updateJson(medicalrecords);
                        result.add(medicalRecordDao.getMedicalrecords().get(medicalRecordDao.getMedicalrecords().size() - 1));
                        result.add(personDao.getPersons().get(personDao.getPersons().size() - 1));
                        logger.info("A new Person Post request with the name "+newPersonData.getFirstName()+
                                " "+newPersonData.getLastName() +" has been added.");
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
        Person personUpdated = personDao.findPersonByName(name);
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
        List<Person> persons = personDao.getPersons();
        persons.set(getIdByName(name), personUpdated);
        personDao.updateJson(persons);
        logger.info("A new Person Put request on person "+name+" has been done:");
        return personUpdated;
    }

    //***********Html Methods*************//

    public Person findPersonByName(String name){
        return personDao.findPersonByName(name);
    }
}
