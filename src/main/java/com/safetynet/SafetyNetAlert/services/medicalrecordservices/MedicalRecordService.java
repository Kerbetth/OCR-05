package com.safetynet.SafetyNetAlert.services.medicalrecordservices;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.domain.MedicalRecord;
import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordService {

    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    private ArrayList<MedicalRecord> medicalRecords;

    public MedicalRecordService() {
        loadMedicalRecords();
    }

    public List<MedicalRecord> listAllMedicalRecords() {return medicalRecords;}

    private void loadMedicalRecords() {
        medicalRecords = new ArrayList<>();
        ArrayList<Map<String, Object>> medicalRecordsList = medicalRecordsDTO.getMedicalrecords();
        for (int i = 0; i < medicalRecordsList.size(); i++) {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setId(i);
            medicalRecord.setFirstName((String)medicalRecordsList.get(i).get("firstName"));
            medicalRecord.setLastName((String)medicalRecordsList.get(i).get("lastName"));
            medicalRecord.setBirthDate((String)medicalRecordsList.get(i).get("birthdate"));
            medicalRecord.setMedications(medicalRecordsList.get(i).get("medications").toString());
            medicalRecord.setAllergies(medicalRecordsList.get(i).get("allergies").toString());
            medicalRecords.add(medicalRecord);
        }
    }

    public void addMedicalRecords(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToAdd = oMapper.convertValue(medicalRecord, Map.class);
            Integer id = -1;
            personToAdd.remove("id");
            medicalRecordsDTO.addMedicalRecordsData(personToAdd);
        } else {
            throw new RuntimeException("Please write infos about the person");
        }

    }


    public void updateMedicalRecords(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> medicalRecordToEdit = oMapper.convertValue(medicalRecord, Map.class);
            Integer id = (Integer) medicalRecordToEdit.get("id");
            medicalRecordToEdit.remove("id");
            medicalRecordsDTO.setMedicalRecordData(id, medicalRecordToEdit);
        } else {
            throw new RuntimeException("Please write infos about the person");
        }
    }

    public MedicalRecord getMedicalRecordByName(String firstName, String lastName) {
        Integer id= medicalRecordsDTO.getIdByName(firstName, lastName);
        return medicalRecords.get(id);
    }

}
