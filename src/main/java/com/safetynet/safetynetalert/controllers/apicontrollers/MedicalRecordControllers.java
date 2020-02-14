package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.persandmedservice.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MedicalRecordControllers {


    @Autowired
    private MedicalrecordService medicalrecordDao;


    @PostMapping(value = "/medicalRecord")
    public List<Object> addMedicalRecordPost(@RequestBody Medicalrecord medicalrecord){
        return medicalrecordDao.addMedicalrecord(medicalrecord);
    }

    @PutMapping(value = "/medicalRecord/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public Medicalrecord setMedicalRecordPut(@PathVariable String name, @RequestBody Medicalrecord medicalrecord){
        return medicalrecordDao.setMedicalrecord(name, medicalrecord);
    }

    @DeleteMapping(value = "/medicalRecord/{name}")
    public void removeMedicalRecordDelete(@PathVariable String name) {
        medicalrecordDao.deleteMedicalRecordAndPersonEntry(name);
    }

}
