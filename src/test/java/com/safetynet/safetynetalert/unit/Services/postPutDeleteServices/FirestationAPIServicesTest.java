package com.safetynet.safetynetalert.unit.Services.postPutDeleteServices;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.FirestationService;
import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FirestationAPIServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static FirestationDao firestationDao;

    @InjectMocks
    FirestationService aPIService;


    @Test
    public void returnFirestationContentWithCorrectDataEvenWithUselessEntryValueForPost() {

        //ARRANGE
        doNothing().when(firestationDao).addFirestation(any());
        ArgumentCaptor<Firestation> firestationArgCapt = ArgumentCaptor.forClass(Firestation.class);

        //ACT
        aPIService.postFirestation(dataTest.getFirestations().get(0));

        //ASSERT
        verify(firestationDao).addFirestation(firestationArgCapt.capture());
        assertEquals(dataTest.getFirestations().get(0).getAddress(), firestationArgCapt.getValue().getAddress());
        assertEquals(1, firestationArgCapt.getValue().getStation());
    }

    @Test
    public void firestationCorrectlyPut() {
        //ARRANGE
        Firestation firestation =new Firestation();
        firestation.setStation(1);
        firestation.setAddress("randomaddress");
        doNothing().when(firestationDao).setFirestation(any());
        String name = "name";
        when(firestationDao.findFirestationByAddress(anyString())).thenReturn(firestation);
        ArgumentCaptor<Firestation> firestationArgCapt = ArgumentCaptor.forClass(Firestation.class);

        //ACT
        aPIService.putFirestation("randomaddress",2);

        //ASSERT
        verify(firestationDao).setFirestation(firestationArgCapt.capture());
        assertEquals(2, firestationArgCapt.getValue().getStation());
    }

    @Test
    public void medicalRecordIsDeletedWithDelete() {
        //ARRANGE
        String name = "name";
        when(firestationDao.getIdByAddress(anyString())).thenReturn(1);
        ArgumentCaptor<Integer> numberArgCapt = ArgumentCaptor.forClass(Integer.class);

        //ACT
        aPIService.deletetFirestation("randomaddress");

        //ASSERT
        verify(firestationDao).deleteFirestation(numberArgCapt.capture());
        assertEquals(1, numberArgCapt.getValue());
    }
}