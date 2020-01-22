package com.safetynet.SafetyNetAlert.controllers.person;

import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PersonController {

    private PersonService personService = new PersonService();


    @RequestMapping("/persons")
    public String listPersons(Model model){
        personService = new PersonService();
        model.addAttribute("persons",personService.listAllPersons());
        return "persons";
    }
    @RequestMapping("/persons/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "personform";
    }

    @RequestMapping("/persons/edit/{id}")
    public String editPerson(@PathVariable Integer id , Model model){
        model.addAttribute("personedit", personService.getPersonById(id));
        return "personedit";
    }

    @RequestMapping(value = "/person/adding")
    public String addingPerson(Person person){
        personService.addPerson(person);
        return "redirect:/persons";
    }

    @RequestMapping(value = "/person/setting")
    public String updatingPerson(Person person){
        personService.updatePerson(person);
        return "redirect:/persons";
    }

    @RequestMapping(value = "/person/del/{id}")
    public String deletingPerson(@PathVariable Integer id){
        personService.removePersonDelete(id);
        return "redirect:/persons";
    }

    @RequestMapping("/persons/info{id}")
    public String getPerson(@PathVariable Integer id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        return "person";
    }

}
