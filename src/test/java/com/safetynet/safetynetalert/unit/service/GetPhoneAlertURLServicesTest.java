package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.daodto.firestationdao.FirestationDao;
import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.getservice.GetService;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetPhoneAlertURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao personDao;
    @Mock
    static FirestationDao firestationDao;
    @Mock
    static Logger loggermock;
    @InjectMocks
    GetService getService;

    @Test
    public void returnPersonInfoWithCorrectDate(){
        //ARRANGE
        List<Firestation> firestations = dataTest.getFirestations();
        when(firestationDao.findFirestationsByNumber(anyString())).thenReturn(firestations);
        when(personDao.findPersonByAddress(any())).thenReturn(dataTest.getPersons());

        //ACT
        Set<String> phoneAlert = getService.phoneAlert(0);

        //ASSERT
        assertThat(phoneAlert).hasSize(4);
        }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        when(firestationDao.findFirestationsByNumber(anyString())).thenReturn(null);
        //ACT
        assertThrows(NoEntryException.class, () ->  getService.phoneAlert(0));
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}