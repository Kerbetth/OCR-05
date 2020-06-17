package com.safetynet.safetynetalert.daodto.medicalrecorddao;

import com.safetynet.safetynetalert.domain.Medicalrecord;

import java.util.List;

/**
 * the get method retrieve all MedicalRecords belonging to the JSONfile
 * the find method retrieve some MedicalRecords in order to certain conditions
 * the update method send the edited data in order to be writing in the JSONfile
 */

public interface MedicalRecordDaoInterface {
    List<Medicalrecord> getMedicalrecords();

    Medicalrecord findMedicalrecordByPerson(String name);

    Medicalrecord findMedicalrecordByID(Integer id);

    void updateJson(List<Medicalrecord> medicalrecords);
}
