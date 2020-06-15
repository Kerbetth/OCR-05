package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.dao.DTOFactory;
import com.safetynet.safetynetalert.dao.DaoMedicalRecord;
import com.safetynet.safetynetalert.dao.DaoPerson;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalrecordService extends PersAndMedService {

    public Logger logger = LogManager.getLogger("MedicalrecordDao");

    @Autowired
    private DaoMedicalRecord daoMedicalRecord;
    @Autowired
    private DaoPerson daoPerson;

    /**
     * addMedicalrecord add a new MedicalReord entry with a corresponding new Person entry
     * setMedicalrecord set birthdate, medication and allergies if a corresponding param exist in the request
     *
     */

    public List<Object> addMedicalrecord(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = daoMedicalRecord.getMedicalrecords();
        List<Person> persons = daoPerson.getPersons();
        if (medicalrecords.size() == persons.size()) {
            if (daoPerson.findPersonByName(medicalrecord.getFirstName() + medicalrecord.getLastName()) == null) {
                List<Object> result = new ArrayList<>();
                medicalrecords.add(medicalrecord);
                persons.add(DTOFactory.createdefaultPerson(medicalrecord.getFirstName(), medicalrecord.getLastName()));
                daoMedicalRecord.updateJson(medicalrecords);
                daoPerson.updateJson(persons);
                result.add(daoMedicalRecord.getMedicalrecords().get(daoMedicalRecord.getMedicalrecords().size() - 1));
                result.add(daoPerson.getPersons().get(daoPerson.getPersons().size() - 1));
                logger.info("A new Person Post request with the name "+medicalrecord.getFirstName()+
                        " "+medicalrecord.getLastName() +"has been added.");
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
        List<Medicalrecord> medicalrecords = daoMedicalRecord.getMedicalrecords();
        Medicalrecord medicalRecordUpdated = daoMedicalRecord.findMedicalrecordByID(id);
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
        daoMedicalRecord.updateJson(medicalrecords);
        logger.info("A new MedicalRecord Put request on person "+firstNameLastName+" has been done:");
        return daoMedicalRecord.getMedicalrecords().get(id);
    }



    //***********Html Methods*************//

    public Medicalrecord findMedicalrecordByID(int id){
        return daoMedicalRecord.findMedicalrecordByID(id);
    }
}
