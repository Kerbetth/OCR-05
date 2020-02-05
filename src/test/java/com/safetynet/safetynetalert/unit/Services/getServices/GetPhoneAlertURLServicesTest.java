package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.unit.DataTest;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetPhoneAlertURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnPersonInfoWithCorrectDate(){
        //ARRANGE
        Set<Firestation> firestations = dataTest.getFirestations();
        when(dao.getStationAddresses(anyString())).thenReturn(firestations);
        when(dao.findPersonByAddress(any())).thenReturn(dataTest.getPersonlist());

        //ACT
        Set<String> phoneAlert = getService.phoneAlert(0);

        //ASSERT
        assertEquals(4, phoneAlert.size());
        }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        Set<Firestation> emptylist = new HashSet<>();
        when(dao.getStationAddresses(anyString())).thenReturn(emptylist);
        //ACT
        Set<String> phoneAlert = getService.phoneAlert(0);
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        assertEquals(0, phoneAlert.size());
    }
}