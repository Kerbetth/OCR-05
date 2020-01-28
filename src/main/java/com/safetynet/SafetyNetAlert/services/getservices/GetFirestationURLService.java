package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataDefaultValue;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetFirestationURLService implements GetURLService {

    public DTO dTOPersons;
    public DTO dTOMedrec;
    public DTO dTOFirestation;
    private String stationNumber;


    public GetFirestationURLService(String stationNumber, DTO dTOPersons, DTO dTOMedrec, DTO dTOFirestation) {
        this.dTOPersons = dTOPersons;
        this.dTOMedrec = dTOMedrec;
        this.dTOFirestation = dTOFirestation;
        this.stationNumber = stationNumber;
    }

    @Override
    public String getRequest() {
        Set<String> personsbyStation = new HashSet<>();
        int children = 0;
        int adults = 0;
        ArrayList<String> personsFirstNameList = dTOPersons.getData(DataEntry.FNAME);
        ArrayList<String> personsLastNameList = dTOPersons.getData(DataEntry.LNAME);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList<String> personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList<String> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        Set<String> stationAddresses = dTOFirestation.getStationAddresses(stationNumber);
        for (int i = 0; i < personsFirstNameList.size(); i++)
            if (stationAddresses == null || stationAddresses.contains(personsAddressList.get(i))) {
                String person = "\n{\"" + DataEntry.FNAME.getString() + " \":\""
                        + personsFirstNameList.get(i)
                        + "\", \""+ DataEntry.LNAME.getString() + "\":\""
                        + personsLastNameList.get(i)
                        + "\", \""+ DataEntry.ADDRESS.getString() + "\":\""
                        + personsAddressList.get(i)
                        + "\", \""+ DataEntry.PHONE.getString() + "\":\""
                        + personsPhoneList.get(i)
                        + "\"}";
                personsbyStation.add(person);
                if (!personsAgeList.get(i).equals(DataDefaultValue.UNKNOW.getString())) {
                    if (Integer.parseInt(personsAgeList.get(i)) < 18) {
                        children++;
                    } else {
                        adults++;
                    }
                }
            }
        String counting = "{\""+DataEntry.CHILDREN.getString()+"\":\""
                + children
                + "\", \""+DataEntry.ADULTS.getString()+"\":\""
                + adults
                + "\"}\n";
        String firestation = "{\""+DataEntry.PERSOBYSTATION.getString()+"\":\n"
                + personsbyStation
                + ",\n\""+DataEntry.COUNT.getString()+"\":"
                + counting
                + "}";
        return firestation;
    }
}
