package com.safetynet.SafetyNetAlert.unit.Services.personServices;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.medicalrecordservices.MedicalRecordAPIService;
import com.safetynet.SafetyNetAlert.unit.DataTest;
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
public class MedicalRecordsAPIServicesTest {

    private DataTest dataTest = new DataTest();
    Map<String, String> medicalRecord = new HashMap<>();

    @Mock
    static DTO dTOMedrecMock;

    @InjectMocks
    MedicalRecordAPIService aPIService;

    @BeforeEach
    private void setup() {
        aPIService = new MedicalRecordAPIService();
        aPIService.dTOMedrec = dTOMedrecMock;
    }


    @Test
    public void returnPersonMapContentWithCorrectDataEvenWithUselessEntryValueForPost() throws ParseException {
        // Map person contain an entry named "Age", which do not belong of "persons" entries, so the Map contains
        // 8 entries instead of the 7 required, let see if this supplement entry is correctly ignored:

        //ARRANGE
        doNothing().when(dTOMedrecMock).addData(any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        medicalRecord = dataTest.getMedrecData(0);
        medicalRecord.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));

        //ACT
        aPIService.postMethod(medicalRecord);

        //ASSERT

        verify(dTOMedrecMock).addData(personArgCapt.capture());
        assertEquals(medicalRecord.get(DataEntry.FNAME.getString()), personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(medicalRecord.get(DataEntry.LNAME.getString()), personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertEquals(medicalRecord.get(DataEntry.BIRTHDATE.getString()), personArgCapt.getValue().get(DataEntry.BIRTHDATE.getString()));
        assertEquals(medicalRecord.get(DataEntry.MEDIC.getString()), personArgCapt.getValue().get(DataEntry.MEDIC.getString()));
        assertEquals(5, personArgCapt.getValue().size());
    }

    @Test
    public void returnErrorIfSomeFieldIsMissing() throws ParseException {
        //ARRANGE
        medicalRecord = dataTest.getPersonData(0);
        medicalRecord.remove(DataEntry.FNAME.getString());

        //ACT
        String test = aPIService.postMethod(medicalRecord);

        //ASSERT
        assertEquals("The " + DataEntry.FNAME.getString() + " value is not specify, operation aborted", test);
    }


    @Test
    public void returnPersonMapContentWithCorrectDataEvenWithUselessEntryValueForPut() throws ParseException {
        //ARRANGE
        doNothing().when(dTOMedrecMock).setData(anyInt(), any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        medicalRecord = dataTest.getMedrecData(0);
        medicalRecord.put(DataEntry.AGE.getString(), (String) dataTest.getPersonsAgeList().get(0));
        String firstNameLastName = DataEntry.FNAME.getString()+DataEntry.LNAME.getString();
        when(dTOMedrecMock.getIdByName(firstNameLastName)).thenReturn(1);
        when(dTOMedrecMock.getDataTypeContentwithID(anyInt())).thenReturn(dataTest.getMedrecData(1));

        //ACT
        aPIService.putMethod(firstNameLastName, medicalRecord);

        //ASSERT
        verify(dTOMedrecMock, times(1)).setData(any(),personArgCapt.capture());
        assertEquals(false, medicalRecord.get(DataEntry.FNAME.getString())== personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(false, medicalRecord.get(DataEntry.LNAME.getString())== personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertEquals(medicalRecord.get(DataEntry.BIRTHDATE.getString()), personArgCapt.getValue().get(DataEntry.BIRTHDATE.getString()));
        assertEquals(5, personArgCapt.getValue().size());
    }

    @Test
    public void returnPersonMapContentWithCorrectDataAfterPartialModificationForPut() throws ParseException {
        //ARRANGE
        doNothing().when(dTOMedrecMock).setData(anyInt(), any());
        ArgumentCaptor<Map> personArgCapt = ArgumentCaptor.forClass(Map.class);
        medicalRecord = dataTest.getMedrecData(0);
        medicalRecord.remove(DataEntry.MEDIC.getString());
        medicalRecord.remove(DataEntry.ALLERGI.getString());
        String firstNameLastName = DataEntry.FNAME.getString()+DataEntry.LNAME.getString();
        when(dTOMedrecMock.getIdByName(firstNameLastName)).thenReturn(1);
        when(dTOMedrecMock.getDataTypeContentwithID(anyInt())).thenReturn(dataTest.getMedrecData(1));

        //ACT
        aPIService.putMethod(firstNameLastName, medicalRecord);

        //ASSERT
        verify(dTOMedrecMock, times(1)).setData(any(),personArgCapt.capture());
        assertEquals(false, medicalRecord.get(DataEntry.FNAME.getString())== personArgCapt.getValue().get(DataEntry.FNAME.getString()));
        assertEquals(false, medicalRecord.get(DataEntry.LNAME.getString())== personArgCapt.getValue().get(DataEntry.LNAME.getString()));
        assertNotEquals(null, personArgCapt.getValue().get(DataEntry.MEDIC.getString()));
        assertEquals(medicalRecord.get(DataEntry.BIRTHDATE.getString()), personArgCapt.getValue().get(DataEntry.BIRTHDATE.getString()));
        assertNotEquals(null, personArgCapt.getValue().get(DataEntry.ALLERGI.getString()));
        assertEquals(5, personArgCapt.getValue().size());
    }

    @Test
    public void medicalRecordMapIsDeletedWithDelete() throws ParseException {
        //ARRANGE
        String name = "name";
        when(dTOMedrecMock.getIdByName(name)).thenReturn(1);
        doNothing().when(dTOMedrecMock).removeData(1);

        //ACT
        aPIService.deleteMethod(name);

        //ASSERT
        verify(dTOMedrecMock, times(1)).removeData(1);
    }
}