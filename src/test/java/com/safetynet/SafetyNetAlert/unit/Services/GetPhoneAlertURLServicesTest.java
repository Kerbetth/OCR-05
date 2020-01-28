package com.safetynet.SafetyNetAlert.unit.Services;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.*;
import org.assertj.core.api.Assertions;
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
public class GetPhoneAlertURLServicesTest {

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
    public void returnPhoneAlertMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        getURLService = new GetPhoneAlertURLService(1, dTOPersons);
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOPersons.getFirestationAddress(1)).thenReturn("3333 broadway");
        ArrayList<String> phoneNumberlist = new ArrayList<>();
        phoneNumberlist.add(dataTest.getPersonsPhoneList().get(0));
        phoneNumberlist.add(dataTest.getPersonsPhoneList().get(2));
        //ACT
        String output= getURLService.getRequest();
        json = (Map) parser.parse(output);
        //ASSERT
        assertEquals(phoneNumberlist, json.get(DataEntry.PHONEALERT.getString()));
        assertEquals(1, json.size());
        assertEquals(2, json.get(DataEntry.PHONEALERT.getString()).size());
    }

    @Test
    public void returnAllPhoneAlertMapContentIfNoStationNumber() throws ParseException {
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
    public void returnNoPhoneAlertMapContentIfNoStationNumberAffiliate() throws ParseException {
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
