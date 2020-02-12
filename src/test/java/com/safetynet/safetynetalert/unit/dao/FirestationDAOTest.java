package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.dao.JsonWriter;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class FirestationDAOTest {

    private DataTest dataTest;

    @Mock
    static Database databaseMock;
    @Mock
    static JsonWriter jsonWriterMock;

    FirestationDao firestationDao = new FirestationDao();

    @Before
    public void setup() {
        dataTest = new DataTest();
        firestationDao.database =databaseMock;
        firestationDao.jsonWriter =jsonWriterMock;
        when(databaseMock.getFirestations()).thenReturn(dataTest.getFirestations());
        doNothing().when(jsonWriterMock).writer(any(),anyString());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();

        //ACT
        firestationDao.addFirestation(m);

        //ASSERT
        assertEquals(4, databaseMock.getFirestations().size());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();
        m.setStation(8);

        //ACT
        firestationDao.setFirestation("3333 Broadway",8);

        //ASSERT
        assertEquals(3, databaseMock.getFirestations().size());
        assertEquals(8, databaseMock.getFirestations().get(0).getStation());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        firestationDao.deleteFirestation("3333 Broadway");

        //ASSERT
        assertEquals(2, databaseMock.getFirestations().size());
    }
}