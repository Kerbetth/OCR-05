package com.safetynet.safetynetalert.controllers.apicontrollers;

import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonControllers {


    @Autowired
    private PersonDao personDao;


    @PostMapping(value = "/person")
    public List<Object> addPersonPost(@RequestBody Person person){
        return personDao.addPerson(person);
    }

    @PutMapping(value = "/person/{name}")
    //"name" has to be declared in format "FirstnameLastname"
    public Person setPersonPut(@PathVariable String name, @RequestBody Person personData){
        Person person1  =  personDao.setPerson(name, personData);
        return person1;
    }

    @DeleteMapping(value = "/person/{name}")
    public void removePersonDelete(@PathVariable String name) {
        personDao.deleteMedicalRecordAndPersonEntry(name);
    }

}
