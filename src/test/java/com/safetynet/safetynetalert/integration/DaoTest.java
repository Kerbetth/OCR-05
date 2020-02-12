package com.safetynet.safetynetalert.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Database;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public class DaoTest  extends Dao {

    private Database database;

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
    public Database getDatabase(){
        return database;
    }
}
