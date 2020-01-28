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

    public GetFireURLService(String address) {
        this.address = address;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<String> personsFirstNameList = dTOMedrec.getData(DataEntry.FNAME);
        ArrayList<String> personsLastNameList = dTOMedrec.getData(DataEntry.LNAME);
        ArrayList<String> personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList<String> personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList<String> personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        String stationNumber = dTOMedrec.getFirestationNumber(address);
        ArrayList<String> personsList = new ArrayList<>();
        for (int i = 0; i < personsFirstNameList.size(); i++) {
            if (personsAddressList.get(i).equals(address) || address == null) {
                String person = "\n{\"firstName\":\""
                        + personsFirstNameList.get(i)
                        + "\", \"lastName\":\""
                        + personsLastNameList.get(i)
                        + "\", \"phone\":\""
                        + personsPhoneList.get(i)
                        + "\", \"age\":\""
                        + personsAgeList.get(i)
                        + "\", \"medications\":"
                        + personsMedicationsList.get(i)
                        + ", \"allergies\":"
                        + personsAllergiesList.get(i)
                        + "}";
                personsList.add(person);
            }
        }
        String fire =   "{\"persons\":"
                        + stationNumber
                        + ",\"stationNumber\":"
                        + personsList.toString()
                        + "}";
        return fire;
    }
}