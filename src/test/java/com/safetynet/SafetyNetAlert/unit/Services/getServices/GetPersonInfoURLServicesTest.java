package com.safetynet.SafetyNetAlert.unit.Services.getServices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.*;
import com.safetynet.SafetyNetAlert.unit.DataTest;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPersonInfoURLServicesTest {

    private DataTest dataTest = new DataTest();
    JSONParser parser = new JSONParser();
    ArrayList<Map<String, Object>> json;
    Map<String, String> name = new HashMap<>();

    @Mock
    static DTO dTOPersons;
    @Mock
    static DTO dTOMedrec;


    @InjectMocks
    GetPersonInfoURLService getURLService;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void returnPersonInfoMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        name.put(DataEntry.FNAME.getString(), dataTest.getPersonsFirstNameList().get(0).toString());
        name.put(DataEntry.LNAME.getString(), dataTest.getPersonsLastNameList().get(0).toString());
        getURLService = new GetPersonInfoURLService(name, dTOPersons, dTOMedrec);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.EMAIL)).thenReturn(dataTest.getPersonsEmailList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());

        //ACT
        String output = getURLService.getRequest();
        json = (ArrayList<Map<String, Object>>) parser.parse(output);

        //ASSERT
        ArrayList<String> medicationlist = (ArrayList<String>) json.get(0).get(DataEntry.MEDIC.getString());
        ArrayList<String> allergieslist = (ArrayList<String>) json.get(0).get(DataEntry.MEDIC.getString());

        assertEquals(dataTest.getPersonsFirstNameList().get(0), json.get(0).get(DataEntry.FNAME.getString()));
        assertEquals(dataTest.getPersonsLastNameList().get(0), json.get(0).get(DataEntry.LNAME.getString()));
        assertEquals(dataTest.getPersonsAddressList().get(0), json.get(0).get(DataEntry.ADDRESS.getString()));
        assertEquals(dataTest.getPersonsEmailList().get(0), json.get(0).get(DataEntry.EMAIL.getString()));
        ArrayList<String> johnMedlist = (ArrayList<String>) dataTest.getMedMedicationsList().get(0);
        for (int i = 0; i < johnMedlist.size(); i++) {
            assertEquals(johnMedlist.get(i), "\"" + medicationlist.get(i)+ "\"" );
        }
        ArrayList<String> johnAllerglist = (ArrayList<String>) dataTest.getMedAllergiesList().get(0);
        for (int i = 0; i < johnAllerglist.size(); i++) {
            assertEquals(johnAllerglist.get(i), "\"" + allergieslist.get(i)+ "\"" );
        }
        assertEquals(dataTest.getPersonsAgeList().get(0), json.get(0).get(DataEntry.AGE.getString()));
        assertEquals(1, json.size());
    }


    @Test
    public void returnNoPersonInfoIfNameDoesntExist() throws ParseException {
        //ARRANGE
        name.put(DataEntry.FNAME.getString(), "nonname");
        name.put(DataEntry.FNAME.getString(), "lastnonname");
        getURLService = new GetPersonInfoURLService(name, dTOPersons, dTOMedrec);
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.EMAIL)).thenReturn(dataTest.getPersonsEmailList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());

        //ACT
        String output = getURLService.getRequest();
        json = (ArrayList<Map<String, Object>>) parser.parse(output);

        //ASSERT
        assertEquals(0, json.size());
    }
}
