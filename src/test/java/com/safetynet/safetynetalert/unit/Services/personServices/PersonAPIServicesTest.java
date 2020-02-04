package com.safetynet.safetynetalert.unit.Services.personServices;

import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.personservices.PersonAPIService;
import com.safetynet.safetynetalert.unit.DataTest;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonAPIServicesTest {

    private DataTest dataTest = new DataTest();
    Map<String, String> person = new HashMap<>();

    @Mock
    static DTO dTOPersonsMock;

    @InjectMocks
    PersonAPIService aPIService;

    @BeforeEach
    private void setup() {
        aPIService = new PersonAPIService();
        aPIService.dTOPersons = dTOPersonsMock;
    }


    @Test
    public void returnPersonMapContentWithCorrectDataEvenWithUselessEntryValueForPost() throws ParseException {
        // Map person contain an entry named "Age", which do not belong of "persons" entries, so the Map contains
        // 8 entries instead of the 7 required, let see if this supplement entry is correctly ignored:

        //ARRANGE
        doNothing().when(dTOPersonsMock).addData(any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        person = dataTest.getPersonData(0);
        person.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));

        //ACT
        aPIService.postMethod(person);

        //ASSERT

        verify(dTOPersonsMock).addData(personArgCapt.capture());
        assertEquals(person.get(DataEntry.FNAME.getString()), personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(person.get(DataEntry.LNAME.getString()), personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertEquals(person.get(DataEntry.ADDRESS.getString()), personArgCapt.getValue().get(DataEntry.ADDRESS.getString()));
        assertEquals(person.get(DataEntry.CITY.getString()), personArgCapt.getValue().get(DataEntry.CITY.getString()));
        assertEquals(person.get(DataEntry.ZIP.getString()), personArgCapt.getValue().get(DataEntry.ZIP.getString()));
        assertEquals(person.get(DataEntry.PHONE.getString()), personArgCapt.getValue().get(DataEntry.PHONE.getString()));
        assertEquals(person.get(DataEntry.EMAIL.getString()), personArgCapt.getValue().get(DataEntry.EMAIL.getString()));
        assertEquals(7, personArgCapt.getValue().size());
    }

    @Test
    public void returnErrorIfSomeFieldIsMissing() throws ParseException {
        //ARRANGE
        person = dataTest.getPersonData(0);
        person.remove(DataEntry.FNAME.getString());

        //ACT
        String test = aPIService.postMethod(person);

        //ASSERT
        assertEquals("The " + DataEntry.FNAME.getString() + " value is not specify, operation aborted", test);
    }


    @Test
    public void returnPersonMapContentWithCorrectDataEvenWithUselessEntryValueForPut() throws ParseException {
        //ARRANGE
        doNothing().when(dTOPersonsMock).setData(anyInt(), any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        person = dataTest.getPersonData(0);
        person.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));
        String firstNameLastName = DataEntry.FNAME.getString()+DataEntry.LNAME.getString();
        when(dTOPersonsMock.getIdByName(firstNameLastName)).thenReturn(1);
        when(dTOPersonsMock.getDataTypeContentwithID(anyInt())).thenReturn(dataTest.getPersonData(1));

        //ACT
        aPIService.putMethod(firstNameLastName,person);

        //ASSERT
        verify(dTOPersonsMock, times(1)).setData(any(),personArgCapt.capture());
        assertEquals(false, person.get(DataEntry.FNAME.getString())== personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(false, person.get(DataEntry.LNAME.getString())== personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertEquals(person.get(DataEntry.ADDRESS.getString()), personArgCapt.getValue().get(DataEntry.ADDRESS.getString()));
        assertEquals(person.get(DataEntry.CITY.getString()), personArgCapt.getValue().get(DataEntry.CITY.getString()));
        assertEquals(person.get(DataEntry.ZIP.getString()), personArgCapt.getValue().get(DataEntry.ZIP.getString()));
        assertEquals(person.get(DataEntry.PHONE.getString()), personArgCapt.getValue().get(DataEntry.PHONE.getString()));
        assertEquals(person.get(DataEntry.EMAIL.getString()), personArgCapt.getValue().get(DataEntry.EMAIL.getString()));
        assertEquals(7, personArgCapt.getValue().size());
    }

    @Test
    public void returnPersonMapContentWithCorrectDataAfterPartialModificationForPut() throws ParseException {
        //ARRANGE
        doNothing().when(dTOPersonsMock).setData(anyInt(), any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        person = dataTest.getPersonData(0);
        person.remove(DataEntry.CITY.getString());
        person.remove(DataEntry.PHONE.getString());
        String firstNameLastName = DataEntry.FNAME.getString()+DataEntry.LNAME.getString();
        when(dTOPersonsMock.getIdByName(firstNameLastName)).thenReturn(1);
        when(dTOPersonsMock.getDataTypeContentwithID(anyInt())).thenReturn(dataTest.getPersonData(1));

        //ACT
        aPIService.putMethod(firstNameLastName,person);

        //ASSERT
        verify(dTOPersonsMock, times(1)).setData(any(),personArgCapt.capture());
        assertEquals(false, person.get(DataEntry.FNAME.getString())== personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(false, person.get(DataEntry.LNAME.getString())== personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertEquals(person.get(DataEntry.ADDRESS.getString()), personArgCapt.getValue().get(DataEntry.ADDRESS.getString()));
        assertNotEquals(null, personArgCapt.getValue().get(DataEntry.CITY.getString()));
        assertEquals(person.get(DataEntry.ZIP.getString()), personArgCapt.getValue().get(DataEntry.ZIP.getString()));
        assertNotEquals(null, personArgCapt.getValue().get(DataEntry.PHONE.getString()));
        assertEquals(person.get(DataEntry.EMAIL.getString()), personArgCapt.getValue().get(DataEntry.EMAIL.getString()));
        assertEquals(7, personArgCapt.getValue().size());
    }

    @Test
    public void personMapIsDeletedWithDelete() throws ParseException {
        //ARRANGE
        String name = "name";
        when(dTOPersonsMock.getIdByName(name)).thenReturn(1);
        doNothing().when(dTOPersonsMock).removeData(1);

        //ACT
        aPIService.deleteMethod(name);

        //ASSERT
        verify(dTOPersonsMock, times(1)).removeData(1);
    }
}