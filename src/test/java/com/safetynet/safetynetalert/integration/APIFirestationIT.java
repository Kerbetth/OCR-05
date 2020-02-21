package com.safetynet.safetynetalert.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.controllers.apicontrollers.FirestationControllers;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class APIFirestationIT {

    @Value("${jsonFileName}")
    public String jsonFile;

    FirestationControllers firestationControllers = new FirestationControllers();

    @BeforeEach
    public void setup() {
        WritingCleanJsonData.writingCleanJsonDataTest();
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
    public void addFirestationWithCorrectData() {
        Firestation f1 = new Firestation();
        f1.setStation(4);
        f1.setAddress("newAddress");
        //ACT
        firestationControllers.addFirestationPost(f1);
        //ASSERT
        assertThat(getDatabase().getFirestations()).hasSize(4);
    }

    @Test
    public void setFirestationWithCorrectData() {
        //ACT
        firestationControllers.setFirestationPut("3333 broadway", 3);

        //ASSERT
        assertThat(getDatabase().getFirestations()).hasSize(3);
        assertThat(getDatabase().getFirestations().get(0).getStation()).isEqualTo(3);
        assertThat(getDatabase().getFirestations().get(0).getAddress()).isEqualTo("3333 broadway");

    }

    @Test
    public void assertDeleteFirestation() {
        //ACT
        firestationControllers.removeFirestationDelete("3333 broadway");

        //ASSERT
        assertThat(getDatabase().getFirestations()).hasSize(2);
    }

}
