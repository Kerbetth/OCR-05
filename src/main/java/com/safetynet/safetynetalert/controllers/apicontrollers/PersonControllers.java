package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.service.CRUDService.PersonService;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonControllers {


    @Autowired
    private PersonService personService;

    /**
     * Add put and delete for person
     */

    @PostMapping(value = "/person")
    public List<Object> addPersonPost(@RequestBody Person person) {
        return personService.add(person);
    }

    @PutMapping(value = "/person/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public Person setPersonPut(@PathVariable String name, @RequestBody Person personData) {
        return personService.set(name, personData);
    }

    @DeleteMapping(value = "/person/{name}")
    public void removePersonDelete(@PathVariable String name) {
        personService.delete(name);
    }

}
