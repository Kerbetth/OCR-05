package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.apiservices.PersonService;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class PersonControllers {


    @Autowired
    private PersonService personService;


    @PostMapping(value = "/person")
    public ResponseEntity addPersonPost(@RequestBody Person person){
        ResponseEntity response = personService.postPerson(person);
        return response;
    }

    @PutMapping(value = "/person/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public ResponseEntity setPersonPut(@PathVariable String name, @RequestBody Person personData){
        ResponseEntity response =  personService.putPerson(name, personData);
        return response;
    }

    @DeleteMapping(value = "/person")
    public ResponseEntity removePersonDelete(@RequestParam String name) {
        ResponseEntity response = personService.deletePersonAndMedicalRecord(name);
        return response;
    }

}
