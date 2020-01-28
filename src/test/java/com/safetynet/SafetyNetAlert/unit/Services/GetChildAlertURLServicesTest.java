package com.safetynet.SafetyNetAlert.unit.Services;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.GetChildAlertURLService;
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
public class GetChildAlertURLServicesTest {

    private DataTest dataTest = new DataTest();
    JSONParser parser = new JSONParser();
    ArrayList<Map<String,String>> json;
    @Mock
    static DTO dTOPersons;
    @Mock
    static DTO dTOMedrec;

    @InjectMocks
    GetChildAlertURLService getURLService;



    @Test
    public void returnChildAlertMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        getURLService= new GetChildAlertURLService("3333 broadway", dTOPersons, dTOMedrec);
        when(dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(dTOMedrec.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(dTOMedrec.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        ArrayList<String> householdMembersList = new ArrayList<>();
        householdMembersList.add("Jeff Loomis");
        //ACT
        String output= getURLService.getRequest();
        //System.out.println(output);
        json = (ArrayList) parser.parse(output);
        //ASSERT
        assertEquals("John", json.get(0).get(DataEntry.FNAME.getString()));
        assertEquals("Schaffer", json.get(0).get(DataEntry.LNAME.getString()));
        assertEquals("15", json.get(0).get(DataEntry.AGE.getString()));
        assertEquals(householdMembersList, json.get(0).get(DataEntry.HOUSEMEMBERS.getString()));
        assertEquals(1, json.size());
    }

    @Test
    public void returnNoChildAlertDataIfNoChildInSpecifyAddress() throws ParseException {
        //ARRANGE
        getURLService= new GetChildAlertURLService("Edelstein", dTOPersons, dTOMedrec);
        when(dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(dTOMedrec.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(dTOMedrec.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        //ACT
        String output= getURLService.getRequest();
        json = (ArrayList) parser.parse(output);
        //ASSERT
        System.out.println(output);
        assertEquals(0, json.size());
    }

    @Test
    public void returnAllChildAlertDataifNoAddressSpecify() throws ParseException {
        //ARRANGE
        getURLService= new GetChildAlertURLService(null, dTOPersons, dTOMedrec);
        when(dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(dTOMedrec.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(dTOMedrec.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        //ACT
        String output= getURLService.getRequest();
        json = (ArrayList) parser.parse(output);
        //ASSERT
        System.out.println(output);
        assertEquals(2, json.size());
    }
}