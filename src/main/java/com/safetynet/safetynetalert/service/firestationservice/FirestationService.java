package com.safetynet.safetynetalert.service.firestationservice;

import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    private Logger logger = LogManager.getLogger("FirestationService");

    @Autowired
    private JsonReaderWriter dao;

    /**
     * classic CRUD method for Firestations management, get, add, set, and delete
     *
     */

    public Firestation addFirestation(Firestation firestation) {
        dao.getDtb().getFirestations().add(firestation);
        dao.writer(dao.getDtb());
        logger.info("A new Firestation Post request with the address "+ firestation.getAddress() +" has been added.");
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
        logger.info("A new Firestation Put request with the address "+ address +" has been done.");
        return firestationToUpdate;
    }

    public void deleteFirestation(String address) {
        Integer id = getIdByAddress(address);
        dao.getDtb().getFirestations().remove(dao.getDtb().getFirestations().get(id));
        dao.writer(dao.getDtb());
        logger.info("A Firestation Delete request has been sent for the firestation with the address "+ address +" which has been deleted.");
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

    public Database getDtb() {
        return dao.getDtb();
    }

    //***********Html Methods*************//

    public Firestation findFirestationByAddress(String address){
        return dao.findFirestationByAddress(address);
    }
}
