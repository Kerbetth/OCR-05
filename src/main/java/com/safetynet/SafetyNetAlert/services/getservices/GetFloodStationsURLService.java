package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.ArrayList;
import java.util.Set;

public class GetFloodStationsURLService implements GetURLService {

    public DTO dTOPersons;
    public DTO dTOFirestation = new DTO(Datatype.FSTATION);
    public DTO dTOMedrec;
    private String stationNumbers;

    public GetFloodStationsURLService(String stationNumbers, DTO dTOPersons, DTO dTOMedrec, DTO dTOFirestation) {
        this.dTOPersons = dTOPersons;
        this.dTOFirestation = dTOFirestation;
        this.dTOMedrec = dTOMedrec;
        this.stationNumbers = stationNumbers;
    }

    @Override
    public String getRequest() {
        Set<String> houseHoldAddresses = dTOFirestation.getStationAddresses(stationNumbers);
        ArrayList personsFirstNameList = dTOPersons.getData(DataEntry.FNAME);
        ArrayList personsLastNameList = dTOPersons.getData(DataEntry.LNAME);
        ArrayList personsAgeList = dTOMedrec.getData(DataEntry.AGE);
        ArrayList personsAddressList = dTOPersons.getData(DataEntry.ADDRESS);
        ArrayList personsPhoneList = dTOPersons.getData(DataEntry.PHONE);
        ArrayList personsMedicationsList = dTOMedrec.getData(DataEntry.MEDIC);
        ArrayList personsAllergiesList = dTOMedrec.getData(DataEntry.ALLERGI);
        String floodStations;
        if (stationNumbers == null) floodStations = "{\"allStations\":{";
        else floodStations = "{\"" + DataEntry.STATION.getString() + " " + stationNumbers + "\":{";
        for (String houseHoldAddress : houseHoldAddresses) {
            ArrayList<String> personsbyHoushold = new ArrayList<>();
            for (int i = 0; i < personsFirstNameList.size(); i++) {
                if (personsAddressList.get(i).equals(houseHoldAddress)) {
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
                    personsbyHoushold.add(person);
                }
            }
            floodStations += "\n\""
                    + houseHoldAddress
                    + "\":"
                    + personsbyHoushold
                    + ",";
        }
        floodStations = (floodStations.substring(0, floodStations.length() - 1)) + "}}";
        return floodStations;
    }
}