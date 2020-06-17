package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.CRUDservice.CRUDService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class FirestationControllers {


    @Autowired
    private CRUDService<Firestation> firestationService;

    /**
     * Add put and delete for firestation
     */

    @PostMapping(value = "/firestation")
    public Firestation addFirestationPost(@RequestBody Firestation firestationData){
        return firestationService.add(firestationData);
    }

    @PutMapping(value = "/firestation/{address}")
    public Firestation setFirestationPut(@PathVariable String address, @RequestBody Firestation firestationData){
        return firestationService.set(address, firestationData);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void removeFirestationDelete(@PathVariable String address) {
        firestationService.delete(address);
    }
}
