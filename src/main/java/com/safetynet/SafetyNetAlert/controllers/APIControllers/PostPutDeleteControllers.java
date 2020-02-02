package com.safetynet.SafetyNetAlert.controllers.APIControllers;

import com.safetynet.SafetyNetAlert.APIservices.PostPutDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class PostPutDeleteControllers {


    @Autowired
    private PostPutDeleteService postPutDeleteService;


    //-------Person--------//

    @PostMapping(value = "/person")
    public String addPersonPost(@RequestParam Map<String,String> personData){
        postPutDeleteService.postPerson(personData);
        return "redirect:/";
    }

    @PutMapping(value = "/person/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public String setPersonPut(@PathVariable String name, @RequestParam Map<String,String> personData){
        postPutDeleteService.putPerson(name, personData);
        return "redirect:/";
    }

    @DeleteMapping(value = "/person")
    public String removePersonDelete(@RequestParam String name) {
        postPutDeleteService.deletePersonAndMedicalRecord(name);
        return "redirect:/";
    }



    //-------MedicalRecord--------//

    @PostMapping(value = "/medicalRecord")
    public String addMedicalRecordPost(@RequestParam Map<String,String> medicalRecordData){
        postPutDeleteService.postMedicalRecord(medicalRecordData);
        return "redirect:/";
    }

    @PutMapping(value = "/medicalRecord/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public String setMedicalRecordPut(@PathVariable String name, @RequestParam Map<String,String> medicalRecordData){
        postPutDeleteService.putMedicalRecord(name, medicalRecordData);
        return "redirect:/";
    }

    @DeleteMapping(value = "/medicalRecord")
    public String removeMedicalRecordDelete(@RequestParam String name) {
        postPutDeleteService.deletePersonAndMedicalRecord(name);
        return "redirect:/";
    }



    //-------Firestation--------//

    @PostMapping(value = "/firestation")
    public String addFirestationPost(@RequestParam String address, @RequestParam Integer stationNumber){
        postPutDeleteService.postFirestation(address, stationNumber);
        return "redirect:/";
    }

    @PutMapping(value = "/firestation/{address}")
    public String setFirestationPut(@PathVariable String address, @RequestParam Integer stationNumber){
        postPutDeleteService.putFirestation(address, stationNumber);
        return "redirect:/";
    }

    @DeleteMapping(value = "/firestation")
    public String removeFirestationDelete(@RequestParam String address) {
        postPutDeleteService.deletetFirestation(address);
        return "redirect:/";
    }
}
