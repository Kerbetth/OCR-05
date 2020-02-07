package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationDao extends Dao{

    public void addFirestation(Firestation firestation) {
        database.getFirestations().add(firestation);
        jsonWriter.writer(database, jsonPath);
    }

    public void setFirestation(Firestation firestation) {
        database.getFirestations().set(getIdByAddress(firestation.getAddress()), firestation);
        jsonWriter.writer(database, jsonPath);
    }

    public void deleteFirestation(Integer id) {
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
