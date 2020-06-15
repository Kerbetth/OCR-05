package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.GetService;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.HouseHold;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFloodAndFire;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetFloodStationsURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static JsonReaderWriter dao;
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
        pl1.add(dataTest.getPersons().get(0));
        pl1.add(dataTest.getPersons().get(2));
        pl2.add(dataTest.getPersons().get(1));
        pl3.add(dataTest.getPersons().get(3));
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
        assertThat(getfloodStations).hasSize(3);
        assertThat(personFloodAndFires).hasSize(2);
        assertThat(personFloodAndFires).extracting("name","phone","medications","allergies")
                .contains(  tuple(pl1.get(0).getFirstName() + " " + pl1.get(0).getLastName(), pl1.get(0).getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(pl1.get(1).getFirstName() + " " + pl1.get(1).getLastName(), pl1.get(1).getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()));
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        List<Firestation> emptylist = new ArrayList<>();
        when(dao.findFirestationsByNumber(anyString())).thenReturn(emptylist);
        //ACT
        assertThrows(NoEntryException.class, () ->  getService.floodstations("5"));
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}