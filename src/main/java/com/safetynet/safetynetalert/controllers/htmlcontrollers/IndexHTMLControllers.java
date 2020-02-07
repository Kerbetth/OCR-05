package com.safetynet.safetynetalert.controllers.htmlcontrollers;


import com.safetynet.safetynetalert.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexHTMLControllers {

    @Autowired
    private Dao dao;

    @RequestMapping("/")
    public String listAll(Model model){
        model.addAttribute("persons", dao.loadPersons());
        model.addAttribute("medicalrecords", dao.loadMedicalRecords());
        model.addAttribute("firestations", dao.loadFirestions());
        return "Index";
    }

}
