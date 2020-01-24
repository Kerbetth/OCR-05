package com.safetynet.SafetyNetAlert.controllers.firestation;

import com.safetynet.SafetyNetAlert.services.medicalrecordservices.MedicalRecordAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class FirestationAPIController {

    private MedicalRecordAPIService medicalRecordAPIService = new MedicalRecordAPIService();


    @PostMapping(value = "/firestation")
    public String addMedicalRecordPost(@RequestParam Map<String,String> medicalRecordData){
        medicalRecordAPIService.addMedicalRecordPost(medicalRecordData);
        return "redirect:/persons";
    }

    @PutMapping(value = "/firestation/{firstName}/{lastName}")
    public String setMedicalRecordPut(@PathVariable String firstName, String lastName, @RequestParam Map<String,String> medicalRecordData){
        medicalRecordAPIService.updateMedicalRecordPut(firstName, lastName, medicalRecordData);
        return "redirect:/persons";
    }

    @DeleteMapping(value = "/firestation")
    public String removeMedicalRecordDelete(@RequestParam String firstName, String lastName) {
        //medicalRecordAPIService.removeMedicalRecordDelete(firstName, lastName);
        return "redirect:/persons";
    }
}
