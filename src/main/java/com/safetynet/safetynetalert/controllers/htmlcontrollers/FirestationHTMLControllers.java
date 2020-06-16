package com.safetynet.safetynetalert.controllers.htmlcontrollers;




import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.service.CRUDservice.FirestationService;
import com.safetynet.safetynetalert.service.htmlService.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FirestationHTMLControllers {

    /**
     * a thymeleaf template has been added to put or add firestations
     *
     *
     */

    @Autowired
    private HtmlService htmlService;
    @Autowired
    private FirestationService firestationService;
    
    @RequestMapping("/station/new")
    public String newFirestationRecord(Model model) {
        model.addAttribute("firestation", new Firestation());
        return "firestationAdd";
    }

    @RequestMapping(value = "/station/adding")
    public String addingFirestation(Firestation firestation) {
        firestationService.add(firestation);
        return "redirect:/";
    }

    @RequestMapping("/station/edit/{address}")
    public String editFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestationedit", htmlService.findFirestationByAddress(address));
        return "firestationedit";
    }

    @RequestMapping(value = "/station/setting")
    public String updatingfirestation(Firestation firestation) {
        firestationService.set(firestation.getAddress(), firestation);
        return "redirect:/";
    }

    @RequestMapping(value = "/station/del/{address}")
    public String deletingfirestation(@PathVariable("address") String address) {
        firestationService.delete(address);
        return "redirect:/";
    }

    @RequestMapping("/station/info/{address}")
    public String infoFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestation", htmlService.findFirestationByAddress(address));
        return "firestation";
    }


}
