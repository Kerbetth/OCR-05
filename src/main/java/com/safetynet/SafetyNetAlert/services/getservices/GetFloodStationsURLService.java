package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.ArrayList;
import java.util.Set;

public class GetFloodStationsURLService implements GetURLService {

    public DTO dTOPersons = new DTO(Datatype.PERSO);
    public DTO dTOFirestation = new DTO(Datatype.FSTATION);
    public DTO dTOMedrec = new DTO(Datatype.MEDREC);
    private String stationNumbers;

    public GetFloodStationsURLService(String stationNumbers) {
        this.stationNumbers = stationNumbers;
    }

    @Override
    public String getRequest() {
        Set<String> houseHoldAddresses = dTOFirestation.getStationAddresses(stationNumbers);
        ArrayList<String> personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList<String> personsFirstNameList = dTOMedrec.getData(DataEntry.FNAME);
        ArrayList<String> personsLastNameList = dTOMedrec.getData(DataEntry.LNAME);
        ArrayList<String> personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList<String> personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList<String> personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        ArrayList<String> personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        String floodStations;
        if (stationNumbers == null) floodStations = "{\"allStations\":{";
        else floodStations = "{\"station " + stationNumbers +"\":{";
        for (String houseHoldAddress : houseHoldAddresses) {
            ArrayList<String> personsbyHoushold = new ArrayList<>();
            for (int i = 0; i < personsFirstNameList.size(); i++) {
                if (personsAddressList.get(i).equals(houseHoldAddress)) {
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
                    personsbyHoushold.add(person);
                }
            }
            floodStations += "\n\""
                    + houseHoldAddress
                    + "\":"
                    + personsbyHoushold
                    + ",";
        }
        floodStations = (floodStations.substring(0, floodStations.length()-1)) + "}}";
        return floodStations;
    }
}