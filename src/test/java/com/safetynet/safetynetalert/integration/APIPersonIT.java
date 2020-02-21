package com.safetynet.safetynetalert.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.controllers.apicontrollers.PersonControllers;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class APIPersonIT {

    @Value("${jsonFileName}")
    public String jsonFile;

    Person p1;

    private PersonControllers personControllers = new PersonControllers();

    @BeforeEach
    public void setup() {
        WritingCleanJsonData.writingCleanJsonDataTest();
        p1 = new DataTest().getPersons().get(0);
    }
    @AfterEach
    public void finish() {
        WritingCleanJsonData.writingCleanJsonDataTest();
    }

    public Database getDatabase(){
        Database database = new Database();
        try {
            database = new ObjectMapper()
                    .readerFor(Database.class)
                    .readValue(new File(jsonFile)
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database;
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
