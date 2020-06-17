package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.crudservice.CrudService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MedicalRecordControllers {


    @Autowired
    private CrudService<Medicalrecord> medicalrecordService;

    /**
     * Add put and delete for medicalRecord
     */

    @PostMapping(value = "/medicalRecord")
    public Medicalrecord addMedicalRecordPost(@RequestBody Medicalrecord medicalrecord){
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
