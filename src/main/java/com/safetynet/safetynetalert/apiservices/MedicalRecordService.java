package com.safetynet.safetynetalert.apiservices;


import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.dao.MedicalrecordDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger("MedicalRecordService");
    @Autowired
    private MedicalrecordDao dao;

    public ResponseEntity postMedicalRecord(Medicalrecord medrecData) {
        if (medrecData.getFirstName() != null) {
            if (medrecData.getLastName() != null) {
                if (medrecData.getBirthdate() != null) {
                    dao.addMedicalrecord(medrecData);
                    return ResponseEntity.ok()
                            .body("New Person with medical record added successfully");
                } else {
                    logger.error("Birthdate not specify, process aborted");
                    return ResponseEntity.badRequest()
                            .body("Birthdate not specify, process aborted");
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

    public ResponseEntity putMedicalRecord(String firstNameLastName, Medicalrecord medrecEdit) {
        Integer id = dao.getIdByName(firstNameLastName);
        Medicalrecord medicalRecord = dao.findMedicalrecordByID(id);
        if (medrecEdit.getBirthdate() != null) {
            medicalRecord.setBirthdate(medrecEdit.getBirthdate());
        }
        if (medrecEdit.getMedications() != null) {
            medicalRecord.setMedications(medrecEdit.getMedications());
        }
        if (medrecEdit.getAllergies() != null) {
            medicalRecord.setAllergies(medrecEdit.getAllergies());
        }
        dao.setMedicalrecord(medicalRecord);
        return ResponseEntity.ok()
                .body("Medical record  of Mr/Mrs " + medicalRecord.getFirstName()
                        + " "
                        + medicalRecord.getLastName() + " has been updated");

    }

    public ResponseEntity deletePersonAndMedicalRecord(String firstNameLastName) {
        Integer id = dao.getIdByName(firstNameLastName);
        dao.deleteMedicalRecordAndPersonEntry(id);
        return ResponseEntity.ok()
                .body("Person and his medical record ara succesfully deleted");
    }

}
