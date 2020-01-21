package com.safetynet.SafetyNetAlert.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Person;
import com.safetynet.SafetyNetAlert.services.dao.JSONDAO;
import com.safetynet.SafetyNetAlert.services.dto.PersonsDTO;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
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

    public void updatePerson(Integer id, String firstName, String lastName, String address, String city, String zip, String phone, String email) {
    Person person = new Person(id, firstName, lastName, address, city, zip, phone, email);
        persons.set(person.getId(), person);
        updatePersonsInJSONDATA(person);
    }

    public Person getPersonById(Integer id) {
        return persons.get(id);
    }

    public void addPerson(Person person) {
        if (person != null) {
            person.setId(persons.size() + 1);
            persons.add(person);
            JSONObject allData = JSONDAO.getJsonData();
            ArrayList<Map> personsList = personsDTO.getPersons();
            Map<String, String> personToAdd = new HashMap<>();
            personToAdd.put("firstName", person.getFirstName());
            personToAdd.put("lastName", person.getLastName());
            personToAdd.put("address", person.getAddress());
            personToAdd.put("city", person.getCity());
            personToAdd.put("zip", person.getZip());
            personToAdd.put("phone", person.getPhone());
            personToAdd.put("email", person.getEmail());
            personsList.add(personToAdd);
            allData.put("persons", personsList);
            try (FileWriter file = new FileWriter("src/main/resources/data.json")) {
                file.write(allData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadPersons();
        } else {
            throw new RuntimeException("Please write infos about the person");
        }
    }

    public void updatePersonsInJSONDATA(Person person){
        if (person != null) {
            ObjectMapper oMapper = new ObjectMapper();
            JSONObject allData = JSONDAO.getJsonData();
            ArrayList<Map> personsList = personsDTO.getPersons();
            Map<String, String> personToAdd = (Map) person;
            personsList.set(person.getId(),personToAdd);
            allData.put("persons", personsList);
            try (FileWriter file = new FileWriter("src/main/resources/data.json")) {
                file.write(allData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadPersons();
        }
    }
}
