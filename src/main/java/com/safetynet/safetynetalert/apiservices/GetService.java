package com.safetynet.safetynetalert.apiservices;

import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.exceptions.NoEntryByStationException;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import com.safetynet.safetynetalert.exceptions.NoFnameOrLnameException;
import com.safetynet.safetynetalert.loggerargument.LogArgs;
import com.safetynet.safetynetalert.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GetService {

    private Logger logger = LogManager.getLogger("GetService");

    @Autowired
    private Dao dao;

    public List<Object> firestation(Integer stationNumber) {
        List<Object> result = new ArrayList<>();
        Integer adults = 0;
        Integer children = 0;
        Count count = new Count();
        List<PersonFirestation> personFirestations = new ArrayList<>();
        List<Firestation> addressList = dao.findFirestationsByNumber(stationNumber.toString());
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
            Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person.getFirstName() + person.getLastName());
            if (DTOFactory.getAge(medicalrecord.getBirthdate()) <= 18) {
                children++;
            } else {
                adults++;
            }
        }
        if (personFirestations.size() == 0) {
            logger.error(LogArgs.getNoEntryByStationMessage(stationNumber.toString()));
            throw new NoEntryByStationException(stationNumber.toString());
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
        if (personsList.size() > 0) {
            for (Person person : personsList) {
                Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person.getFirstName() + person.getLastName());
                Integer age = DTOFactory.getAge(medicalrecord.getBirthdate());
                if (age <= 18) {
                    Child child = new Child();
                    child.setFirstName(person.getFirstName());
                    child.setLastName(person.getLastName());
                    child.setAddress(person.getAddress());
                    child.setAge(age);
                    List<String> houseHoldMemberList = new ArrayList<>();
                    for (Person person2 : personsList) {
                        if (!person2.getFirstName().equals(person.getFirstName())) {
                            houseHoldMemberList.add(person2.getFirstName() + " " + person.getLastName());
                        }
                    }
                    child.setHouseHoldMembers(houseHoldMemberList);
                    result.add(child);
                }
            }
            if (result.size() == 0) {
                logger.error("Their is no children at the address \"" + address + "\"");
                return null;
            }
        } else {
            logger.error(LogArgs.getNoEntryMessage(address));
            throw new NoEntryException(address);
        }
        return result;
    }

    public Set<String> phoneAlert(Integer stationNumber) {
        Set<String> result = new HashSet<>();
        List<Firestation> addressList = dao.findFirestationsByNumber(stationNumber.toString());
        if (addressList != null) {
            for (Firestation firestation : addressList) {
                List<Person> personsList = dao.findPersonByAddress(firestation.getAddress());
                for (Person person : personsList) {
                    result.add(person.getPhone());
                }
            }
        } else {
            logger.error(LogArgs.getNoEntryByStationMessage(stationNumber.toString()));
            throw new NoEntryByStationException(stationNumber.toString());
        }
        return result;
    }

    public List<Object> fire(String address) {
        List<Object> result = new ArrayList<>();
        List<PersonFloodAndFire> fireList = new ArrayList<>();
        Integer station = 0;
        if (dao.findFirestationByAddress(address) != null) {
            station = dao.findFirestationByAddress(address).getStation();
            List<Person> personsList = dao.findPersonByAddress(address);
            for (Person person : personsList) {
                Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person.getFirstName() + person.getLastName());
                PersonFloodAndFire personFire = DTOFactory.createPersonFloodAndFire(person, medicalrecord);
                fireList.add(personFire);
            }
        } else {
            logger.error(LogArgs.getNoEntryMessage(address));
        }
        result.add(station);
        result.add(fireList);
        return result;
    }

    public List<HouseHold> floodstations(String numbersList) {
        List<HouseHold> result = new ArrayList<>();
        List<Firestation> addressList = dao.findFirestationsByNumber(numbersList);
        if (addressList.size() > 0) {
            for (Firestation address : addressList) {
                HouseHold houseHold = new HouseHold();
                houseHold.setAddress(address.getAddress());
                List<PersonFloodAndFire> personFloodsList = new ArrayList<>();
                List<Person> personsList = dao.findPersonByAddress(address.getAddress());
                for (Person person : personsList) {
                    Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person.getFirstName() + person.getLastName());
                    PersonFloodAndFire personFire = DTOFactory.createPersonFloodAndFire(person, medicalrecord);
                    personFloodsList.add(personFire);
                }
                houseHold.setPersonList(personFloodsList);
                result.add(houseHold);
            }
        } else {
            logger.error(LogArgs.getNoEntryByStationMessage(numbersList));
            throw new NoEntryByStationException(numbersList);
        }
        return result;
    }

    public List<PersonInfo> personInfo(String firstName, String lastName) {
        List<PersonInfo> result = new ArrayList<>();
        List<Person> persons = dao.findPersonsWithSameFirstNameOrLastName(firstName, lastName);
        if (persons.size() > 0) {
            for (Person person : persons) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setName(person.getFirstName() + " " + person.getLastName());
                personInfo.setAddress(person.getAddress());
                personInfo.setEmail(person.getEmail());
                Medicalrecord medicalrecord = dao.findMedicalrecordByPerson(person.getFirstName() + person.getLastName());
                personInfo.setAge(DTOFactory.getAge(medicalrecord.getBirthdate()));
                personInfo.setMedications(medicalrecord.getMedications());
                personInfo.setAllergies(medicalrecord.getAllergies());
                result.add(personInfo);
            }
        } else {
            logger.error(LogArgs.getNoFnameOrLNameMessage(firstName,lastName));
            throw new NoFnameOrLnameException(firstName,lastName);
        }
        return result;
    }

    public List<String> communityEmail(String city) {
        List<String> result = new ArrayList<>();
        List<Person> persons = dao.findPersonByCity(city);
        if (persons.size() > 0) {
            for (Person person : persons) {
                result.add(person.getEmail());
            }
            if (result.size() == 0) {
                logger.error("Their is no email registred at \"" + city + "\" city");
            }
        } else {
            logger.error("The city \"" + city + "\" doesn't exist in the repertory");
            return null;
        }
        return result;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}