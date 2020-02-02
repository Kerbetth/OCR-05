package com.safetynet.SafetyNetAlert.controllers;




import com.safetynet.SafetyNetAlert.beans.Firestation;
import com.safetynet.SafetyNetAlert.beans.Medicalrecord;
import com.safetynet.SafetyNetAlert.beans.Person;
import com.safetynet.SafetyNetAlert.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HTMLControllers {

    @Autowired
    private Dao dao;

    //----------IndexController----------//

    @RequestMapping("/")
    public String listAll(Model model){

        model.addAttribute("persons",dao.loadPersons());
        model.addAttribute("medicalrecords", dao.loadMedicalRecords());
        model.addAttribute("firestations", dao.loadFirestions());
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
        dao.addPerson(person);
        return "redirect:/";
    }

    @RequestMapping("/persons/edit/{firstName}/{lastName}")
    public String editPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName , Model model){
        model.addAttribute("personedit", dao.findPersonByName(firstName+lastName));
        return "personedit";
    }

    @RequestMapping(value = "/person/setting")
    public String updatingPerson(Person person){
        dao.setPerson(person);
        return "redirect:/";
    }

    @RequestMapping(value = "/person/del/{firstName}/{lastName}")
    public String deletingPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName){
        dao.deleteMedicalRecordAndPersonEntry(dao.getIdByName(firstName+lastName));
        return "redirect:/";
    }

    @RequestMapping("/persons/info/{firstName}/{lastName}")
    public String getPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName, Model model){
        model.addAttribute("person", dao.findPersonByName(firstName+lastName));
        return "person";
    }



//----------MedicalRecordControllers----------//

    @RequestMapping("/medrec/new")
    public String newmedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new Medicalrecord());
        return "medicalRecordAdd";
    }

    @RequestMapping(value = "/medrec/adding")
    public String addingMedicalRecord(Medicalrecord medicalRecord) {
        dao.addMedicalrecord(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping("/medrec/edit/{firstName}/{lastName}")
    public String editmedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecordedit", dao.findMedicalrecordByID(dao.getIdByName(firstName+lastName)));
        return "medicalRecordedit";
    }

    @RequestMapping(value = "/medrec/setting")
    public String updatingMedicalRecord(Medicalrecord medicalRecord) {
        dao.setMedicalrecord(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping(value = "/medrec/del/{firstName}/{lastName}")
    public String deletingMedicalRecord(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
        dao.deleteMedicalRecordAndPersonEntry(dao.getIdByName(firstName+lastName));
        return "redirect:/";
    }

    @RequestMapping("/medrec/info/{firstName}/{lastName}")
    public String getMedrec(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecord", dao.findMedicalrecordByID(dao.getIdByName(firstName+lastName)));
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
        dao.addFirestation(firestation);
        return "redirect:/";
    }

    @RequestMapping("/station/edit/{address}")
    public String editFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestationedit", dao.findFirestationByAddress(address));
        return "firestationedit";
    }

    @RequestMapping(value = "/station/setting")
    public String updatingfirestation(Firestation firestation) {
        dao.setFirestation(firestation);
        return "redirect:/";
    }

    @RequestMapping(value = "/station/del/{address}")
    public String deletingfirestation(@PathVariable("address") String address) {
        dao.deleteFirestation(dao.getIdByAddress(address));
        return "redirect:/";
    }

    @RequestMapping("/station/info/{address}")
    public String infoFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestation", dao.findFirestationByAddress(address));
        return "firestation";
    }

}
