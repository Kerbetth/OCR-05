package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.DaoTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.firestationservice.FirestationService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class FirestationDAOTest {

    private DataTest dataTest;
    @Mock
    static Logger loggerMock;

    @Spy
    DaoTest dao = new DaoTest();

    @InjectMocks
    FirestationService firestationService = new FirestationService();

    @Before
    public void setup() {
        dataTest = new DataTest();
        doNothing().when(dao).writer(any());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();

        //ACT
        firestationService.addFirestation(m);

        //ASSERT
        assertEquals(4, firestationService.getDtb().getFirestations().size());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();
        m.setStation(8);

        //ACT
        firestationService.setFirestation("3333 broadway",8);

        //ASSERT
        assertEquals(3, firestationService.getDtb().getFirestations().size());
        assertEquals(8, firestationService.getDtb().getFirestations().get(0).getStation());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        firestationService.deleteFirestation("3333 broadway");

        //ASSERT
        assertEquals(2, firestationService.getDtb().getFirestations().size());
    }
}