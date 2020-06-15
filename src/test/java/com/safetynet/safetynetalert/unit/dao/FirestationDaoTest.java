package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.unit.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class FirestationDaoTest {

    private DataTest dataTest=new DataTest();
    private Database database;

    @Mock
    JsonReaderWriter jsonReaderWriter;

    @InjectMocks
    FirestationDao dao;

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        doNothing().when(jsonReaderWriter).writer(any());
        database = dataTest.getDatabase();
        when(jsonReaderWriter.getDtb()).thenReturn(database);
    }

    @Test
    public void returnCorrectFirestationAccordingToTheAddress() {
        //ACT
        Firestation firestation = dao.findFirestationByAddress(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getFirestation1().getAddress()).isEqualTo(firestation.getAddress());
    }

    @Test
    public void returnCorrectFirestationsAccordingToTheNumberOfStation() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1");

        //ASSERT
        assertThat(dataTest.getFirestations().get(0).getStation()).isEqualTo(firestations.get(0).getStation());
        assertThat(dataTest.getFirestations().get(1).getAddress()).isEqualTo(firestations.get(1).getAddress());
        assertThat(firestations).hasSize(2);
    }

    @Test
    public void ignoreNumbersOfFirestationThatDoesntExist() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1,2,3");

        //ASSERT
        assertThat(firestations.get(0).getStation()).isEqualTo(1);
        assertThat(firestations.get(1).getStation()).isEqualTo(1);
        assertThat(firestations.get(2).getStation()).isEqualTo(2);
        assertThat(firestations).hasSize(3);
    }


}