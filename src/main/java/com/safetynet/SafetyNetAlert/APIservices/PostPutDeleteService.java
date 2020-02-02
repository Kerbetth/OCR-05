package com.safetynet.SafetyNetAlert.APIservices;


import com.safetynet.SafetyNetAlert.beans.Firestation;
import com.safetynet.SafetyNetAlert.beans.Medicalrecord;
import com.safetynet.SafetyNetAlert.beans.Person;
import com.safetynet.SafetyNetAlert.dao.DTOFactory;
import com.safetynet.SafetyNetAlert.dao.Dao;
import com.safetynet.SafetyNetAlert.enumerations.DataEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostPutDeleteService {
    @Autowired
    private Dao dao;


    public String postMedicalRecord(Map<String, String> medrecData) {
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName(medrecData.get(DataEntry.FNAME.getString()));
        medicalrecord.setLastName(medrecData.get(DataEntry.LNAME.getString()));
        medicalrecord.setBirthdate(DTOFactory.getDate(medrecData.get(DataEntry.BIRTHDATE.getString())));
        medicalrecord.setMedications(DTOFactory.getList(medrecData.get(DataEntry.MEDIC.getString())));
        medicalrecord.setAllergies(DTOFactory.getList(medrecData.get(DataEntry.ALLERGI.getString())));
        dao.addMedicalrecord(medicalrecord);
        return "Ok";
    }

    public String postPerson(Map<String, String> personData) {
        Person person = new Person();
        person.setFirstName( personData.get(DataEntry.FNAME.getString()));
        person.setLastName( personData.get(DataEntry.LNAME.getString()));
        person.setAddress( personData.get(DataEntry.ADDRESS.getString()));
        person.setCity( personData.get(DataEntry.CITY.getString()));
        person.setZip( Integer.parseInt(personData.get(DataEntry.ZIP.getString())));
        person.setPhone( personData.get(DataEntry.PHONE.getString()));
        person.setEmail( personData.get(DataEntry.EMAIL.getString()));
        dao.addPerson(person);
        return "Ok";
    }

    public String postFirestation(String address, Integer stationNumber) {
        Firestation firestation = new Firestation();
        firestation.setAddress(address);
        firestation.setStation(stationNumber);
        dao.addFirestation(firestation);
        return "Ok";
    }

    public void putMedicalRecord(String firstNameLastName, Map<String, String> map) {
        Integer id = dao.getIdByName(firstNameLastName);
        Medicalrecord medicalRecord = dao.findMedicalrecordByID(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey()) {
                case "birthdate":
                    Date birthdate = DTOFactory.getDate(value.getValue());
                    medicalRecord.setBirthdate(birthdate);
                    break;
                case "medications":
                    List<String> medications = new ArrayList<>(Arrays.asList(value.getKey().split(",")));
                    medicalRecord.setMedications(medications);
                    break;
                case "allergies":
                    List<String> allergies = new ArrayList<>(Arrays.asList(value.getKey().split(",")));
                    medicalRecord.setAllergies(allergies);
                    break;
            }
        }
        dao.setMedicalrecord(id, medicalRecord);
    }

    public void putPerson(String firstNameLastName, Map<String, String> map) {
        Integer id = dao.getIdByName(firstNameLastName);
        Person person = dao.findPersonByID(id);
        for (Map.Entry<String, String> value : map.entrySet()) {
            switch (value.getKey()) {
                case "address":
                    person.setAddress(value.getValue());
                    break;
                case "city":
                    person.setCity(value.getValue());
                    break;
                case "zip":
                    person.setZip(Integer.parseInt(value.getValue()));
                    break;
                case "phone":
                    person.setPhone(value.getValue());
                    break;
                case "email":
                    person.setEmail(value.getValue());
                    break;
            }
        }
        dao.setPerson(id, person);
    }

    public void putFirestation(String address, Integer stationNumber) {
        Integer id = dao.getIdByAddress(address);
        Firestation firestation = dao.findFirestationByAddress(address);
        firestation.setStation(stationNumber);
        dao.setFirestation(id, firestation);
    }

    public void deletePersonAndMedicalRecord(String firstNameLastName) {
        Integer id = dao.getIdByName(firstNameLastName);
        dao.deleteMedicalRecordAndPersonEntry(id);
    }

    public void deletetFirestation(String address) {
        Integer id = dao.getIdByAddress(address);
        dao.deleteFirestation(id);
    }


    //***********Html Methods*************//

    public void addingPerson(Person person){
        dao.addPerson(person);
    }


}
