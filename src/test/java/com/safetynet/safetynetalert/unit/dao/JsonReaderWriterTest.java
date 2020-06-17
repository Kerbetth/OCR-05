package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JsonReaderWriterTest {

    private DataTest dataTest=new DataTest();

    @Autowired
    JsonReaderWriter dao;

}