package com.safetynet.SafetyNetAlert.mentorat;

import com.safetynet.SafetyNetAlert.mentorat.dao.MentoratDao;
import com.safetynet.SafetyNetAlert.mentorat.db.Medicalrecord;
import com.safetynet.SafetyNetAlert.mentorat.db.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentoratService {
    @Autowired
    private MentoratDao dao;

    public List<PersonInfo> personInfo(String firstName, String lastName) {
        List<PersonInfo> result = new ArrayList<>();
        List<Person> persons = dao.findPerson(firstName, lastName);
        for (Person person : persons) {
            PersonInfo info = new PersonInfo();
            info.setNom(person.getLastName());

            List<Medicalrecord> records = dao.findMedicalrecord(person);
            List<String> medications = new ArrayList<>();
            for (Medicalrecord record : records) {
                medications.addAll(record.getMedications());
            }
            info.setMedications(medications);
            result.add(info);
        }
        return result;
    }
}
