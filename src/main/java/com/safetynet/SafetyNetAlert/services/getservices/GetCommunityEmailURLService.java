package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GetCommunityEmailURLService implements GetURLService {

    DTO dTOPersons = new DTO(Datatype.PERSO);
    private String city;

    public GetCommunityEmailURLService(String city){
        this.city = city;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsCityList = dTOPersons.getData(DataEntry.CITY);
        ArrayList<String> personsEmailList = dTOPersons.getData(DataEntry.EMAIL);
        Set<String> emailList = new HashSet<>();
        for (int i = 0; i < personsCityList.size(); i++)  {
            if (personsCityList.get(i).equals(city) || city == null) {
                emailList.add("\"" + (personsEmailList.get(i)) + "\"");
            }
        }
        return emailList.toString();
    }



}
