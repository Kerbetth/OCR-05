package com.safetynet.SafetyNetAlert.controllers;

import com.safetynet.SafetyNetAlert.services.APIServices;
import com.safetynet.SafetyNetAlert.services.firestationservices.FirestationAPIService;
import com.safetynet.SafetyNetAlert.services.medicalrecordservices.MedicalRecordAPIService;
import com.safetynet.SafetyNetAlert.services.personservices.PersonAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class APIController {

    private APIServices apiServices;

    //-------MedicalRecord--------//

    @PostMapping(value = "/medicalRecord")
    public String addMedicalRecordPost(@RequestParam Map<String,String> medicalRecordData){
        apiServices = new MedicalRecordAPIService();
        apiServices.postMethod(medicalRecordData);
        return "redirect:/";
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public String setMedicalRecordPut(@PathVariable String firstNameLastName, @RequestParam Map<String,String> medicalRecordData){
        apiServices = new MedicalRecordAPIService();
        apiServices.putMethod(firstNameLastName, medicalRecordData);
        return "redirect:/";
    }

    @DeleteMapping(value = "/medicalRecord")
    public String removeMedicalRecordDelete(@RequestParam String firstNameLastName) {
        apiServices = new MedicalRecordAPIService();
        apiServices.deleteMethod(firstNameLastName);
        APIServices apiServicesP = new PersonAPIService();
        apiServicesP.deleteMethod(firstNameLastName);
        return "redirect:/";
    }

    //-------Person--------//

    @PostMapping(value = "/person")
    public String addPersonPost(@RequestParam Map<String,String> personData){
        apiServices = new PersonAPIService();
        apiServices.postMethod(personData);
        return "redirect:/";
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public String setPersonPut(@PathVariable String firstNameLastName, @RequestParam Map<String,String> personData){
        apiServices = new PersonAPIService();
        apiServices.putMethod(firstNameLastName, personData);
        return "redirect:/";
    }

    @DeleteMapping(value = "/person")
    public String removePersonDelete(@RequestParam String firstNameLastName) {
        apiServices = new PersonAPIService();
        apiServices.deleteMethod(firstNameLastName);
        APIServices apiServicesM = new MedicalRecordAPIService();
        apiServicesM.deleteMethod(firstNameLastName);
        return "redirect:/";
    }

    //-------Firestation--------//

    @PostMapping(value = "/firestation")
    public String addFirestationPost(@RequestParam Map<String,String> medicalRecordData){
        apiServices = new FirestationAPIService();
        apiServices.postMethod(medicalRecordData);
        return "redirect:/";
    }

    @PutMapping(value = "/firestation/{firstName}/{lastName}")
    public String setFirestationPut(@PathVariable String firstNameLastName, @RequestParam Map<String,String> firestationData){
        apiServices = new FirestationAPIService();
        apiServices.putMethod(firstNameLastName, firestationData);
        return "redirect:/";
    }

    @DeleteMapping(value = "/firestation")
    public String removeFirestationDelete(@RequestParam String address) {
        apiServices = new FirestationAPIService();
        apiServices.deleteMethod(address);
        return "redirect:/";
    }
}
