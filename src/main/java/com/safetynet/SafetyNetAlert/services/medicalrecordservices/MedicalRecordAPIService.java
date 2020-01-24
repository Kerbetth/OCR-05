package com.safetynet.SafetyNetAlert.services.medicalrecordservices;




import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MedicalRecordAPIService {

    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();


    public String addMedicalRecordPost(Map<String, String> medicalRecordData) {
        Map<String, String> medicalRecord = new HashMap<>();
        medicalRecord.put("firstName", medicalRecordData.get("firstName"));
        medicalRecord.put("lastName", medicalRecordData.get("lastName"));
        medicalRecord.put("birthdate", medicalRecordData.get("birthdate"));
        for (Map.Entry<String, String> value : medicalRecord.entrySet()) {
            if (value.getValue() == null) {
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        medicalRecord.put("medications", medicalRecordData.get("medications"));
        medicalRecord.put("allergies", medicalRecordData.get("allergies"));
        medicalRecordsDTO.addMedicalRecordsData(medicalRecordData);
        return "Ok";
    }

    public void updateMedicalRecordPut(String firstName, String lastName, Map<String, String> map) {
        Integer id = medicalRecordsDTO.getIdByName(firstName, lastName);
        Map<String, String> person = (Map) medicalRecordsDTO.getMedicalrecords().get(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey()) {
                case "birthdate":
                    person.put("address", value.getValue());
                    break;
                case "medications":
                    person.put("city", value.getValue());
                    break;
                case "allergies":
                    person.put("zip", value.getValue());
                    break;
            }
        }
        medicalRecordsDTO.setMedicalRecordData(id, person);
    }


}
