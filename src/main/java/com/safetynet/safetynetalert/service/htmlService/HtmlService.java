package com.safetynet.safetynetalert.service.htmlService;

import com.safetynet.safetynetalert.daodto.firestationdao.FirestationDao;
import com.safetynet.safetynetalert.daodto.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HtmlService {

    @Autowired
    PersonDao personDao;
    @Autowired
    MedicalRecordDao medicalRecordDao;
    @Autowired
    FirestationDao firestationDao;

    //***********Html Methods*************//

    public Person findPersonByName(String name){
        return personDao.findPersonByName(name);
    }

    public Medicalrecord findMedicalrecordByID(int id){
        return medicalRecordDao.findMedicalrecordByID(id);
    }

    public int getIdByName(String s) {
       return personDao.getIdByName(s);
    }

    public List<Person> loadPersons() {
        return personDao.getPersons();
    }

    public List<Firestation> loadFirestions() {
        return firestationDao.getFirestations();
    }

    public List<Medicalrecord> loadMedicalRecords() {
        return medicalRecordDao.getMedicalrecords();
    }
    public Firestation findFirestationByAddress(String address){
        return firestationDao.findFirestationByAddress(address);
    }
}
