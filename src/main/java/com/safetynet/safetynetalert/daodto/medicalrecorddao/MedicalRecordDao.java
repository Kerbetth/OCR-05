package com.safetynet.safetynetalert.daodto.medicalrecorddao;

import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriterInterface;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MedicalRecordDao implements MedicalRecordDaoInterface {

    @Autowired
    private JsonReaderWriterInterface jsonReaderWriter;

    private static final Logger logger = LogManager.getLogger("DaoMedicalRecord");

    @Override
    public List<Medicalrecord> getMedicalrecords() {
        return jsonReaderWriter.getDtb().getMedicalrecords();
    }

    @Override
    public Medicalrecord findMedicalrecordByPerson(String name) {
        Optional<Medicalrecord> medicalrecord =
                getMedicalrecords()
                        .stream()
                        .filter(currentMed -> Objects.equals(name, currentMed.getFirstName() + currentMed.getLastName()))
                        .findFirst();
        if (medicalrecord.isEmpty()) {
            throw new NoEntryException(name);
        }
        return medicalrecord.get();
    }

    @Override
    public Medicalrecord findMedicalrecordByID(Integer id) {
        return getMedicalrecords().get(id);
    }

    @Override
    public void updateJson(List<Medicalrecord> medicalrecords){
        jsonReaderWriter.updateMedicalRecords(medicalrecords);
    }


}
