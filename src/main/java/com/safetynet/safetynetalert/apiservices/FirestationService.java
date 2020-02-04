package com.safetynet.safetynetalert.apiservices;


import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.dao.FirestationDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FirestationService {

    private static final Logger logger = LogManager.getLogger("PostPutDeleteService");

    @Autowired
    private FirestationDao dao;

    public ResponseEntity postFirestation(String address, Integer stationNumber) {
        Firestation firestation = new Firestation();
        firestation.setAddress(address);
        firestation.setStation(stationNumber);
        dao.addFirestation(firestation);
        return ResponseEntity.ok()
                .body("A new fire station at the address:" + address + " has been added");
    }

    public ResponseEntity putFirestation(String address, Integer stationNumber) {
        Firestation firestation = dao.findFirestationByAddress(address);
        firestation.setStation(stationNumber);
        dao.setFirestation(firestation);
        return ResponseEntity.ok()
                .body("Fire station at the address:" + address + " has been updated");
    }

    public ResponseEntity deletetFirestation(String address) {
        Integer id = dao.getIdByAddress(address);
        dao.deleteFirestation(id);
        return ResponseEntity.ok()
                .body("Fire station at the address:" + address + " has been deleted");
    }
}
