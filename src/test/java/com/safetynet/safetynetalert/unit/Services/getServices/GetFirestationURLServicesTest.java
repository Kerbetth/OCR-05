package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.getservices.*;

import com.safetynet.safetynetalert.unit.DataTest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFirestationURLServicesTest {

    private DataTest dataTest = new DataTest();
    JSONParser parser = new JSONParser();
    Map<String, Object> json;
    @Mock
    static DTO dTOPersons;
    @Mock
    static DTO dTOMedrec;
    @Mock
    static DTO dTOFirestation;

    @InjectMocks
    GetFirestationURLService getURLService;



    @Test
    public void returnFirestationMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        getURLService = new GetFirestationURLService("1", dTOPersons, dTOMedrec, dTOFirestation);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOFirestation.getStationAddresses("1")).thenReturn(dataTest.getStationAddresses());

        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);

        //ASSERT
        ArrayList personslist = (ArrayList) json.get(DataEntry.PERSOBYSTATION.getString());
        Map<String, Object> person = (Map) personslist.get(1);
        Map<String, String> counting = (Map) json.get(DataEntry.COUNT.getString());


        assertEquals("1", counting.get(DataEntry.ADULTS.getString()));
        assertEquals("1", counting.get(DataEntry.CHILDREN.getString()));
        assertEquals("John", person.get(DataEntry.FNAME.getString()));
        assertEquals("Schaffer", person.get(DataEntry.LNAME.getString()));
        assertEquals("3333 broadway", person.get(DataEntry.ADDRESS.getString()));
        assertEquals("555-555-555", person.get(DataEntry.PHONE.getString()));
        assertEquals(2, personslist.size());
    }


    @Test
    public void returnNoFirestationMapContentIfStationNumberDoesntExist() throws ParseException {
        //ARRANGE
        getURLService = new GetFirestationURLService("8", dTOPersons, dTOMedrec, dTOFirestation);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOFirestation.getStationAddresses("8")).thenReturn(dataTest.getWrongStationAddresses());

        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);

        //ASSERT
        ArrayList personslist = (ArrayList) json.get(DataEntry.PERSOBYSTATION.getString());
        Map<String, String> counting = (Map) json.get(DataEntry.COUNT.getString());
        assertEquals("0", counting.get(DataEntry.ADULTS.getString()));
        assertEquals("0", counting.get(DataEntry.CHILDREN.getString()));
        assertEquals("0", counting.get(DataEntry.UNKNOWAGE.getString()));
        assertEquals(0, personslist.size());
        assertEquals(2, json.size());
    }

    @Test
    public void returnAllFirestationMapContentIfNoStationNumber() throws ParseException {
        //ARRANGE
        getURLService = new GetFirestationURLService(null, dTOPersons, dTOMedrec, dTOFirestation);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOFirestation.getStationAddresses(null)).thenReturn(null);

        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);

        //ASSERT
        ArrayList personslist = (ArrayList) json.get(DataEntry.PERSOBYSTATION.getString());
        Map<String, String> counting = (Map) json.get(DataEntry.COUNT.getString());
        assertEquals("1", counting.get(DataEntry.ADULTS.getString()));
        assertEquals("2", counting.get(DataEntry.CHILDREN.getString()));
        assertEquals("1", counting.get(DataEntry.UNKNOWAGE.getString()));
        assertEquals(4, personslist.size());
        assertEquals(2, json.size());
    }
}
