package com.safetynet.SafetyNetAlert.controllers;




import com.safetynet.SafetyNetAlert.domain.Firestation;
import com.safetynet.SafetyNetAlert.domain.MedicalRecord;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.firestationservices.FirestationService;
import com.safetynet.SafetyNetAlert.services.medicalrecordservices.MedicalRecordService;
import com.safetynet.SafetyNetAlert.services.personservices.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HTMLController {

    private PersonService personService;
    private MedicalRecordService medicalRecordService;
    private FirestationService firestationService;

    //----------IndexController----------//

    @RequestMapping("/")
    public String listAll(Model model){
        personService = new PersonService();
        medicalRecordService = new MedicalRecordService();
        firestationService = new FirestationService();
        model.addAttribute("persons",personService.listAllPersons());
        model.addAttribute("medicalrecords", medicalRecordService.listAllMedicalRecords());
        model.addAttribute("firestations", firestationService.listAllfirestations());
        return "Index";
    }

    //----------PersonsControllers----------//

    @RequestMapping("/persons/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "personAdd";
    }

    @RequestMapping(value = "/person/adding")
    public String addingPerson(Person person){
        System.out.println(person);
        personService.addPerson(person);
        return "redirect:/";
    }

    @RequestMapping("/persons/edit/{firstName}/{lastName}")
    public String editPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName , Model model){
        model.addAttribute("personedit", personService.getPersonByName(firstName+lastName));
        return "personedit";
    }

    @RequestMapping(value = "/person/setting")
    public String updatingPerson(Person person){
        personService.updatePerson(person);
        return "redirect:/";
    }

    @RequestMapping(value = "/person/del/{firstName}/{lastName}")
    public String deletingPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName){
        medicalRecordService.removeMedicalRecordData(firstName+lastName);
        personService = new PersonService();
        personService.removePersonData(firstName+lastName);
        return "redirect:/";
    }

    @RequestMapping("/persons/info/{firstName}/{lastName}")
    public String getPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName, Model model){
        model.addAttribute("person", personService.getPersonByName(firstName+lastName));
        return "person";
    }

//----------MedicalRecordControllers----------//

    @RequestMapping("/medrec/new")
    public String newmedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        return "medicalRecordAdd";
    }

    @RequestMapping(value = "/medrec/adding")
    public String addingMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecords(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping("/medrec/edit/{firstName}/{lastName}")
    public String editmedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecordedit", medicalRecordService.getMedicalRecordByName(firstName+lastName));
        return "medicalRecordedit";
    }

    @RequestMapping(value = "/medrec/setting")
    public String updatingMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordService.updateMedicalRecords(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping(value = "/medrec/del/{firstName}/{lastName}")
    public String deletingMedicalRecord(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName, Model model) {
        medicalRecordService.removeMedicalRecordData(firstName+lastName);
        personService = new PersonService();
        personService.removePersonData(firstName+lastName);
        return "redirect:/";
    }

    @RequestMapping("/medrec/info/{firstName}/{lastName}")
    public String getMedrec(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecord", medicalRecordService.getMedicalRecordByName(firstName+lastName));
        return "medicalRecord";
    }

//----------FirestationControllers----------//

    @RequestMapping("/station/new")
    public String newFirestationRecord(Model model) {
        model.addAttribute("firestation", new Firestation());
        return "firestationAdd";
    }

    @RequestMapping(value = "/station/adding")
    public String addingFirestation(Firestation firestation) {
        firestationService.addData(firestation);
        return "redirect:/";
    }

    @RequestMapping("/station/edit/{address}")
    public String editFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestationedit", firestationService.getfirestationByAddress(address));
        return "firestationedit";
    }

    @RequestMapping(value = "/station/setting")
    public String updatingfirestation(Firestation firestation) {
        firestationService.updateData(firestation);
        return "redirect:/";
    }

    @RequestMapping(value = "/station/del/{address}")
    public String deletingfirestation(@PathVariable("address") String address, Model model) {
        firestationService.removeData(address);
        return "redirect:/";
    }

    @RequestMapping("/station/info/{address}")
    public String infoFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestation", firestationService.getfirestationByAddress(address));
        return "firestation";
    }

}
