package com.safetynet.SafetyNetAlert.services.personservices;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
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
        ArrayList<Map<String, String>> medList = (ArrayList) personsDTO.getData().get("medicalrecords");
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
            personsDTO.addPersonsData(personToAdd);
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
            personsDTO.setPersonsData(id, personToEdit);
        } else {
            throw new RuntimeException("Please write all required infos about the person");
        }
    }

    public void removePersonData(String firstName, String lastName) {
        Person person = persons.get(personsDTO.getIdByName(firstName,lastName));
        personsDTO.removePersonData(person.getId());
    }

    public Person getPersonByName(String firstName, String lastName) {
        Integer id=personsDTO.getIdByName(firstName, lastName);
        return persons.get(id);
    }


}
