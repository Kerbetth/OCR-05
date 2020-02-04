package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.getservices.GetPhoneAlertURLService;
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
public class GetCommunityEmailURLServicesTest {

    private DataTest dataTest = new DataTest();
    JSONParser parser = new JSONParser();
    Map<String,ArrayList> json;
    @Mock
    static DTO dTOPersons;
    @Mock
    static DTO dTOMedrec;
    @Mock
    static DTO dTOFirestation;

    @InjectMocks
    GetPhoneAlertURLService getURLService;


    @Test
    public void returnCommunityEmailMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        getURLService = new GetPhoneAlertURLService(1, dTOPersons);
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOPersons.getFirestationAddress(1)).thenReturn("3333 broadway");
        ArrayList<String> phoneNumberlist = new ArrayList<>();
        phoneNumberlist.add(dataTest.getPersonsPhoneList().get(0).toString());
        phoneNumberlist.add(dataTest.getPersonsPhoneList().get(2).toString());
        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);
        //ASSERT
        assertEquals(phoneNumberlist, json.get(DataEntry.PHONEALERT.getString()));
        assertEquals(1, json.size());
        assertEquals(2, json.get(DataEntry.PHONEALERT.getString()).size());
    }

    @Test
    public void returnAllCommunityEmailMapContentIfNoStationNumber() throws ParseException {
        //ARRANGE
        getURLService = new GetPhoneAlertURLService(null, dTOPersons);
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOPersons.getFirestationAddress(null)).thenReturn(null);
        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);
        //ASSERT
        assertEquals(1, json.size());
        assertEquals(4, json.get(DataEntry.PHONEALERT.getString()).size());
    }

    @Test
    public void returnNoCommunityEmailMapContentIfNoStationNumberAffiliate() throws ParseException {
        //ARRANGE
        getURLService = new GetPhoneAlertURLService(5, dTOPersons);
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOPersons.getFirestationAddress(5)).thenReturn("randomAddress");
        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);
        //ASSERT
        assertEquals(1, json.size());
        assertEquals(0, json.get(DataEntry.PHONEALERT.getString()).size());
    }
}
