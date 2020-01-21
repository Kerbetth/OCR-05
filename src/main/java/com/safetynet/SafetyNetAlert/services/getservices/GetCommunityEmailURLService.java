package com.safetynet.SafetyNetAlert.services.getservices;

import com.safetynet.SafetyNetAlert.services.dto.FirestationDTO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GetCommunityEmailURLService implements GetURLService {

    PersonsDTO personsDTO = new PersonsDTO();
    FirestationDTO firestationDTO = new FirestationDTO();
    private String city;

    public GetCommunityEmailURLService(String city){
        this.city = city;
    }

    @Override
    public String getRequest() {
        ArrayList<String> personsCityList = personsDTO.getPersonsData("city");
        ArrayList<String> personsEmailList = personsDTO.getPersonsData("email");
        Set<String> emailList = new HashSet<>();
        for (int i = 0; i < personsCityList.size(); i++)  {
            if (personsCityList.get(i).equals(city) || city == null) {
                emailList.add("\"" + (personsEmailList.get(i)) + "\"");
            }
        }
        return emailList.toString();
    }



}
