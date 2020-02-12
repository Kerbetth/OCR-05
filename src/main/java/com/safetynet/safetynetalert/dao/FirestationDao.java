package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationDao extends Dao{

    public Firestation addFirestation(Firestation firestation) {
        database.getFirestations().add(firestation);
        jsonWriter.writer(database, jsonPath);
        return firestation;
    }

    public Firestation setFirestation(String address, Integer stationNumber) {
        Integer id = getIdByAddress(address);
        List<Firestation> firestations= database.getFirestations();
        Firestation firestationToUpdate = firestations.get(id);
        firestationToUpdate.setStation(stationNumber);
        firestations.set(id, firestationToUpdate);
        database.setFirestations(firestations);
        jsonWriter.writer(database, jsonPath);
        return firestationToUpdate;
    }

    public void deleteFirestation(String address) {
        Integer id = getIdByAddress(address);
        database.getFirestations().remove(database.getFirestations().get(id));
        jsonWriter.writer(database, jsonPath);
    }

    public Integer getIdByAddress(String address) {
        Integer id = 0;
        List<Firestation> firestations = database.getFirestations();
        for (Firestation firestation :firestations) {
            if ((firestation.getAddress()).equals(address)){
                break;
            } else id++;
        }
        return id;
    }

}
