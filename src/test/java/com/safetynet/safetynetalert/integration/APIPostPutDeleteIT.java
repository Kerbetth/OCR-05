package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class APIPostPutDeleteIT {

    DataTest dataTest = new DataTest();
    @Spy
    private DaoTest dao = new DaoTest();
    @Spy
    private Logger logger = LogManager.getLogger("GetServiceSpy");

    Firestation p1;
    Firestation p2;
    Firestation p3;

    @InjectMocks
    private FirestationDao firestation = new FirestationDao();

    @Before
    public void setup() {
        dataTest.writingCleanJsonDataTest();
        firestation.setDatabase(dao.getDatabase());
        p1 = dataTest.getFirestations().get(0);
        p2 = dataTest.getFirestations().get(1);
        p3 = dataTest.getFirestations().get(2);
    }

    @Test
    public void addFirestationWithCorrectData() {

        //ACT
        firestation.addFirestation(p1);

        //ASSERT
        assertEquals(4, firestation.database.getFirestations().size());
    }

    @Test
    public void setFirestationWithCorrectData() {
        //ACT
        Firestation newfirestation = firestation.setFirestation("3333 broadway",3);

        //ASSERT
        assertEquals(3, newfirestation.getStation());
        assertEquals("3333 broadway", newfirestation.getAddress());
        assertEquals(3, firestation.database.getFirestations().size());

    }

    @Test
    public void assertDeleteFirestation() {
        //ACT
        firestation.deleteFirestation("3333 broadway");

        //ASSERT
        assertEquals(2, firestation.database.getFirestations().size());
    }
}
