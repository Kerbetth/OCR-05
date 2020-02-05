package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Count;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFirestation;
import com.safetynet.safetynetalert.unit.DataTest;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetFirestationURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFirestationContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersonlist();
        addressPerson.remove(addressPerson.get(3));
        Set<Firestation> firestations = dataTest.getFirestations();
        firestations.remove(2);
        when(dao.getStationAddresses("1")).thenReturn(firestations);
        when(dao.findPersonByAddress(addressPerson.get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(1))
                .thenReturn(dataTest.getMedicalrecords().get(2));

        //ACT
        List<Object> getfirestation = getService.firestation(1);

        //ASSERT
        Count count = (Count) getfirestation.get(1);
        List<PersonFirestation> personFirestation = (List<PersonFirestation>) getfirestation.get(0);
        assertEquals(2, getfirestation.size());
        assertEquals(2, count.getAdults());
        assertEquals(1, count.getChildren());
        Person p1 =  dataTest.getPersonlist().get(0);
        Person p2 =  dataTest.getPersonlist().get(1);
        Person p3 =  dataTest.getPersonlist().get(2);
        assertThat(personFirestation).extracting("firstName","lastName","address","phone")
                .contains(  tuple(p1.getFirstName(), p1.getLastName(), p1.getAddress(), p1.getPhone()),
                            tuple(p2.getFirstName(), p2.getLastName(), p2.getAddress(), p2.getPhone()),
                            tuple(p3.getFirstName(), p3.getLastName(), p3.getAddress(), p3.getPhone()));
    }

    @Test
    public void returnNoFirestationDataIfNumberStationDoesntExist(){
        //ARRANGE
        Set<Firestation> firestations = new HashSet<>();
        when(dao.getStationAddresses("5")).thenReturn(firestations);

        //ACT
        List<Object> getfirestation = getService.firestation(5);

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        Count count = (Count) getfirestation.get(1);
        assertEquals(2, getfirestation.size());
        assertEquals(0, count.getAdults());
        assertEquals(0, count.getChildren());
    }
}