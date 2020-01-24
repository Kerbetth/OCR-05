package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.FirestationsDTO;
import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.ArrayList;
import java.util.Set;

public class GetFloodStationsURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    FirestationsDTO firestationsDTO = new FirestationsDTO();

    private String stationNumbers;

    public GetFloodStationsURLService(String stationNumbers) {
        this.stationNumbers = stationNumbers;
    }

    @Override
    public String getRequest() {
        Set<String> houseHoldAddresses = firestationsDTO.getStationAddresses(stationNumbers);
        ArrayList<String> personsAgeList = medicalRecordsDTO.getMedicalRecordsData("age");
        ArrayList<String> personsFirstNameList = medicalRecordsDTO.getMedicalRecordsData("firstName");
        ArrayList<String> personsLastNameList = medicalRecordsDTO.getMedicalRecordsData("lastName");
        ArrayList<String> personsPhoneList = personsDTO.getPersonsData("phone");
        ArrayList<String> personsMedicationsList = medicalRecordsDTO.getMedicalRecordsData("medications");
        ArrayList<String> personsAllergiesList = medicalRecordsDTO.getMedicalRecordsData("allergies");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        String floodStations;
        if (stationNumbers == null) floodStations = "{\"allStations\":{";
        else floodStations = "{\"station " + stationNumbers +"\":{";
        for (String houseHoldAddress : houseHoldAddresses) {
            ArrayList<String> personsbyHoushold = new ArrayList<>();
            for (int i = 0; i < personsFirstNameList.size(); i++) {
                if (personsAddressList.get(i).equals(houseHoldAddress)) {
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
                    personsbyHoushold.add(person);
                }
            }
            floodStations += "\n\""
                    + houseHoldAddress
                    + "\":"
                    + personsbyHoushold
                    + ",";
        }
        floodStations = (floodStations.substring(0, floodStations.length()-1)) + "}}";
        return floodStations;
    }
}