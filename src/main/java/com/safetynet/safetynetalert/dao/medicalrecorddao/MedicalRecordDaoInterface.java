package com.safetynet.safetynetalert.dao.medicalrecorddao;

import com.safetynet.safetynetalert.domain.Medicalrecord;

import java.util.List;

public interface MedicalRecordDaoInterface {
    List<Medicalrecord> getMedicalrecords();

    Medicalrecord findMedicalrecordByPerson(String name);

    Medicalrecord findMedicalrecordByID(Integer id);

    void updateJson(List<Medicalrecord> medicalrecords);
}
