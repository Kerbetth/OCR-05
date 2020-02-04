package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.getservices.GetChildAlertURLService;
import com.safetynet.safetynetalert.domain.Child;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.unit.DataTest;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetChildAlertURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;

    @InjectMocks
    GetService getService;



    @Test
    public void returnChildAlertMapContentWithCorrectData() throws ParseException {
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersonlist();
        addressPerson.remove(1);
        addressPerson.remove(3);
        when(dao.findPersonByAddress(dataTest.getPersonlist().get(0).getAddress())).thenReturn(addressPerson);
        ArrayList<String> householdMembersList = new ArrayList<>();
        householdMembersList.add("Jeff Loomis");
        //ACT
        List<Child> children = getService.childAlert(dataTest.getPersonlist().get(0).getAddress());

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
        getService = new GetChildAlertURLService("Edelstein", dao, dTOMedrec);
        when(dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(dTOMedrec.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(dTOMedrec.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(dao.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        //ACT
        String output= getService.getRequest();
        json = (ArrayList) parser.parse(output);
        //ASSERT
        assertEquals(0, json.size());
    }

    @Test
    public void returnAllChildAlertDataifNoAddressSpecify() throws ParseException {
        //ARRANGE
        getService = new GetChildAlertURLService(null, dao, dTOMedrec);
        when(dTOMedrec.getData(DataEntry.AGE)).thenReturn(dataTest.getPersonsAgeList());
        when(dTOMedrec.getData(DataEntry.FNAME)).thenReturn(dataTest.getPersonsFirstNameList());
        when(dTOMedrec.getData(DataEntry.LNAME)).thenReturn(dataTest.getPersonsLastNameList());
        when(dao.getData(DataEntry.ADDRESS)).thenReturn(dataTest.getPersonsAddressList());
        //ACT
        String output= getService.getRequest();
        json = (ArrayList) parser.parse(output);
        //ASSERT
        assertEquals(2, json.size());
    }
}