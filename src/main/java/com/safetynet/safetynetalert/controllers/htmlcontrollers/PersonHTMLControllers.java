package com.safetynet.safetynetalert.controllers.htmlcontrollers;




import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.apiservices.persandmedservice.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PersonHTMLControllers {

    @Autowired
    private PersonService personService;


    @RequestMapping("/persons/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "personAdd";
    }

    @RequestMapping(value = "/person/adding")
    public String addingPerson(Person person){
        personService.addPerson(person);
        return "redirect:/";
    }

    @RequestMapping("/persons/edit/{firstName}/{lastName}")
    public String editPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName , Model model){
        model.addAttribute("personedit", personService.findPersonByName(firstName+lastName));
        return "personedit";
    }

    @RequestMapping(value = "/person/setting")
    public String updatingPerson(Person person){
        personService.setPerson(person.getFirstName()+person.getLastName(),person);
        return "redirect:/";
    }

    @RequestMapping(value = "/person/del/{name}")
    public String deletingPerson(@PathVariable("name") String name){
        personService.deleteMedicalRecordAndPersonEntry(name);
        return "redirect:/";
    }

    @RequestMapping("/persons/info/{firstName}/{lastName}")
    public String getPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName, Model model){
        model.addAttribute("person", personService.findPersonByName(firstName+lastName));
        return "person";
    }

}
