package com.safetynet.safetynetalert.unit.Services.personServices;

import com.safetynet.safetynetalert.apiservices.FirestationService;
import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FirestationAPIServicesTest {


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
        aPIService.postFirestation("randomaddress",1);

        //ASSERT
        verify(firestationDao).addFirestation(firestationArgCapt.capture());
        assertEquals("randomaddress", firestationArgCapt.getValue().getAddress());
        assertEquals(1, firestationArgCapt.getValue().getStation());
    }

    @Test
    public void medicalRecordCorrectlyPut() {
        //ARRANGE
        Firestation firestation =new Firestation();
        firestation.setStation(1);
        firestation.setAddress("randomaddress");
        doNothing().when(firestationDao).setFirestation(any());
        String name = "name";
        when(firestationDao.findFirestationByAddress(anyString())).thenReturn(firestation);
        ArgumentCaptor<Firestation> firestationArgCapt = ArgumentCaptor.forClass(Firestation.class);

        //ACT
        ResponseEntity responseEntity = aPIService.putFirestation("randomaddress",2);

        //ASSERT
        verify(firestationDao).setFirestation(firestationArgCapt.capture());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, firestationArgCapt.getValue().getStation());
    }

    @Test
    public void medicalRecordIsDeletedWithDelete() {
        //ARRANGE
        String name = "name";
        when(firestationDao.getIdByAddress(anyString())).thenReturn(1);
        ArgumentCaptor<Integer> numberArgCapt = ArgumentCaptor.forClass(Integer.class);

        //ACT
        ResponseEntity responseEntity = aPIService.deletetFirestation("randomaddress");

        //ASSERT
        verify(firestationDao).deleteFirestation(numberArgCapt.capture());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, numberArgCapt.getValue());
    }
}