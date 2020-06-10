package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.firestationservice.FirestationService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class FirestationControllers {


    @Autowired
    private FirestationService firestationService;

    /**
     * Add put and delete for firestation
     */

    @PostMapping(value = "/firestation")
    public Firestation addFirestationPost(@RequestBody Firestation firestationData){
        return firestationService.addFirestation(firestationData);
    }

    @PutMapping(value = "/firestation/{address}")
    public Firestation setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        return firestationService.setFirestation(address, stationNumber);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void removeFirestationDelete(@PathVariable String address) {
        firestationService.deleteFirestation(address);
    }
}
