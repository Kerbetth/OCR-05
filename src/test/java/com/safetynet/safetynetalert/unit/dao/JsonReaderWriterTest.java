package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JsonReaderWriterTest {

    private DataTest dataTest=new DataTest();

    @Autowired
    JsonReaderWriter dao;

}