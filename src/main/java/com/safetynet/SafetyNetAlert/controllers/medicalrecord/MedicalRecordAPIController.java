package com.safetynet.SafetyNetAlert.controllers.medicalrecord;

import com.safetynet.SafetyNetAlert.services.medicalrecordservices.MedicalRecordAPIService;
import com.safetynet.SafetyNetAlert.services.personservices.PersonAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class MedicalRecordAPIController {

    private MedicalRecordAPIService medicalRecordAPIService = new MedicalRecordAPIService();


    @PostMapping(value = "/medicalRecord")
    public String addMedicalRecordPost(@RequestParam Map<String,String> medicalRecordData){
        medicalRecordAPIService.addMedicalRecordPost(medicalRecordData);
        return "redirect:/persons";
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public String setMedicalRecordPut(@PathVariable String firstName, String lastName, @RequestParam Map<String,String> medicalRecordData){
        medicalRecordAPIService.updateMedicalRecordPut(firstName, lastName, medicalRecordData);
        return "redirect:/persons";
    }

    /*@DeleteMapping(value = "/medicalRecord")
    public String removeMedicalRecordDelete(@RequestParam String firstName, String lastName) {
        medicalRecordAPIService.removeMedicalRecordDelete(firstName, lastName);
        return "redirect:/persons";
    }*/
}
