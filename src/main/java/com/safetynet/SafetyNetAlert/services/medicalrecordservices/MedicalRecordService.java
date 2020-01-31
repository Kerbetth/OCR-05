package com.safetynet.SafetyNetAlert.services.medicalrecordservices;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.MedicalRecord;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordService {

    public DTO dTOMedrec = new DTO(Datatype.MEDREC);
    private ArrayList<MedicalRecord> medicalRecords;

    public MedicalRecordService() {
        loadMedicalRecords();
    }

    public List<MedicalRecord> listAllMedicalRecords() {return medicalRecords;}

    private void loadMedicalRecords() {
        medicalRecords = new ArrayList<>();
        ArrayList<Map> medicalRecordsList = dTOMedrec.getDataTypeContent();
        for (int i = 0; i < medicalRecordsList.size(); i++) {
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setId(i);
            medicalRecord.setFirstName((String)medicalRecordsList.get(i).get(DataEntry.FNAME.getString()));
            medicalRecord.setLastName((String)medicalRecordsList.get(i).get(DataEntry.LNAME.getString()));
            medicalRecord.setBirthDate((String)medicalRecordsList.get(i).get(DataEntry.BIRTHDATE.getString()));
            medicalRecord.setMedications(medicalRecordsList.get(i).get(DataEntry.MEDIC.getString()).toString());
            medicalRecord.setAllergies(medicalRecordsList.get(i).get(DataEntry.ALLERGI.getString()).toString());
            medicalRecords.add(medicalRecord);
        }
    }

    public void addMedicalRecords(MedicalRecord medicalRecord) {
        if (medicalRecord != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToAdd = oMapper.convertValue(medicalRecord, Map.class);
            Integer id = -1;
            personToAdd.remove("id");
            dTOMedrec.addData(personToAdd);
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
            dTOMedrec.setData(id, medicalRecordToEdit);
        } else {
            throw new RuntimeException("Please write infos about the person");
        }
    }

    public MedicalRecord getMedicalRecordByName(String firstNameLastName) {
        Integer id= dTOMedrec.getIdByName(firstNameLastName);
        return medicalRecords.get(id);
    }
    public void removeMedicalRecordData(String firstNameLastName) {
        int id = dTOMedrec.getIdByName(firstNameLastName);
        dTOMedrec.removeData(id);
    }
}
