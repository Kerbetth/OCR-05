package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.FirestationsDTO;
import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetFirestationURLService implements GetURLService {

    FirestationsDTO firestationsDTO = new FirestationsDTO();
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
        Set<String> stationAddresses = firestationsDTO.getStationAddresses(stationNumber);
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
                if (!personsAgeList.get(i).equals("unknow")) {
                    if (Integer.parseInt(personsAgeList.get(i)) < 18) {
                        children++;
                    } else {
                        adults++;
                    }
                }
            }
        String counting = "{\"children\":\""
                + children
                + "\", \"adults\":\""
                + adults
                + "\"}\n";
        String firestation = "{\"persons by Station\":\n"
                + personsbyStation
                + ",\n\"counting\":"
                + counting
                + "}";
        return firestation;
    }
}
