package com.safetynet.safetynetalert.service.persandmedservice;

import com.safetynet.safetynetalert.service.DTOFactory;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NotEqualSizeListException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalrecordService extends PersAndMedService {

    public Logger logger = LogManager.getLogger("MedicalrecordDao");



    public List<Object> addMedicalrecord(Medicalrecord medicalrecord) {
        List<Medicalrecord> medicalrecords = dao.getDtb().getMedicalrecords();
        List<Person> persons = dao.getDtb().getPersons();
        if (medicalrecords.size() == persons.size()) {
            if (dao.findPersonByName(medicalrecord.getFirstName() + medicalrecord.getLastName()) == null) {
                List<Object> result = new ArrayList<>();
                medicalrecords.add(medicalrecord);
                persons.add(DTOFactory.createdefaultPerson(medicalrecord.getFirstName(), medicalrecord.getLastName()));
                dao.getDtb().setMedicalrecords(medicalrecords);
                dao.getDtb().setPersons(persons);
                dao.writer(dao.getDtb());
                result.add(dao.getDtb().getMedicalrecords().get(dao.getDtb().getMedicalrecords().size() - 1));
                result.add(dao.getDtb().getPersons().get(dao.getDtb().getPersons().size() - 1));
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
        Medicalrecord medicalRecordUpdated = dao.findMedicalrecordByID(id);
        if (medrecEdit.getBirthdate() != null) {
            medicalRecordUpdated.setBirthdate(medrecEdit.getBirthdate());
        }
        if (medrecEdit.getMedications() != null) {
            medicalRecordUpdated.setMedications(medrecEdit.getMedications());
        }
        if (medrecEdit.getAllergies() != null) {
            medicalRecordUpdated.setAllergies(medrecEdit.getAllergies());
        }
        dao.getDtb().getMedicalrecords().set(id, medicalRecordUpdated);
        dao.writer(dao.getDtb());
        return dao.getDtb().getMedicalrecords().get(id);
    }



    //***********Html Methods*************//

    public Medicalrecord findMedicalrecordByID(int id){
        return dao.findMedicalrecordByID(id);
    }
}