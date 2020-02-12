package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class FirestationControllers {


    @Autowired
    private FirestationDao dao;


    @PostMapping(value = "/firestation")
    public Firestation addFirestationPost(@RequestBody Firestation firestationData){
        Firestation firestation = dao.addFirestation(firestationData);
        return firestation;
    }

    @PutMapping(value = "/firestation/{address}")
    public Firestation setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        Firestation firestation = dao.setFirestation(address, stationNumber);
        return firestation;
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void removeFirestationDelete(@PathVariable String address) {
        dao.deleteFirestation(address);
    }
}
