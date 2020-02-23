package com.safetynet.safetynetalert.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.controllers.apicontrollers.PersonControllers;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class APIPersonIT {

    Person p1;
    Person p2;

    @Autowired
    private PersonControllers personControllers;

    public Database getDatabase(){
        Database database = new Database();
        try {
            database = new ObjectMapper()
                    .readerFor(Database.class)
                    .readValue(new File("datatest.json")
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database;
    }
    @BeforeEach
    public void setup() {
        p1 = new DataTest().getPersons().get(0);
        p2 = new DataTest().getPersons().get(1);
    }

    @Test
    public void addPersonWithCorrectData() {
        //ACT
        p1.setFirstName("newname");
        personControllers.addPersonPost(p1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(5);

    }

    @Test
    public void setPersonWithCorrectData() {
        //ACT
        personControllers.setPersonPut("JeffLoomis",p1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(4);
        assertThat(getDatabase().getPersons().get(1)).extracting(
                Person::getFirstName,
                Person::getLastName,
                Person::getAddress,
                Person::getCity,
                Person::getZip,
                Person::getPhone,
                Person::getEmail)
                .contains("Jeff", "Loomis", p1.getAddress(),p1.getCity(),p1.getZip(), p1.getEmail(),p1.getPhone());

        personControllers.setPersonPut("JeffLoomis",p2);
    }

    @Test
    public void assertDeletePerson() {
        //ACT
        personControllers.removePersonDelete("JeffLoomis");

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(3);
        assertThat(getDatabase().getMedicalrecords()).hasSize(3);

    }

}
