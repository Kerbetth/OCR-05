package com.safetynet.safetynetalert.daodto.firestationdao;

import com.safetynet.safetynetalert.domain.Firestation;

import java.util.List;

public interface FirestationDaoInterface {

    List<Firestation> getFirestations();

    Firestation findFirestationByAddress(String address);

    List<Firestation> findFirestationsByNumber(String stationNumbers);

    Integer getIdByAddress(String address);

    void updateJson(List<Firestation> firestations);
}
