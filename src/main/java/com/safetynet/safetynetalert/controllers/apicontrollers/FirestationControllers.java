package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.apiservices.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class FirestationControllers {


    @Autowired
    private FirestationService firestationService;


    @PostMapping(value = "/firestation")
    public ResponseEntity addFirestationPost(@RequestParam String address, @RequestParam Integer stationNumber){
        ResponseEntity response = firestationService.postFirestation(address, stationNumber);
        return response;
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        ResponseEntity response = firestationService.putFirestation(address, stationNumber);
        return response;
    }

    @DeleteMapping(value = "/firestation")
    public ResponseEntity removeFirestationDelete(@RequestParam String address) {
        ResponseEntity response = firestationService.deletetFirestation(address);
        return response;
    }
}
