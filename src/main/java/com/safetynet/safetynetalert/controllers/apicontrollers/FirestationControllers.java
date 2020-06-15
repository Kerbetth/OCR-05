package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.CRUDService.FirestationService;
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
        return (Firestation)firestationService.add(firestationData);
    }

    @PutMapping(value = "/firestation/{address}")
    public Firestation setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        return firestationService.set(address, stationNumber);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void removeFirestationDelete(@PathVariable String address) {
        firestationService.delete(address);
    }
}
