package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.getservices.GetFireURLService;
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
public class GetFireURLServicesTest {

    private DataTest dataTest = new DataTest();
    JSONParser parser = new JSONParser();
    Map<String, Object> json;

    @Mock
    static DTO dTOPersons;
    @Mock
    static DTO dTOMedrec;

    @InjectMocks
    GetFireURLService getURLService;


    @Test
    public void returnFireMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        getURLService = new GetFireURLService("3333 broadway", dTOPersons, dTOMedrec);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOPersons.getFirestationNumber("3333 broadway")).thenReturn("1");

        //ACT
        String output = getURLService.getRequest();
        json = (Map<String, Object>) parser.parse(output);

        //ASSERT
        ArrayList personslist = (ArrayList) json.get(DataEntry.PERSOBYSTATION.getString());
        Map<String, Object> person = (Map) personslist.get(0);
        ArrayList<Object> medicationlist = (ArrayList) person.get(DataEntry.MEDIC.getString());
        ArrayList<Object> allergieslist = (ArrayList) person.get(DataEntry.ALLERGI.getString());

        assertEquals(dataTest.getPersonsFirstNameList().get(0), person.get(DataEntry.FNAME.getString()));
        assertEquals(dataTest.getPersonsLastNameList().get(0), person.get(DataEntry.LNAME.getString()));
        assertEquals(dataTest.getPersonsPhoneList().get(0), person.get(DataEntry.PHONE.getString()));
        ArrayList<String> johnMedlist = (ArrayList<String>) dataTest.getMedMedicationsList().get(0);
        for (int i = 0; i < johnMedlist.size(); i++) {
            assertEquals(johnMedlist.get(i), "\"" + medicationlist.get(i) + "\"");
        }
        ArrayList<String> johnAllerglist = (ArrayList<String>) dataTest.getMedAllergiesList().get(0);
        for (int i = 0; i < johnAllerglist.size(); i++) {
            assertEquals(johnAllerglist.get(i), "\"" + allergieslist.get(i) + "\"");
        }
        assertEquals(dataTest.getPersonsAgeList().get(0), person.get(DataEntry.AGE.getString()));
        assertEquals(2, json.size());
        assertEquals("1", json.get("stationNumber").toString());
        assertEquals(2, personslist.size());
    }

    @Test
    public void returnNoFireIfStationNumberDoesntExist() throws ParseException {
        //ARRANGE
        getURLService = new GetFireURLService("noAddress", dTOPersons, dTOMedrec);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOPersons.getFirestationNumber("noAddress")).thenReturn("7");

        //ACT
        String output = getURLService.getRequest();
        json = (Map<String, Object>) parser.parse(output);

        //ASSERT
        ArrayList personslist = (ArrayList) json.get(DataEntry.PERSOBYSTATION.getString());
        assertEquals(2, json.size());
        assertEquals("7", json.get("stationNumber").toString());
        assertEquals(0, personslist.size());
    }


}
