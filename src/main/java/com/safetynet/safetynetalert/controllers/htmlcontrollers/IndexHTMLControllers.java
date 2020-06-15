package com.safetynet.safetynetalert.controllers.htmlcontrollers;


import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexHTMLControllers {

    @Autowired
    private JsonReaderWriter dao;

    /**
     * a thymeleaf template has been added to get a global view of the data
     * this is the index which show all the data with possibilities of CRUD actions
     *
     */

    @RequestMapping("/")
    public String listAll(Model model){
        model.addAttribute("persons", dao.loadPersons());
        model.addAttribute("medicalrecords", dao.loadMedicalRecords());
        model.addAttribute("firestations", dao.loadFirestions());
        return "Index";
    }
}
