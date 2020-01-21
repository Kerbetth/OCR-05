package com.safetynet.SafetyNetAlert.controllers;

import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class PersonController {

    private PersonService personService = new PersonService();

    //@Autowired
    //public void setProductService(PersonService personService) {this.personService = personService;}



    @RequestMapping("/persons")
    public String listPersons(Model model){
        personService = new PersonService();
        model.addAttribute("persons",personService.listAllPersons());
        return "persons";
    }
    @RequestMapping("/person/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "personform";
    }

    @RequestMapping("/personInf/edit/{id}")
    public String editPerson(@PathVariable Integer id , Model model){
        model.addAttribute("personedit", personService.getPersonById(id));
        return "personedit";
    }

    @RequestMapping(value = "/person/add", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Person person){
        personService.addPerson(person);
        return "redirect:/persons";
    }

    @RequestMapping(value = "/person/set", method = RequestMethod.PUT)
    public String UpdatePerson(@RequestParam Integer id, String firstName, String lastName, String address, String city, String zip, String phone, String email){
        personService.updatePerson(id, firstName, lastName, address, city, zip, phone, email);
        return "redirect:/persons";
    }


    @RequestMapping("/personInf/{id}")
    public String getPerson(@PathVariable Integer id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        return "person";
    }
}
