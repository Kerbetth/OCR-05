package com.safetynet.safetynetalert.apiservices.firestationservice;

import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    @Autowired
    private Dao dao;

    public Firestation addFirestation(Firestation firestation) {
        dao.getDtb().getFirestations().add(firestation);
        dao.writer(dao.getDtb());
        return firestation;
    }

    public Firestation setFirestation(String address, Integer stationNumber) {
        Integer id = getIdByAddress(address);
        List<Firestation> firestations= dao.getDtb().getFirestations();
        Firestation firestationToUpdate = firestations.get(id);
        firestationToUpdate.setStation(stationNumber);
        firestations.set(id, firestationToUpdate);
        dao.getDtb().setFirestations(firestations);
        dao.writer(dao.getDtb());
        return firestationToUpdate;
    }

    public void deleteFirestation(String address) {
        Integer id = getIdByAddress(address);
        dao.getDtb().getFirestations().remove(dao.getDtb().getFirestations().get(id));
        dao.writer(dao.getDtb());
    }

    public Integer getIdByAddress(String address) {
        Integer id = 0;
        List<Firestation> firestations = dao.getDtb().getFirestations();
        for (Firestation firestation :firestations) {
            if ((firestation.getAddress()).equals(address)){
                break;
            } else id++;
        }
        return id;
    }

    public Dao getDao() {
        return dao;
    }

    public Database getDtb() {
        return dao.getDtb();
    }
    //***********Html Methods*************//

    public Firestation findFirestationByAddress(String address){
        return dao.findFirestationByAddress(address);
    }
}
