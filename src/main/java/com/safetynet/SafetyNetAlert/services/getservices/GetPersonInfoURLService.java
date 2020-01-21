package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetPersonInfoURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    private Map name;

    public GetPersonInfoURLService(Map name) {
        this.name = name;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsAgeList = medicalRecordsDTO.getMedicalRecordsData("age");
        ArrayList<String> personsFirstNameList = medicalRecordsDTO.getMedicalRecordsData("firstName");
        ArrayList<String> personsLastNameList = medicalRecordsDTO.getMedicalRecordsData("lastName");
        ArrayList<String> personsEmailList = personsDTO.getPersonsData("email");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        ArrayList<String> personsMedicationsList = medicalRecordsDTO.getMedicalRecordsData("medications");
        ArrayList<String> personsAllergiesList = medicalRecordsDTO.getMedicalRecordsData("allergies");
        ArrayList<String> personInfoList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if ((personsFirstNameList.get(i).equals(name.get("firstName")) || name.get("firstName")==null)
               &&
               (personsLastNameList.get(i).equals(name.get("lastName")) || name.get("lastName")==null)) {
                String person = "\n{\"firstName\":\""
                        + personsFirstNameList.get(i)
                        + "\", \"lastName\":\""
                        + personsLastNameList.get(i)
                        + "\", \"address\":\""
                        + personsAddressList.get(i)
                        + "\", \"age\":\""
                        + personsAgeList.get(i)
                        + "\", \"email\":\""
                        + personsEmailList.get(i)
                        + "\", \"medications\":"
                        + personsMedicationsList.get(i)
                        + ", \"allergies\":"
                        + personsAllergiesList.get(i)
                        + "}";
                personInfoList.add(person);
            }

        }
        return personInfoList.toString();
    }
}
