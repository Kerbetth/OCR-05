package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetPhoneAlertURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    FirestationDTO firestationDTO = new FirestationDTO();
    private Integer firestation;

    public GetPhoneAlertURLService(Integer firestation){
        this.firestation = firestation;
    }

    @Override
    public String getRequest() {
        String stationAddress = firestationDTO.getFirestationAddress(firestation);
        ArrayList<String> personsPhoneList = personsDTO.getPersonsData("phone");
        ArrayList<String> personsAddressList = personsDTO.getPersonsData("address");
        Set<String> phoneNumbersList = new HashSet<>();
        for (int i = 0; i < personsPhoneList.size(); i++)  {
            if (personsAddressList.get(i).equals(stationAddress) || stationAddress == null) {
                phoneNumbersList.add("\"" + (personsPhoneList.get(i)) + "\"");
            }
        }
        return phoneNumbersList.toString();
    }



}
