package com.safetynet.safetynetalert.service.CRUDservice;

import com.safetynet.safetynetalert.dao.DTOFactory;
import com.safetynet.safetynetalert.dao.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.dao.medicalrecorddao.MedicalRecordDaoInterface;
import com.safetynet.safetynetalert.dao.persondao.PersonDao;
import com.safetynet.safetynetalert.dao.persondao.PersonDaoInterface;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.exceptions.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalrecordService implements CRUDService<Medicalrecord> {

    public Logger logger = LogManager.getLogger("MedicalrecordDao");

    @Autowired
    private MedicalRecordDaoInterface medicalRecordDao;
    @Autowired
    private PersonDaoInterface personDao;

    /**
     * addMedicalrecord add a new MedicalReord entry with a corresponding new Person entry
     * setMedicalrecord set birthdate, medication and allergies if a corresponding param exist in the request
     *
     */

    public Medicalrecord add(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = medicalRecordDao.getMedicalrecords();
        List<Person> persons = personDao.getPersons();
        if (medicalrecords.size() == persons.size()) {
            if (personDao.findPersonByName(medicalrecord.getFirstName() + medicalrecord.getLastName()) == null) {
                List<Object> result = new ArrayList<>();
                medicalrecords.add(medicalrecord);
                persons.add(DTOFactory.createdefaultPerson(medicalrecord.getFirstName(), medicalrecord.getLastName()));
                medicalRecordDao.updateJson(medicalrecords);
                personDao.updateJson(persons);
                logger.info("A new Person Post request with the name "+medicalrecord.getFirstName()+
                        " "+medicalrecord.getLastName() +"has been added.");
                return medicalrecord;
            } else {
                logger.error(LogArgs.getExistingNameMessage(medicalrecord.getFirstName() + medicalrecord.getLastName()));
                return null;
            }
        } else {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
    }

    public Medicalrecord set(String firstNameLastName, Medicalrecord medrecEdit) {
        Integer id = personDao.getIdByName(firstNameLastName);
        List<Medicalrecord> medicalrecords = medicalRecordDao.getMedicalrecords();
        Medicalrecord medicalRecordUpdated = medicalRecordDao.findMedicalrecordByID(id);
        if (medrecEdit.getBirthdate() != null) {
            medicalRecordUpdated.setBirthdate(medrecEdit.getBirthdate());
        }
        if (medrecEdit.getMedications() != null) {
            medicalRecordUpdated.setMedications(medrecEdit.getMedications());
        }
        if (medrecEdit.getAllergies() != null) {
            medicalRecordUpdated.setAllergies(medrecEdit.getAllergies());
        }
        medicalrecords.set(id, medicalRecordUpdated);
        medicalRecordDao.updateJson(medicalrecords);
        logger.info("A new MedicalRecord Put request on person "+firstNameLastName+" has been done:");
        return medicalRecordDao.getMedicalrecords().get(id);
    }

    public void delete(String name) {
        int id = personDao.getIdByName(name);
        List<Medicalrecord> medicalrecords = medicalRecordDao.getMedicalrecords();
        List<Person> persons = personDao.getPersons();
        medicalrecords.remove(medicalrecords.get(id));
        persons.remove(persons.get(id));
        if (persons.size() != medicalrecords.size()) {
            logger.error(LogArgs.getNotEqualSizeMessage());
            throw new NotEqualSizeListException();
        }
        personDao.updateJson(persons);
        logger.info("A Person/Medical Delete request has been sent for the Person with the name "+ name +" which has been deleted.");
    }

}
