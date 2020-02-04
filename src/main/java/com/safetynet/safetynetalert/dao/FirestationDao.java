package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class FirestationDao extends Dao{

    private static final Logger logger = LogManager.getLogger("FirestationDao");

    public FirestationDao() throws IOException {
    }


    public void addFirestation(Firestation firestation) {
        database.getFirestations().add(firestation);
        daoWriter(database);
    }

    public void setFirestation(Firestation firestation) {
        database.getFirestations().set(getIdByAddress(firestation.getAddress()), firestation);
        daoWriter(database);
    }

    public void deleteFirestation(Integer id) {
        database.getFirestations().remove(database.getFirestations().get(id));
        daoWriter(database);
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
