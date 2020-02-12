package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFloodAndFire;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetFireURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static Dao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFireContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(3));
        addressPerson.remove(addressPerson.get(1));
        when(dao.findFirestationByAddress(anyString())).thenReturn(dataTest.getFirestation1());
        when(dao.findPersonByAddress(addressPerson.get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(2));

        //ACT
        List<Object> getfire = getService.fire(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = (List<PersonFloodAndFire>) getfire.get(1);
        assertEquals(2, getfire.size());
        assertEquals(1, (Integer) getfire.get(0));
        assertEquals(2, personFloodAndFires.size());
        Person p1 =  dataTest.getPersons().get(0);
        Person p3 =  dataTest.getPersons().get(1);
        assertThat(personFloodAndFires).extracting("name","phone","medications","allergies")
                .contains(  tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){

        //ACT
        List<Object> getfire = getService.fire("noaddress");

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        assertEquals(2, getfire.size());
        assertEquals(0, (Integer) getfire.get(0));
    }
}