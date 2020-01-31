package com.safetynet.SafetyNetAlert.services.getservices;


import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;
import java.util.ArrayList;

public class GetFireURLService implements GetURLService {

    public DTO dTOPersons = new DTO(Datatype.PERSO);
    public DTO dTOMedrec = new DTO(Datatype.MEDREC);

    private String address;

    public GetFireURLService(String address, DTO dTOPersons, DTO dTOMedrec) {
        this.dTOPersons = dTOPersons;
        this.dTOMedrec = dTOMedrec;
        this.address = address;
    }

    @Override
    public String getRequest() {
        ArrayList<Object> personsFirstNameList = dTOPersons.getData(DataEntry.FNAME);
        ArrayList<Object> personsLastNameList = dTOPersons.getData(DataEntry.LNAME);
        ArrayList<Object> personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList<Object> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList<Object> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<Object> personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList<Object> personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        String stationNumber = dTOPersons.getFirestationNumber(address);
        ArrayList<String> personsList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if (personsAddressList.get(i).equals(address) || address == null) {
                String person = "\n{\"" + DataEntry.FNAME.getString() + "\":\""
                        + personsFirstNameList.get(i)
                        + "\", \"" + DataEntry.LNAME.getString() + "\":\""
                        + personsLastNameList.get(i)
                        + "\", \"" + DataEntry.PHONE.getString() + "\":\""
                        + personsPhoneList.get(i)
                        + "\", \"" + DataEntry.AGE.getString() + "\":\""
                        + personsAgeList.get(i)
                        + "\", \"" + DataEntry.MEDIC.getString() + "\":"
                        + personsMedicationsList.get(i)
                        + ", \"" + DataEntry.ALLERGI.getString() + "\":"
                        + personsAllergiesList.get(i)
                        + "}";
                personsList.add(person);
            }
        }
        String fire =   "{\"stationNumber\":"
                        + stationNumber
                        + ",\""+DataEntry.PERSOBYSTATION.getString()+"\":"
                        + personsList.toString()
                        + "}";
        return fire;
    }
}