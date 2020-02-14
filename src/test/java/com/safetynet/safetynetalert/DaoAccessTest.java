package com.safetynet.safetynetalert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.service.persandmedservice.PersonService;
import com.safetynet.safetynetalert.domain.Database;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;

@Repository
public class DaoAccessTest extends Dao {

    public DaoAccessTest() {
        try {
            setDatabase( new ObjectMapper()
                    .readerFor(Database.class)
                    .readValue(
                            Optional.ofNullable(
                                    PersonService.class.getClassLoader().getResourceAsStream("datatest.json")
                            ).orElseThrow()
                    ));
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}