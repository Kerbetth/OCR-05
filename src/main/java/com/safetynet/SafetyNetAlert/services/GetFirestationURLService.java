package com.safetynet.SafetyNetAlert.services;

import com.safetynet.SafetyNetAlert.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.impl.GetURLService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class GetFirestationURLService implements GetURLService {

    FirestationDTO firestationDTO = new FirestationDTO();
    PersonsDTO personsDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
    private String stationNumber;


    public GetFirestationURLService(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String getRequest() {
        Set<String> personsbyStation = new HashSet<>();
        int children = 0;
        int adults = 0;
        ArrayList<String> personsFirstNameList = personsDTO.getPersonsData("firstName");
        ArrayList<String> personsLastNameList = personsDTO.getPersonsData("lastName");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        ArrayList<String> personsPhoneList = personsDTO.getPersonsData("phone");
        ArrayList<String> personsAgeList = medicalRecordsDTO.getMedicalRecordsData("age");
        Set<String> stationAddresses = firestationDTO.getStationAddresses(stationNumber);
        System.out.println(stationAddresses);
        for (int i = 0; i < personsFirstNameList.size(); i++)
            if (stationAddresses == null || stationAddresses.contains(personsAddressList.get(i))) {
                String person = "\n{\"firstName\":\""
                                + personsFirstNameList.get(i)
                                + "\", \"lastName\":\""
                                + personsLastNameList.get(i)
                                + "\", \"address\":\""
                                + personsAddressList.get(i)
                                + "\", \"phone\":\""
                                + personsPhoneList.get(i)
                                + "\"}";
                personsbyStation.add(person);
                if (Integer.parseInt(personsAgeList.get(i)) < 18) {
                    children++;
                } else {
                    adults++;
                }
            }
        String counting =   "{\"children\":\""
                            + children
                            + "\", \"adults\":\""
                            + adults
                            + "\"}\n";
        String firestation =    "{\"persons by Station\":\n"
                                + personsbyStation
                                + ",\n\"counting\":"
                                + counting
                                + "}";
        return firestation;
    }
}
