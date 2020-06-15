package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DaoMedicalRecord {

    /**
     * the Dao method deploy the content of the jsonfile in a database composed with corresponding object
     * the @Value give the name of the file, which is variable for test purposes
     * writer method is the one who edited the jsonfile in order to registered the modifications
     * All the find methods get all sort of data from the constructed database
     *
     */

    @Autowired
    private JsonReaderWriter jsonReaderWriter;

    private List<Medicalrecord> medicalrecords;
    private static final Logger logger = LogManager.getLogger("DaoMedicalRecord");

    public List<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }



    public DaoMedicalRecord() {
        medicalrecords = jsonReaderWriter.getDtb().getMedicalrecords();
    }

    public Medicalrecord findMedicalrecordByPerson(String name) {
        Optional<Medicalrecord> medicalrecord =
                medicalrecords
                        .stream()
                        .filter(currentMed -> Objects.equals(name, currentMed.getFirstName() + currentMed.getLastName()))
                        .findFirst();
        if (medicalrecord.isEmpty()) {
            throw new NoEntryException(name);
        }
        return medicalrecord.get();
    }

    public Medicalrecord findMedicalrecordByID(Integer id) {
        return medicalrecords.get(id);
    }

    public void updateJson(List<Medicalrecord> medicalrecords){
        jsonReaderWriter.updateMedicalRecords(medicalrecords);
    }


}