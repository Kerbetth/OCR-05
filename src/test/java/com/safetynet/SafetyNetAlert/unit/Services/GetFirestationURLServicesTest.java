package com.safetynet.SafetyNetAlert.unit.Services;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.getservices.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
    Map<String,ArrayList<Map<String,String>>> json;
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
        when(getURLService.dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOFirestation.getStationAddresses("1")).thenReturn(dataTest.getStationAddresses());
        //ACT
        String output= getURLService.getRequest();
        System.out.println(output);
        json = (Map) parser.parse(output);
        //ASSERT
        assertEquals("Jeff", json.get(DataEntry.PERSOBYSTATION.getString()).get(0).get(DataEntry.FNAME.getString()));
        assertEquals("Loomis", json.get(DataEntry.PERSOBYSTATION.getString()).get(0).get(DataEntry.LNAME.getString()));
        assertEquals("3333 broadway", json.get(DataEntry.PERSOBYSTATION.getString()).get(0).get(DataEntry.ADDRESS.getString()));
        assertEquals("000-666-750", json.get(DataEntry.PERSOBYSTATION.getString()).get(0).get(DataEntry.PHONE.getString()));
        assertEquals(2, json.get(DataEntry.PERSOBYSTATION.getString()).size());
    }

    @Test
    public void returnCommunityEmailMapContentWithCorrectData(){
        //ARRANGE
        GetCommunityEmailURLService getURLService = new GetCommunityEmailURLService("1");

        //when(getURLService.dTOPersons.getData("email")).thenReturn(dataTest.getPersonsEmailList());
        //when(getURLService.dTOPersons.getData("city")).thenReturn(dataTest.getPersonsCityList());
        //ACT
        String test= getURLService.getRequest();
        assertEquals(test, "test");
        /*personsList = (ArrayList) Arrays.asList(getURLService.getRequest());

        //ASSERT
        Assertions.assertThat(personsList.get(0).get("age") == "15");
        Assertions.assertThat(personsList.get(0).get("firstName") == "John");
        Assertions.assertThat(personsList.get(0).get("lastName") == "Schaffer");
        Assertions.assertThat(personsList.get(0).get("householdMembersList") == "[\"Jeff Loomis\"]");
        Assertions.assertThat(personsList.size()==1);*/
    }

    @Test
    public void returnFireMapContentWithCorrectData(){
        //ARRANGE
        GetFireURLService getURLService = new GetFireURLService("1");
        getURLService.dTOPersons = dTOPersons;
        getURLService.dTOMedrec = dTOMedrec;
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        //ACT
        String test= getURLService.getRequest();
        assertEquals(test, "test");
        /*personsList = (ArrayList) Arrays.asList(getURLService.getRequest());

        //ASSERT
        Assertions.assertThat(personsList.get(0).get("age") == "15");
        Assertions.assertThat(personsList.get(0).get("firstName") == "John");
        Assertions.assertThat(personsList.get(0).get("lastName") == "Schaffer");
        Assertions.assertThat(personsList.get(0).get("householdMembersList") == "[\"Jeff Loomis\"]");
        Assertions.assertThat(personsList.size()==1);*/
    }

    @Test
    public void returnFloodStationsMapContentWithCorrectData(){
        //ARRANGE
        GetFloodStationsURLService getURLService = new GetFloodStationsURLService("1");
        getURLService.dTOPersons = dTOPersons;
        getURLService.dTOMedrec = dTOMedrec;
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.PHONE)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOPersons.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        //ACT
        String test= getURLService.getRequest();
        assertEquals(test, "test");
        /*personsList = (ArrayList) Arrays.asList(getURLService.getRequest());

        //ASSERT
        Assertions.assertThat(personsList.get(0).get("age") == "15");
        Assertions.assertThat(personsList.get(0).get("firstName") == "John");
        Assertions.assertThat(personsList.get(0).get("lastName") == "Schaffer");
        Assertions.assertThat(personsList.get(0).get("householdMembersList") == "[\"Jeff Loomis\"]");
        Assertions.assertThat(personsList.size()==1);*/
    }

    @Test
    public void returnPersonInfoMapContentWithCorrectData(){
        //ARRANGE
        GetFloodStationsURLService getURLService = new GetFloodStationsURLService("1");
        getURLService.dTOPersons = dTOPersons;
        getURLService.dTOMedrec = dTOMedrec;
        when(getURLService.dTOPersons.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(getURLService.dTOPersons.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(getURLService.dTOPersons.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        when(getURLService.dTOPersons.getData(DataEntry.EMAIL)).thenReturn(dataTest.getPersonsPhoneList());
        when(getURLService.dTOMedrec.getData(DataEntry.MEDIC)).thenReturn(dataTest.getMedMedicationsList());
        when(getURLService.dTOMedrec.getData(DataEntry.ALLERGI)).thenReturn(dataTest.getMedAllergiesList());
        when(getURLService.dTOPersons.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        String test= getURLService.getRequest();
        assertEquals(test, "test");
        /*personsList = (ArrayList) Arrays.asList(getURLService.getRequest());

        //ASSERT
        Assertions.assertThat(personsList.get(0).get("age") == "15");
        Assertions.assertThat(personsList.get(0).get("firstName") == "John");
        Assertions.assertThat(personsList.get(0).get("lastName") == "Schaffer");
        Assertions.assertThat(personsList.get(0).get("householdMembersList") == "[\"Jeff Loomis\"]");
        Assertions.assertThat(personsList.size()==1);*/
    }

}
