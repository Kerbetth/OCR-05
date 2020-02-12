package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.apiservices.firestationservice.FirestationService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class FirestationControllers {


    @Autowired
    private FirestationService dao;


    @PostMapping(value = "/firestation")
    public Firestation addFirestationPost(@RequestBody Firestation firestationData){
        return dao.addFirestation(firestationData);
    }

    @PutMapping(value = "/firestation/{address}")
    public Firestation setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        return dao.setFirestation(address, stationNumber);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void removeFirestationDelete(@PathVariable String address) {
        dao.deleteFirestation(address);
    }
}
