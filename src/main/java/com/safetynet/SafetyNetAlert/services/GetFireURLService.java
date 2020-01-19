package com.safetynet.SafetyNetAlert.services;

import com.safetynet.SafetyNetAlert.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.impl.GetURLService;
import java.util.ArrayList;

public class GetFireURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    FirestationDTO firestationDTO = new FirestationDTO();

    private String address;

    public GetFireURLService(String address) {
        this.address = address;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsAgeList = medicalRecordsDTO.getMedicalRecordsData("age");
        ArrayList<String> personsFirstNameList = medicalRecordsDTO.getMedicalRecordsData("firstName");
        ArrayList<String> personsLastNameList = medicalRecordsDTO.getMedicalRecordsData("lastName");
        ArrayList<String> personsPhoneList = personsDTO.getPersonsData("phone");
        ArrayList<String> personsMedicationsList = medicalRecordsDTO.getMedicalRecordsData("medications");
        ArrayList<String> personsAllergiesList = medicalRecordsDTO.getMedicalRecordsData("allergies");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        String stationNumber = firestationDTO.getFirestationNumber(address);
        ArrayList<String> personsList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if (personsAddressList.get(i).equals(address) || address == null) {
                String person = "\n{\"firstName\":\""
                        + personsFirstNameList.get(i)
                        + "\", \"lastName\":\""
                        + personsLastNameList.get(i)
                        + "\", \"phone\":\""
                        + personsPhoneList.get(i)
                        + "\", \"age\":\""
                        + personsAgeList.get(i)
                        + "\", \"medications\":"
                        + personsMedicationsList.get(i)
                        + ", \"allergies\":"
                        + personsAllergiesList.get(i)
                        + "}";
                personsList.add(person);
            }
        }
        String fire =   "{\"persons\":"
                        + stationNumber
                        + ",\"stationNumber\":"
                        + personsList.toString()
                        + "}";
        return fire;
    }
}