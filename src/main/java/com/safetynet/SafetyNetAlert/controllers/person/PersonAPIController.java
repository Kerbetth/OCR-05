package com.safetynet.SafetyNetAlert.controllers.person;

import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.PersonAPIService;
import com.safetynet.SafetyNetAlert.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class PersonAPIController {

    private PersonAPIService personAPIService = new PersonAPIService();


    @PostMapping(value = "/person")
    public String addPersonPost(@RequestParam Map<String,String> personData){
        personAPIService.addPersonPost(personData);
        return "redirect:/persons";
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public String setPersonPut(@PathVariable String firstName, String lastName, @RequestParam Map<String,String> personData){
        personAPIService.updatePersonPut(firstName, lastName, personData);
        return "redirect:/persons";
    }

    @DeleteMapping(value = "/person")
    public String removePersonDelete(@RequestParam String firstName, String lastName) {
        personAPIService.removePersonDelete(firstName, lastName);
        return "redirect:/persons";
    }
}
