package com.safetynet.safetynetalert.controllers.htmlcontrollers;




import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.service.persandmedservice.MedicalrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MedicalrecordHTMLControllers {

    @Autowired
    private MedicalrecordService medicalrecordDao;

    @RequestMapping("/medrec/new")
    public String newmedicalRecord(Model model) {
        model.addAttribute("medicalRecord", new Medicalrecord());
        return "medicalRecordAdd";
    }

    @RequestMapping(value = "/medrec/adding")
    public String addingMedicalRecord(Medicalrecord medicalRecord) {
        medicalrecordDao.addMedicalrecord(medicalRecord);
        return "redirect:/";
    }

    @RequestMapping("/medrec/edit/{firstName}/{lastName}")
    public String editmedicalRecord(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecordedit", medicalrecordDao.findMedicalrecordByID(medicalrecordDao.getIdByName(firstName+lastName)));
        return "medicalRecordedit";
    }

    @RequestMapping(value = "/medrec/setting")
    public String updatingMedicalRecord(Medicalrecord medicalRecord) {
        medicalrecordDao.setMedicalrecord(medicalRecord.getFirstName()+medicalRecord.getLastName(),medicalRecord);
        return "redirect:/";
    }

    @RequestMapping(value = "/medrec/del/{name}")
    public String deletingMedicalRecord(@PathVariable("name") String name) {
        medicalrecordDao.deleteMedicalRecordAndPersonEntry(name);
        return "redirect:/";
    }

    @RequestMapping("/medrec/info/{firstName}/{lastName}")
    public String getMedrec(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("medicalRecord", medicalrecordDao.findMedicalrecordByID(medicalrecordDao.getIdByName(firstName+lastName)));
        return "medicalRecord";
    }
}
