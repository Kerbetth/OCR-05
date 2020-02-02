package com.safetynet.SafetyNetAlert.APIservices;

import com.safetynet.SafetyNetAlert.dao.DTOFactory;
import com.safetynet.SafetyNetAlert.beans.*;
import com.safetynet.SafetyNetAlert.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetService {

    @Autowired
    private Dao dao;

    public List<Object> firestation(Integer stationNumber) {
        List<Object> result = new ArrayList<>();
        Integer adults = 0;
        Integer children = 0;
        Count count = new Count();
        List<PersonFirestation> personFirestations = new ArrayList<>();
        Set<Firestation> addressList = dao.getStationAddresses(stationNumber.toString());
        List<Person> personsList = new ArrayList<>();
        for (Firestation firestation : addressList) {
            personsList.addAll(dao.findPersonByAddress(firestation.getAddress()));
        }
        for (Person person : personsList) {
            PersonFirestation personFirestation = new PersonFirestation();
            personFirestation.setFirstName(person.getFirstName());
            personFirestation.setLastName(person.getLastName());
            personFirestation.setAddress(person.getAddress());
            personFirestation.setPhone(person.getPhone());
            personFirestations.add(personFirestation);
            Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person);
            if (DTOFactory.getAge(medicalrecord.getBirthdate()) <= 18) {
                children++;
            } else {
                adults++;
            }
        }
        count.setAdults(adults);
        count.setChildren(children);
        result.add(personFirestations);
        result.add(count);
        return result;
    }
    public List<Child> childAlert(String address) {
        List<Child> result = new ArrayList<>();
        List<Person> personsList = dao.findPersonByAddress(address);
        for (Person person : personsList) {
            Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person);
            Integer age = DTOFactory.getAge(medicalrecord.getBirthdate());
            if (age <= 18) {
                Child child = new Child();
                child.setFirstName(person.getFirstName());
                child.setLastName(person.getLastName());
                child.setAddress(person.getAddress());
                child.setAge(age);
                List<String> houseHoldMemberList = new ArrayList<>();
                for (Person householdmember : personsList) {
                    if (!householdmember.getFirstName().equals(person.getFirstName())) {
                        houseHoldMemberList.add(householdmember.getFirstName() + " " + householdmember.getLastName());
                    }
                }
                child.setHouseHoldMembers(houseHoldMemberList);
                result.add(child);
            }
        }
        return result;
    }

    public Set<String> phoneAlert(Integer stationNumber) {
        Set<String> result = new HashSet<>();
        Set<Firestation> addressList = dao.getStationAddresses(stationNumber.toString());
        for (Firestation firestation : addressList) {
            List<Person> personsList = dao.findPersonByAddress(firestation.getAddress());
            for (Person person : personsList) {
                result.add(person.getPhone());
            }

        }
        return result;
    }

    public List<Object> fire(String address) {
        List<Object> result = new ArrayList<>();
        Integer station = dao.findFirestationByAddress(address).getStation();
        List<PersonFloodAndFire> personFloodsList = new ArrayList<>();
        List<Person> personsList = dao.findPersonByAddress(address);
        for (Person person : personsList) {
            PersonFloodAndFire personFlood = new PersonFloodAndFire();
            personFlood.setName(person.getFirstName() + " " + person.getLastName());
            personFlood.setPhone(person.getPhone());
            Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person);
            personFlood.setAge(DTOFactory.getAge(medicalrecord.getBirthdate()));
            personFlood.setMedications(medicalrecord.getMedications());
            personFlood.setAllergies(medicalrecord.getAllergies());
            personFloodsList.add(personFlood);
        }
        result.add(station);
        result.add(personsList);
        return result;
    }

    public List<HouseHold> floodstations(String numbersList) {
        List<HouseHold> result = new ArrayList<>();
        Set<Firestation> addressList = dao.getStationAddresses(numbersList);
        for (Firestation address : addressList) {
            HouseHold houseHold = new HouseHold();
            houseHold.setAddress(address.getAddress());
            List<PersonFloodAndFire> personFloodsList = new ArrayList<>();
            List<Person> personsList = dao.findPersonByAddress(address.getAddress());
            for (Person person : personsList) {
                PersonFloodAndFire personFlood = new PersonFloodAndFire();
                personFlood.setName(person.getFirstName() + " " + person.getLastName());
                personFlood.setPhone(person.getPhone());
                Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person);
                personFlood.setAge(DTOFactory.getAge(medicalrecord.getBirthdate()));
                personFlood.setMedications(medicalrecord.getMedications());
                personFlood.setAllergies(medicalrecord.getAllergies());
                personFloodsList.add(personFlood);
            }
            houseHold.setpersonList(personFloodsList);
            result.add(houseHold);
        }
        return result;
    }

    public List<PersonInfo> personInfo(String firstName, String lastName) {
        List<PersonInfo> result = new ArrayList<>();
        List<Person> persons = dao.findPersonsWithSameFirstNameOrLastName(firstName, lastName);
        for (Person person : persons) {
            PersonInfo info = new PersonInfo();
            info.setName(person.getLastName() + " " + person.getFirstName());
            info.setAddress(person.getAddress());
            info.setEmail(person.getEmail());
            Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person);
            info.setAge(DTOFactory.getAge(medicalrecord.getBirthdate()));
            info.setMedications(medicalrecord.getMedications());
            info.setAllergies(medicalrecord.getAllergies());
            result.add(info);
        }
        return result;
    }

    public List<String> communityEmail(String city) {
        List<String> result = new ArrayList<>();
        List<Person> persons = dao.findPersonByCity(city);
        for (Person person : persons) {
            result.add(person.getEmail());
        }
        return result;
    }

}