package com.safetynet.safetynetalert.controllers.htmlcontrollers;




import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.service.CRUDService.MedicalrecordService;
import com.safetynet.safetynetalert.service.htmlService.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MedicalrecordHTMLControllers {

    /**
     * a thymeleaf template has been added to put or add Medicalrecord
     *
     *
     */

    @Autowired
    private MedicalrecordService medicalrecordDao;
    @Autowired
    private HtmlService htmlService;

    @RequestMapping("/medrec/new")
    public String newmedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new Medicalrecord());
        return "medicalRecordAdd";
    }

    @RequestMapping(value = "/medrec/adding")
    public String addingMedicalRecord(Medicalrecord medicalRecord) {
        medicalrecordDao.add(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping("/medrec/edit/{firstName}{lastName}")
    public String editmedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecordedit", htmlService.findMedicalrecordByID(htmlService.getIdByName(firstName+lastName)));
        return "medicalRecordedit";
    }

    @RequestMapping(value = "/medrec/setting")
    public String updatingMedicalRecord(Medicalrecord medicalRecord) {
        medicalrecordDao.set(medicalRecord.getFirstName()+medicalRecord.getLastName(),medicalRecord);
        return "redirect:/";
    }

    @RequestMapping(value = "/medrec/del/{name}")
    public String deletingMedicalRecord(@PathVariable("name") String name) {
        medicalrecordDao.delete(name);
        return "redirect:/";
    }

    @RequestMapping("/medrec/info/{firstName}/{lastName}")
    public String getMedrec(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecord", htmlService.findMedicalrecordByID(htmlService.getIdByName(firstName+lastName)));
        return "medicalRecord";
    }
}
