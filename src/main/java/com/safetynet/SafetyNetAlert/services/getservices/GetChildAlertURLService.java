package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.enumerations.DataDefaultValue;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetChildAlertURLService implements GetURLService {

    private String address;
    public DTO dTOPersons;
    public DTO dTOMedrec;

    public GetChildAlertURLService(String address, DTO dTOPersons, DTO dTOMedrec) {
        this.dTOPersons = dTOPersons;
        this.dTOMedrec = dTOMedrec;
        this.address = address;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<String> personsFirstNameList = dTOMedrec.getData(DataEntry.FNAME);
        ArrayList<String> personsLastNameList = dTOMedrec.getData(DataEntry.LNAME);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList<String> personsList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if (!personsAgeList.get(i).equals(DataDefaultValue.UNKNOW.getString())) {
                if (Integer.parseInt(personsAgeList.get(i)) < 18) {
                    if (personsAddressList.get(i).equals(address) || address == null) {
                        ArrayList<String> householdMemberList = new ArrayList<>();
                        for (int j = 0; j < personsFirstNameList.size(); j++) {
                            if (personsAddressList.get(i).equals(personsAddressList.get(j)) && !personsFirstNameList.get(i).equals(personsFirstNameList.get(j))) {
                                householdMemberList.add("\"" + personsFirstNameList.get(j) + " " + personsLastNameList.get(j) + "\"");
                            }
                        }
                        String child = "{\"" + DataEntry.FNAME.getString() + "\":\""
                                + personsFirstNameList.get(i)
                                + "\", \"" + DataEntry.LNAME.getString() + "\":\""
                                + personsLastNameList.get(i)
                                + "\", \"" + DataEntry.AGE.getString() + "\":\""
                                + personsAgeList.get(i)
                                + "\", \"" + DataEntry.HOUSEMEMBERS.getString() + "\":"
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
