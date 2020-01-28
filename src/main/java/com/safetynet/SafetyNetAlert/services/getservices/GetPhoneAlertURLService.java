package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.dto.DTOFactory;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetPhoneAlertURLService implements GetURLService {

    public DTO dTOPersons;
    private Integer firestation;

    public GetPhoneAlertURLService(Integer firestation, DTO dTOPersons){
        this.firestation = firestation;
        this.dTOPersons = dTOPersons;
    }

    @Override
    public String getRequest() {
        String stationAddress = dTOPersons.getFirestationAddress(firestation);
        ArrayList<String> personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        Set<String> phoneNumbersList = new HashSet<>();
        for (int i = 0; i < personsPhoneList.size(); i++)  {
            if (personsAddressList.get(i).equals(stationAddress) || stationAddress == null) {
                phoneNumbersList.add("\"" + (personsPhoneList.get(i)) + "\"");
            }
        }
        String phoneNumbers = "{\"" + DataEntry.PHONEALERT.getString() + "\":" + phoneNumbersList+ "}";
        return phoneNumbers;
    }
}
