package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.CRUDService.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MedicalRecordControllers {


    @Autowired
    private MedicalrecordService medicalrecordService;

    /**
     * Add put and delete for medicalRecord
     */

    @PostMapping(value = "/medicalRecord")
    public List<Object> addMedicalRecordPost(@RequestBody Medicalrecord medicalrecord){
        return medicalrecordService.add(medicalrecord);
    }

    @PutMapping(value = "/medicalRecord/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public Medicalrecord setMedicalRecordPut(@PathVariable String name, @RequestBody Medicalrecord medicalrecord){
        return medicalrecordService.set(name, medicalrecord);
    }

    @DeleteMapping(value = "/medicalRecord/{name}")
    public void removeMedicalRecordDelete(@PathVariable String name) {
        medicalrecordService.delete(name);
    }

}
