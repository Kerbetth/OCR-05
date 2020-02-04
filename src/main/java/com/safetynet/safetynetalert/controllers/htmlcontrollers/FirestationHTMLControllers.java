package com.safetynet.safetynetalert.controllers.htmlcontrollers;




import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.dao.FirestationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class FirestationHTMLControllers {


    @Autowired
    private FirestationDao firestationDao;

    
    @RequestMapping("/station/new")
    public String newFirestationRecord(Model model) {
        model.addAttribute("firestation", new Firestation());
        return "firestationAdd";
    }

    @RequestMapping(value = "/station/adding")
    public String addingFirestation(Firestation firestation) {
        firestationDao.addFirestation(firestation);
        return "redirect:/";
    }

    @RequestMapping("/station/edit/{address}")
    public String editFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestationedit", firestationDao.findFirestationByAddress(address));
        return "firestationedit";
    }

    @RequestMapping(value = "/station/setting")
    public String updatingfirestation(Firestation firestation) {
        firestationDao.setFirestation(firestation);
        return "redirect:/";
    }

    @RequestMapping(value = "/station/del/{address}")
    public String deletingfirestation(@PathVariable("address") String address) {
        firestationDao.deleteFirestation(firestationDao.getIdByAddress(address));
        return "redirect:/";
    }

    @RequestMapping("/station/info/{address}")
    public String infoFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestation", firestationDao.findFirestationByAddress(address));
        return "firestation";
    }

}
