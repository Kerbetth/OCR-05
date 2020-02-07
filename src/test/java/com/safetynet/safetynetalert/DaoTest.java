package com.safetynet.safetynetalert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Database;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Repository
public class DaoTest {

    Database database;
    String path = "src/main/resources/datatest.json";

    public DaoTest() {
        try {
            database = new ObjectMapper()
                    .readerFor(Database.class)
                    .readValue(
                            Optional.ofNullable(
                                    PersonDao.class.getClassLoader().getResourceAsStream("datatest.json")
                            ).orElseThrow()
                    );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void daoWriter(Database database){
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = Obj.writeValueAsString(database);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter(path)) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
