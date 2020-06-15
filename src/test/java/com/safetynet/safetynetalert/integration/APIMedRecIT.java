package com.safetynet.safetynetalert.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.CRUDService.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class APIMedRecIT {


    DataTest dataTest = new DataTest();

    Medicalrecord m1;

    @Autowired
    private MedicalrecordService medicalrecordService;

    @BeforeEach
    public void setup() {
        m1 = dataTest.getMedicalrecords().get(0);
    }



    public Database getDatabase() {
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

    @Test
    public void addPersonWithCorrectData() {
        //ACT
        m1.setFirstName("newname");
        medicalrecordService.add(m1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(5);
        assertThat(getDatabase().getMedicalrecords()).hasSize(5);

    }

    @Test
    public void setPersonWithCorrectData() {
        //ACT
        medicalrecordService.set("JeffLoomis", m1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(4);
        assertThat(getDatabase().getMedicalrecords()).hasSize(4);
        assertThat(getDatabase().getMedicalrecords().get(1)).extracting(
                Medicalrecord::getFirstName,
                Medicalrecord::getLastName,
                Medicalrecord::getBirthdate,
                Medicalrecord::getMedications,
                Medicalrecord::getAllergies)
                .contains("Jeff", "Loomis", m1.getBirthdate(), m1.getMedications(), m1.getAllergies());
    }

    @Test
    public void assertDeletePerson() {
        //ACT
        medicalrecordService.delete("JeffLoomis");
        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(3);
        assertThat(getDatabase().getMedicalrecords()).hasSize(3);

    }

}