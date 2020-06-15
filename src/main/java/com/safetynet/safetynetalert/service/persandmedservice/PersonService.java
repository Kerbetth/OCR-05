package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.dao.DTOFactory;
import com.safetynet.safetynetalert.dao.DaoMedicalRecord;
import com.safetynet.safetynetalert.dao.DaoPerson;
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
    private DaoMedicalRecord daoMedicalRecord;

    @Autowired
    private DaoPerson daoPerson;

    public Logger logger = LogManager.getLogger("PersonDao");

    String regexEmail = "^(.+)@(.+)$";
    String regexPhone = "^[0-9-]{8,12}$";

    public List<Object> addPerson(Person newPersonData) {
        if (newPersonData.getPhone() == null || newPersonData.getPhone().matches(regexPhone)) {
            if (newPersonData.getEmail() == null || newPersonData.getEmail().matches(regexEmail)) {
                List<Person> persons = daoPerson.getPersons();
                List<Medicalrecord> medicalrecords = daoMedicalRecord.getMedicalrecords();
                if (medicalrecords.size() == persons.size()) {
                    if (daoPerson.findPersonByName(newPersonData.getFirstName() + newPersonData.getLastName()) == null) {
                        List<Object> result = new ArrayList<>();
                        persons.add(newPersonData);
                        medicalrecords.add(DTOFactory.createdefaultMedicalRecord(newPersonData.getFirstName(), newPersonData.getLastName()));
                        daoPerson.updateJson(persons);
                        daoMedicalRecord.updateJson(medicalrecords);
                        result.add(daoMedicalRecord.getMedicalrecords().get(daoMedicalRecord.getMedicalrecords().size() - 1));
                        result.add(daoPerson.getPersons().get(daoPerson.getPersons().size() - 1));
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
        Person personUpdated = daoPerson.findPersonByName(name);
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
        daoPerson.updateJson(daoPerson.getPersons().set(getIdByName(name), personUpdated));
        logger.info("A new Person Put request on person "+name+" has been done:");
        return personUpdated;
    }

    //***********Html Methods*************//

    public Person findPersonByName(String name){
        return daoPerson.findPersonByName(name);
    }
}
