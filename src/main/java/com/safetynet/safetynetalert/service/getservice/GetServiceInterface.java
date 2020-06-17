package com.safetynet.safetynetalert.service.getservice;

import com.safetynet.safetynetalert.domain.*;

import java.util.List;
import java.util.Set;

/**
 * this interface contains all the specified Get methods corresponding to the endpoints wanted
 */

public interface GetServiceInterface {

    FirestationGet firestation(Integer stationNumber);

    List<Child> childAlert(String address);

    Set<String> phoneAlert(Integer stationNumber);

    Fire fire(String address);

    List<HouseHold> floodstations(String numbersList);

    List<PersonInfo> personInfo(String firstName, String lastName);

    List<String> communityEmail(String city);
}
