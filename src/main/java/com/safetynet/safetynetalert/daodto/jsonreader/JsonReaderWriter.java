package com.safetynet.safetynetalert.daodto.jsonreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * the @Value give the name of the file, which is variable for test purposes
 */

@Repository
public class JsonReaderWriter implements JsonReaderWriterInterface {


    private Database database;
    private static final Logger logger = LogManager.getLogger("Dao");
    public String jsonFile;



    public JsonReaderWriter(@Value("${jsonFileName}") String jsonfile) {
        try {
            FileReader fileString = new FileReader(getClass().getClassLoader().getResource(jsonfile).getFile(), StandardCharsets.UTF_8);
            database = new ObjectMapper()
                    .readValue(fileString,
                            Database.class
                    );
            this.jsonFile = jsonfile;
            fileString.close();
        } catch (IOException e) {
            e.printStackTrace();}
    }

    @Override
    public void writer(Database database) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter(new File("./target/classes", jsonFile));) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFirestations(List<Firestation> firestations) {
        database.setFirestations(firestations);
        writer(database);
    }

    @Override
    public void updateMedicalRecords(List<Medicalrecord> medicalrecords) {
        database.setMedicalrecords(medicalrecords);
        writer(database);
    }

    @Override
    public void updatePersons(List<Person> persons) {
        database.setPersons(persons);
        writer(database);
    }

    @Override
    public Database getDtb() {
        return database;
    }
}
