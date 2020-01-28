package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetPersonInfoURLService implements GetURLService {

    DTO dTOPersons = new DTO(Datatype.PERSO);
    DTO dTOMedrec = new DTO(Datatype.MEDREC);
    private Map name;

    public GetPersonInfoURLService(Map name) {
        this.name = name;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<String> personsFirstNameList = dTOMedrec.getData(DataEntry.FNAME);
        ArrayList<String> personsLastNameList = dTOMedrec.getData(DataEntry.LNAME);
        ArrayList<String> personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList<String> personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList<String> personsEmailList = dTOPersons.getData(DataEntry.EMAIL);
        ArrayList<String> personInfoList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            String fnamelname = (String) name.get(DataEntry.FNAME)+name.get(DataEntry.LNAME);
            if (((personsFirstNameList.get(i)+personsLastNameList.get(i)).equals(name.get(fnamelname))) || name.get(fnamelname)==null) {
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
