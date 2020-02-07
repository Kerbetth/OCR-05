package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.HouseHold;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFloodAndFire;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetFloodStationsURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFloodStationsWithCorrectData(){
        //ARRANGE
        List<Person> pl1 = new ArrayList<>();
        List<Person> pl2 = new ArrayList<>();
        List<Person> pl3 = new ArrayList<>();
        pl1.add(dataTest.getPersonlist().get(0));
        pl1.add(dataTest.getPersonlist().get(2));
        pl2.add(dataTest.getPersonlist().get(1));
        pl3.add(dataTest.getPersonlist().get(3));
        when(dao.findFirestationsByNumber(anyString())).thenReturn(dataTest.getFirestations());
        when(dao.findPersonByAddress(anyString()))
                .thenReturn(pl1)
                .thenReturn(pl2)
                .thenReturn(pl3);
        when(dao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0));

        //ACT
        List<HouseHold> getfloodStations = getService.floodstations("1,2");

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = getfloodStations.get(0).getPersonList();
        assertEquals(3, getfloodStations.size());
        assertEquals(2, personFloodAndFires.size());
        assertThat(personFloodAndFires).extracting("name","phone","medications","allergies")
                .contains(  tuple(pl1.get(0).getFirstName() + " " + pl1.get(0).getLastName(), pl1.get(0).getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(pl1.get(1).getFirstName() + " " + pl1.get(1).getLastName(), pl1.get(1).getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()));
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        List<Firestation> emptylist = new ArrayList<>();
        when(dao.findFirestationsByNumber(anyString())).thenReturn(emptylist);
        //ACT
        List<HouseHold> getfloodStations = getService.floodstations("5");
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        assertEquals(0, getfloodStations.size());
    }
}