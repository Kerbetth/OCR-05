package com.safetynet.safetynetalert.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.SafetyNetAlertApplication;
import com.safetynet.safetynetalert.controllers.apicontrollers.FirestationControllers;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class APIFirestationIT {

    @Autowired
    FirestationControllers firestationControllers;

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
        Database result = getDatabase();
        assertThat(result.getFirestations()).hasSize(3);
        assertThat(result.getFirestations().get(0).getStation()).isEqualTo(3);
        assertThat(result.getFirestations().get(0).getAddress()).isEqualTo("3333 broadway");
    }

    @Test
    public void assertDeleteFirestation() {

        //ACT
        firestationControllers.removeFirestationDelete("3333 broadway");

        //ASSERT
        assertThat(getDatabase().getFirestations()).hasSize(2);
    }

}
