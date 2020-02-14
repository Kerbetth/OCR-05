package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.DaoAccessTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.service.firestationservice.FirestationService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class APIFirestationIT {

    DataTest dataTest = new DataTest();
    @Spy
    private DaoAccessTest dao = new DaoAccessTest();
    @Spy
    private Logger logger = LogManager.getLogger("GetServiceSpy");

    Firestation f1;

    @InjectMocks
    private FirestationService firestation = new FirestationService();

    @Before
    public void setup() {
        WritingCleanJsonData.writingCleanJsonDataTest();
        firestation.getDao().setDatabase(dao.getDtb());
        firestation.getDao().setJsonWriter("target/classes/datatest.json");
        f1 = dataTest.getFirestations().get(0);

    }

    @After
    public void finish() {
        WritingCleanJsonData.writingCleanJsonDataTest();
    }
    @Test
    public void addFirestationWithCorrectData() {

        //ACT
        firestation.addFirestation(f1);

        //ASSERT
        DaoAccessTest dao2 = new DaoAccessTest();
        assertEquals(4, dao2.getDtb().getFirestations().size());
    }

    @Test
    public void setFirestationWithCorrectData() {
        //ACT
        firestation.setFirestation("3333 broadway",3);

        //ASSERT
        DaoAccessTest dao2 = new DaoAccessTest();
        assertEquals(3, dao2.getDtb().getFirestations().get(0).getStation());
        assertEquals("3333 broadway", dao2.getDtb().getFirestations().get(0).getAddress());
        assertEquals(3, dao2.getDtb().getFirestations().size());

    }

    @Test
    public void assertDeleteFirestation() {
        //ACT
        firestation.deleteFirestation("3333 broadway");

        //ASSERT
        DaoAccessTest dao2 = new DaoAccessTest();
        assertEquals(2, dao2.getDtb().getFirestations().size());
    }

}
