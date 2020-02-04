package com.safetynet.safetynetalert.unit.Services.personServices;

import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.firestationservices.FirestationAPIService;
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
public class FirestationAPIServicesTest {

    private DataTest dataTest = new DataTest();
    Map<String, String> firestation = new HashMap<>();

    @Mock
    static DTO dTOFirestationsMock;

    @InjectMocks
    FirestationAPIService aPIService;

    @BeforeEach
    private void setup() {
        aPIService = new FirestationAPIService();
        aPIService.dTOFirestation = dTOFirestationsMock;
    }


    @Test
    public void returnFirestationMapContentWithCorrectDataEvenWithUselessEntryValueForPost() throws ParseException {
        // Map person contain an entry named "Age", which do not belong of "persons" entries, so the Map contains
        // 8 entries instead of the 7 required, let see if this supplement entry is correctly ignored:

        //ARRANGE
        doNothing().when(dTOFirestationsMock).addData(any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        firestation = dataTest.getFirestationData(0);
        firestation.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));

        //ACT
        aPIService.postMethod(firestation);

        //ASSERT
        verify(dTOFirestationsMock).addData(personArgCapt.capture());
        assertEquals(firestation.get(DataEntry.ADDRESS.getString()), personArgCapt.getValue().get(DataEntry.ADDRESS.getString()));
        assertEquals(firestation.get(DataEntry.STATION.getString()), personArgCapt.getValue().get(DataEntry.STATION.getString()));
        assertEquals(2, personArgCapt.getValue().size());
    }

    @Test
    public void returnErrorIfSomeFieldIsMissing() throws ParseException {
        //ARRANGE
        firestation = dataTest.getFirestationData(0);
        firestation.remove(DataEntry.STATION.getString());

        //ACT
        String test = aPIService.postMethod(firestation);

        //ASSERT
        assertEquals("The " + DataEntry.STATION.getString() + " value is not specify, operation aborted", test);
    }


    @Test
    public void returnPersonMapContentWithCorrectDataEvenWithUselessEntryValueForPut() throws ParseException {
        //ARRANGE
        doNothing().when(dTOFirestationsMock).setData(anyInt(), any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        firestation = dataTest.getFirestationData(0);
        firestation.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));
        String address = "3333 broadway";
        when(dTOFirestationsMock.getIdByAddress(address)).thenReturn(1);
        when(dTOFirestationsMock.getDataTypeContentwithID(anyInt())).thenReturn(dataTest.getFirestationData(1));

        //ACT
        aPIService.putMethod(address, firestation);

        //ASSERT
        verify(dTOFirestationsMock, times(1)).setData(any(),personArgCapt.capture());
        assertEquals(false, firestation.get(DataEntry.ADDRESS.getString())== personArgCapt.getValue().get(DataEntry.ADDRESS.getString()));
        assertEquals(firestation.get(DataEntry.STATION.getString()), personArgCapt.getValue().get(DataEntry.STATION.getString()));
        assertEquals(2, personArgCapt.getValue().size());
    }

    @Test
    public void personMapIsDeletedWithDelete() throws ParseException {
        //ARRANGE
        String name = "name";
        when(dTOFirestationsMock.getIdByAddress(name)).thenReturn(1);
        doNothing().when(dTOFirestationsMock).removeData(1);

        //ACT
        aPIService.deleteMethod(name);

        //ASSERT
        verify(dTOFirestationsMock, times(1)).removeData(1);
    }
}