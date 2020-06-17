package com.safetynet.safetynetalert.daodto.firestationdao;

import com.safetynet.safetynetalert.domain.Firestation;

import java.util.List;


/**
 * the get method retrieve all Firestations belonging to the JSONfile
 * the find method retrieve some Firestations in order to certain conditions
 * the update method send the edited data in order to be writing in the JSONfile
 */

public interface FirestationDaoInterface {

    List<Firestation> getFirestations();

    Firestation findFirestationByAddress(String address);

    List<Firestation> findFirestationsByNumber(String stationNumbers);

    void updateJson(List<Firestation> firestations);


    Integer getIdByAddress(String address);
}
