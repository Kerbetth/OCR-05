package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetChildAlertURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    private String address;

    public GetChildAlertURLService(String address) {
        this.address = address;
    }

    @Override
    public String getRequest() {
        //address = DecodeURLService.decodeURL(address);
        ArrayList<String> personsAgeList = medicalRecordsDTO.getMedicalRecordsData("age");
        ArrayList<String> personsFirstNameList = medicalRecordsDTO.getMedicalRecordsData("firstName");
        ArrayList<String> personsLastNameList = medicalRecordsDTO.getMedicalRecordsData("lastName");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        ArrayList<String> personsList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if (!personsAgeList.get(i).equals("unknow")) {
                if (Integer.parseInt(personsAgeList.get(i)) < 18) {
                    if (personsAddressList.get(i).equals(address) || address == null) {
                        ArrayList<String> householdMemberList = new ArrayList<>();
                        for (int j = 0; j < personsFirstNameList.size(); j++) {
                            if (personsAddressList.get(i).equals(personsAddressList.get(j)) && !personsFirstNameList.get(i).equals(personsFirstNameList.get(j))) {
                                householdMemberList.add("\"" + personsFirstNameList.get(j) + " " + personsLastNameList.get(j) + "\"");
                            }
                        }
                        String child = "{\"firstName\":\""
                                + personsFirstNameList.get(i)
                                + "\", \"lastName\":\""
                                + personsLastNameList.get(i)
                                + "\", \"age\":\""
                                + personsAgeList.get(i)
                                + "\", \"householdMemeberList\":"
                                + householdMemberList.toString()
                                + "}";
                        personsList.add(child);
                    }
                }
            }
        }
        return personsList.toString();
    }
}
