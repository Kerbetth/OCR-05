package com.safetynet.SafetyNetAlert.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {

    PersonsDTO personsDTO = new PersonsDTO();
    private ArrayList<Person> persons;

    public PersonService() {
        loadPersons();
    }

    public List<Person> listAllPersons() {
        return persons;
    }

    private void loadPersons() {
        persons = new ArrayList<>();
        ArrayList<Map<String, String>> personsList = personsDTO.getPersons();
        for (int i = 0; i < personsList.size(); i++) {
            Person person = new Person();
            person.setId(i);
            person.setFirstName(personsList.get(i).get("firstName"));
            person.setLastName(personsList.get(i).get("lastName"));
            person.setAddress(personsList.get(i).get("address"));
            person.setCity(personsList.get(i).get("city"));
            person.setZip(personsList.get(i).get("zip"));
            person.setPhone(personsList.get(i).get("phone"));
            person.setEmail(personsList.get(i).get("email"));
            persons.add(person);
        }
    }

    public void addPerson(Person person) {
        if (person != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToAdd = oMapper.convertValue(person, Map.class);
            Integer id = -1;
            personToAdd.remove("id");
            personsDTO.setPersonsData(id, personToAdd);
        } else {
            throw new RuntimeException("Please write infos about the person");
        }
    }


    public void updatePerson(Person person) {
        if (person != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> personToEdit = oMapper.convertValue(person, Map.class);
            Integer id = (Integer) personToEdit.get("id");
            personToEdit.remove("id");
            personsDTO.setPersonsData(id, personToEdit);
        } else {
            throw new RuntimeException("Please write infos about the person");
        }
    }

    public Person getPersonById(Integer id) {
        return persons.get(id);
    }

    public void removePersonDelete(Integer id) {
        personsDTO.removePersonData(id);
    }



}
