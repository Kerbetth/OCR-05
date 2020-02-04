package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.apiservices.MedicalRecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MedicalRecordControllers {


    @Autowired
    private MedicalRecordService medicalRecordService;


    @PostMapping(value = "/medicalRecord")
    public ResponseEntity addMedicalRecordPost(@RequestBody Medicalrecord medicalrecord){
        ResponseEntity response = medicalRecordService.postMedicalRecord(medicalrecord);
        return response;
    }

    @PutMapping(value = "/medicalRecord/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public ResponseEntity setMedicalRecordPut(@PathVariable String name, @RequestBody Medicalrecord medicalrecord){
        ResponseEntity response = medicalRecordService.putMedicalRecord(name, medicalrecord);
        return response;
    }

    @DeleteMapping(value = "/medicalRecord")
    public ResponseEntity removeMedicalRecordDelete(@RequestParam String name) {
        ResponseEntity response = medicalRecordService.deletePersonAndMedicalRecord(name);
        return response;
    }

}
