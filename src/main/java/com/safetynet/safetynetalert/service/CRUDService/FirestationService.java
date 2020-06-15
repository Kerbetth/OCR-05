package com.safetynet.safetynetalert.service.CRUDService;

import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService implements CRUDService {

    private Logger logger = LogManager.getLogger("FirestationService");

    @Autowired
    private FirestationDao firestationDao;

    /**
     * classic CRUD method for Firestations management, get, add, set, and delete
     *
     */

    public Object add(Object object) {
        Firestation firestation = (Firestation) object;
        List<Firestation> firestations = firestationDao.getFirestations();
        firestations.add( firestation);
        firestationDao.updateJson(firestations);
        logger.info("A new Firestation Post request with the address "+ firestation.getAddress() +" has been added.");
        return firestation;
    }

    public Firestation set(String address, Object stationNumber) {
        Integer id = firestationDao.getIdByAddress(address);
        List<Firestation> firestations= firestationDao.getFirestations();
        Firestation firestationToUpdate = firestations.get(id);
        firestationToUpdate.setStation((Integer)stationNumber);
        firestations.set(id, firestationToUpdate);
        firestationDao.updateJson(firestations);
        logger.info("A new Firestation Put request with the address "+ address +" has been done.");
        return firestationToUpdate;
    }

    public void delete(String address) {
        Integer id = firestationDao.getIdByAddress(address);
        List<Firestation> firestations = firestationDao.getFirestations();
        firestations.remove(firestations.get(id));
        firestationDao.updateJson(firestations);
        logger.info("A Firestation Delete request has been sent for the firestation with the address "+ address +" which has been deleted.");
    }




    //***********Html Methods*************//

    public Firestation findFirestationByAddress(String address){
        return firestationDao.findFirestationByAddress(address);
    }
}
