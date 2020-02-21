package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.GetService;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Count;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFirestation;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetFirestationURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static Dao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFirestationContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(3));
        List<Firestation> firestations = dataTest.getFirestations();
        firestations.remove(2);
        when(dao.findFirestationsByNumber("1")).thenReturn(firestations);
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
        Person p1 =  dataTest.getPersons().get(0);
        Person p2 =  dataTest.getPersons().get(1);
        Person p3 =  dataTest.getPersons().get(2);
        assertThat(personFirestation).extracting("firstName","lastName","address","phone")
                .contains(  tuple(p1.getFirstName(), p1.getLastName(), p1.getAddress(), p1.getPhone()),
                            tuple(p2.getFirstName(), p2.getLastName(), p2.getAddress(), p2.getPhone()),
                            tuple(p3.getFirstName(), p3.getLastName(), p3.getAddress(), p3.getPhone()));
    }

    @Test
    public void returnNoFirestationDataIfNumberStationDoesntExist(){
        //ARRANGE
        List<Firestation> firestations = new ArrayList<>();
        when(dao.findFirestationsByNumber("5")).thenReturn(firestations);

        //ACT
        assertThrows(NoEntryException.class, () -> getService.firestation(5));

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}