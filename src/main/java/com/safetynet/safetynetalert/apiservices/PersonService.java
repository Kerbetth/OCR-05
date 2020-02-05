package com.safetynet.safetynetalert.apiservices;


import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger("PostPutDeleteService");
    @Autowired
    private PersonDao dao;
    String regexemail = "^(.+)@(.+)$";
    String regexPhone = "^[0-9-]{8,12}$";

    public ResponseEntity postPerson(Person person) {
        Pattern patternEmail = Pattern.compile(regexemail);
        Pattern patternPhone = Pattern.compile(regexPhone);
        if (person.getFirstName() != null) {
            if (person.getLastName() != null) {
                if (person.getPhone() == null || patternPhone.matcher(person.getPhone()).matches()) {
                    if (person.getEmail() == null || patternEmail.matcher(person.getEmail()).matches()) {
                        dao.addPerson(person);
                        return ResponseEntity.ok()
                                .body("New Person with medical record added successfully");
                    } else {
                        logger.error("Error, " + person.getEmail() + "is not correct, please enter a correct email format");
                        return ResponseEntity.badRequest()
                                .body("Error, " + person.getEmail() + "is not correct, please enter a correct email format");
                    }
                } else {
                    logger.error("Error, " + person.getPhone() + "is not correct, please enter a correct phone number");
                    return ResponseEntity.badRequest()
                            .body("Error, " + person.getPhone() + "is not correct, please enter a correct phone number");
                }
            } else {
                logger.error("LastName not specify, process aborted");
                return ResponseEntity.badRequest()
                        .body("LastName not specify, process aborted");
            }
        } else {
            logger.error("FirstName not specify, process aborted");
            return ResponseEntity.badRequest()
                    .body("FirstName not specify, process aborted");
        }
    }

    public ResponseEntity putPerson(String firstNameLastName, Person person) {
        Person newperson = dao.findPersonByName(firstNameLastName);
        if (person.getAddress() != null) {
            newperson.setAddress(person.getAddress());
        }
        if (person.getCity() != null) {
            newperson.setCity(person.getCity());
        }
        if (person.getZip() != null) {
            newperson.setZip(person.getZip());
        }
        if (person.getPhone() != null) {
            newperson.setPhone(person.getPhone());
        }
        if (person.getEmail()!= null){
            newperson.setEmail((person.getEmail()));
        }
        dao.setPerson(newperson);
        return ResponseEntity.ok()
                .body("Medical record  of Mr/Mrs " + newperson.getFirstName()
                        + " "
                        + newperson.getLastName() + " has been updated");
    }

    public ResponseEntity deletePersonAndMedicalRecord(String firstNameLastName) {
        Integer id = dao.getIdByName(firstNameLastName);
        dao.deleteMedicalRecordAndPersonEntry(id);
        return ResponseEntity.ok()
                .body("Person and his medical record ara succesfully deleted");
    }

}
