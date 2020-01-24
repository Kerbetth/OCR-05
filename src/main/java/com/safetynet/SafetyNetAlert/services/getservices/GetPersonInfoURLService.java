package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.*;

public class GetPersonInfoURLService implements GetURLService {

    public DTO dTOPersons;
    public DTO dTOMedrec;
    private Map name;

    public GetPersonInfoURLService(Map name, DTO dTOPersons, DTO dTOMedrec) {
        this.dTOPersons = dTOPersons;
        this.dTOMedrec = dTOMedrec;
        this.name = name;
    }

    @Override
    public String getRequest() {
        ArrayList personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList personsEmailList = dTOPersons.getData(DataEntry.EMAIL);
        ArrayList personsFirstNameList = dTOPersons.getData(DataEntry.FNAME);
        ArrayList personsLastNameList = dTOPersons.getData(DataEntry.LNAME);
        ArrayList personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        ArrayList personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<String> personInfoList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            String fnamelname = (String) name.get(DataEntry.FNAME.getString()) + name.get(DataEntry.LNAME.getString());
            if (((personsFirstNameList.get(i).toString() + personsLastNameList.get(i).toString()).equals(fnamelname)) || fnamelname == null) {
                String person = "\n{\""+ DataEntry.FNAME.getString() +"\":\""
                        + personsFirstNameList.get(i)
                        + "\", \""+ DataEntry.LNAME.getString() +"\":\""
                        + personsLastNameList.get(i)
                        + "\", \""+ DataEntry.ADDRESS.getString() +"\":\""
                        + personsAddressList.get(i)
                        + "\", \""+ DataEntry.AGE.getString() +"\":\""
                        + personsAgeList.get(i)
                        + "\", \""+ DataEntry.EMAIL.getString() +"\":\""
                        + personsEmailList.get(i)
                        + "\", \""+ DataEntry.MEDIC.getString() +"\":"
                        + personsMedicationsList.get(i)
                        + ", \""+ DataEntry.ALLERGI.getString() +"\":"
                        + personsAllergiesList.get(i)
                        + "}";
                personInfoList.add(person);
            }

        }
        return personInfoList.toString();
    }
}
