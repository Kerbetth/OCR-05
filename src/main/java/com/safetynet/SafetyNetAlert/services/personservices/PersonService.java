package com.safetynet.SafetyNetAlert.services.personservices;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    DTO dTOPersons = new DTO(Datatype.PERSO);
    private ArrayList<Person> persons;

    public PersonService() {
        loadPersons();
    }

    public List<Person> listAllPersons() {
        return persons;
    }

    private void loadPersons() {
        persons = new ArrayList<>();
        ArrayList<Map> personsList = dTOPersons.getDataTypeContent();
        for (int i = 0; i < personsList.size(); i++) {
            Person person = new Person();
            person.setId(i);
            person.setFirstName(personsList.get(i).get("firstName").toString());
            person.setLastName(personsList.get(i).get("lastName").toString());
            person.setAddress(personsList.get(i).get("address").toString());
            person.setCity(personsList.get(i).get("city").toString());
            person.setZip(personsList.get(i).get("zip").toString());
            person.setPhone(personsList.get(i).get("phone").toString());
            person.setEmail(personsList.get(i).get("email").toString());
            persons.add(person);
        }
    }

    public boolean verifyNoNullData(Person personData){
        Integer i=0;
        if(personData.getFirstName() != "") i++;
        if(personData.getLastName() != "") i++;
        if(personData.getAddress() != "") i++;
        if(personData.getCity() != "") i++;
        if(personData.getZip() != "") i++;
        if(personData.getPhone() != "") i++;
        if(personData.getEmail() != "") i++;
        Boolean isNotNull = (i == 7);
        return isNotNull;
    }

    public void addPerson(Person person) {
        if (verifyNoNullData(person)) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToAdd = oMapper.convertValue(person, Map.class);
            dTOPersons.addData(personToAdd);
        } else {
            throw new RuntimeException("Please write all required infos about the person");
        }
    }

    public void updatePerson(Person person) {
        if (verifyNoNullData(person)) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToEdit = oMapper.convertValue(person, Map.class);
            Integer id = (Integer) personToEdit.get("id");
            personToEdit.remove("id");
            dTOPersons.setData(id, personToEdit);
        } else {
            throw new RuntimeException("Please write all required infos about the person");
        }
    }

    public void removePersonData(String firstNameLastName) {
        int id =dTOPersons.getIdByName(firstNameLastName);
        dTOPersons.removeData(id);
    }

    public Person getPersonByName(String firstNameLastName) {
        Integer id=dTOPersons.getIdByName(firstNameLastName);
        return persons.get(id);
    }
}
