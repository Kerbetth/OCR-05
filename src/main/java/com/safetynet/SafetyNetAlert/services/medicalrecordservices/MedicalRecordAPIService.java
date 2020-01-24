package com.safetynet.SafetyNetAlert.services.medicalrecordservices;




import com.safetynet.SafetyNetAlert.services.APIServices;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MedicalRecordAPIService implements APIServices {

    public DTO dTOMedrec = new DTO(Datatype.MEDREC);


    public String postMethod(Map<String, String> medrecData) {
        Map<String, String> medicalRecord = new HashMap<>();
        medicalRecord.put(DataEntry.FNAME.getString(), medrecData.get(DataEntry.FNAME.getString()));
        medicalRecord.put(DataEntry.LNAME.getString(), medrecData.get(DataEntry.LNAME.getString()));
        medicalRecord.put(DataEntry.BIRTHDATE.getString(), medrecData.get(DataEntry.BIRTHDATE.getString()));
        for (Map.Entry<String, String> value : medicalRecord.entrySet()) {
            if (value.getValue() == null) {
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        medicalRecord.put(DataEntry.MEDIC.getString(), medrecData.get(DataEntry.MEDIC.getString()));
        medicalRecord.put(DataEntry.ALLERGI.getString(), medrecData.get(DataEntry.ALLERGI.getString()));
        dTOMedrec.addData(medicalRecord);
        return "Ok";
    }

    public void putMethod(String firstNameLastName, Map<String, String> map) {
        Integer id = dTOMedrec.getIdByName(firstNameLastName);
        Map<String, String> medicalRecord = (Map) dTOMedrec.getDataTypeContentwithID(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey()) {
                case "birthdate":
                    medicalRecord.put(DataEntry.BIRTHDATE.getString(), value.getValue());
                    break;
                case "medications":
                    medicalRecord.put(DataEntry.MEDIC.getString(), value.getValue());
                    break;
                case "allergies":
                    medicalRecord.put(DataEntry.ALLERGI.getString(), value.getValue());
                    break;
            }
        }
        dTOMedrec.setData(id, medicalRecord);
    }

    public void deleteMethod(String firstNameLastName) {
        Integer id = dTOMedrec.getIdByName(firstNameLastName);
        dTOMedrec.removeData(id);
    }

}
