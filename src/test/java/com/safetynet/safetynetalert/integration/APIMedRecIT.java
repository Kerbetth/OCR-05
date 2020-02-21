package com.safetynet.safetynetalert.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.service.persandmedservice.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class APIMedRecIT {

    @Value("${jsonFileName}")
    public String jsonFile;

    DataTest dataTest = new DataTest();

    Medicalrecord m1;

    private MedicalrecordService medicalrecordService = new MedicalrecordService();

        @BeforeEach
        public void setup() {
            WritingCleanJsonData.writingCleanJsonDataTest();
            m1 = dataTest.getMedicalrecords().get(0);
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
        m1.setFirstName("newname");
        medicalrecordService.addMedicalrecord(m1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(5);
        assertThat(getDatabase().getMedicalrecords()).hasSize(5);
        WritingCleanJsonData.writingCleanJsonDataTest();
    }

    @Test
    public void setPersonWithCorrectData() {
        //ACT
        medicalrecordService.setMedicalrecord("JeffLoomis", m1);

        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(4);
        assertThat(getDatabase().getMedicalrecords()).hasSize(4);
        assertThat(getDatabase().getMedicalrecords().get(1)).extracting(Enum.FNAME.str(), Enum.LNAME.str(), Enum.BDATE.str(), Enum.MED.str(), Enum.ALLERG.str())
                .contains("Jeff", "Loomis", m1.getBirthdate(), m1.getMedications(), m1.getAllergies());
    }

    @Test
    public void assertDeletePerson() {
        //ACT
        medicalrecordService.deleteMedicalRecordAndPersonEntry("JeffLoomis");
        //ASSERT
        assertThat(getDatabase().getPersons()).hasSize(3);
        assertThat(getDatabase().getMedicalrecords()).hasSize(3);
    }

}