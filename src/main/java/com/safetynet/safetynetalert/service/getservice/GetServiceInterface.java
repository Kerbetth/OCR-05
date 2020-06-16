package com.safetynet.safetynetalert.service.getservice;

import com.safetynet.safetynetalert.domain.Child;
import com.safetynet.safetynetalert.domain.HouseHold;
import com.safetynet.safetynetalert.domain.PersonInfo;

import java.util.List;
import java.util.Set;

public interface GetServiceInterface {

    List<Object> firestation(Integer stationNumber);

    List<Child> childAlert(String address);

    Set<String> phoneAlert(Integer stationNumber);

    List<Object> fire(String address);

    List<HouseHold> floodstations(String numbersList);

    List<PersonInfo> personInfo(String firstName, String lastName);

    List<String> communityEmail(String city);
}
