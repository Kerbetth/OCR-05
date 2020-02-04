package com.safetynet.safetynetalert.unit.dto;

import com.safetynet.safetynetalert.apiservices.dao.JSONDAO;
import com.safetynet.safetynetalert.apiservices.dto.DTO;
import com.safetynet.safetynetalert.apiservices.enumerations.DataEntry;
import com.safetynet.safetynetalert.apiservices.enumerations.Datatype;
import com.safetynet.safetynetalert.unit.DataTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DTOTest {

    private DataTest dataTest = new DataTest();


    DTO dto = new DTO(Datatype.PERSO);

    @Mock
    JSONDAO jsondaoMock;


    @Test
    public void returnCorrectDataWithGetData() {
        //ARRANGE
        dto.dataTypeContent = dataTest.get4PersonsData();

        //ACT
        ArrayList firstname = dto.getData(DataEntry.FNAME);
        ArrayList lastname = dto.getData(DataEntry.LNAME);
        ArrayList address = dto.getData(DataEntry.ADDRESS);
        ArrayList city = dto.getData(DataEntry.CITY);
        ArrayList zip = dto.getData(DataEntry.ZIP);
        ArrayList phone = dto.getData(DataEntry.PHONE);
        ArrayList email = dto.getData(DataEntry.EMAIL);

        //ASSERT
        for (int i = 0; i < firstname.size(); i++) {
            assertEquals(dataTest.getPersonsFirstNameList().get(i), firstname.get(i));
            assertEquals(dataTest.getPersonsLastNameList().get(i), lastname.get(i));
            assertEquals(dataTest.getPersonsAddressList().get(i), address.get(i));
            assertEquals(dataTest.getPersonsCityList().get(i), city.get(i));
            assertEquals(dataTest.getPersonsZipList().get(i), zip.get(i));
            assertEquals(dataTest.getPersonsPhoneList().get(i), phone.get(i));
            assertEquals(dataTest.getPersonsEmailList().get(i), email.get(i));
        }

        assertEquals(4, dto.dataTypeContent.size());
        assertEquals(7, dto.dataTypeContent.get(0).size());
    }

    @Test
    public void returnCorrectAge(){
        //ARRANGE
        dto.dataTypeContent = dataTest.get4MedrecData();

        //ACT
        ArrayList ages = dto.getData(DataEntry.AGE);

        //ASSERT
        for (int i = 0; i < ages.size(); i++) {
            assertEquals(2, ages.get(i).toString().length());
        }
    }

    @Test
    public void returnCorrectDataAfterAddData(){
        //ARRANGE
        dto.dataTypeContent = dataTest.get4PersonsData();
        dto.jsondao = jsondaoMock;
        ArgumentCaptor<Map> allDataCapture = ArgumentCaptor.forClass(Map.class);
        dto.allData = dataTest.getAllData();
        Map newPerson = dataTest.getNewPersonData();

        //ACT
        dto.addData(newPerson);

        //ASSERT
        verify(jsondaoMock).jsonWriter(allDataCapture.capture());
        Map<String,ArrayList<Map<String,String>>> finalData = (Map<String,ArrayList<Map<String,String>>>) allDataCapture.getValue();
        Map<String, String> personResult = finalData.get(Datatype.PERSO.getString()).get(4);
        assertEquals(newPerson.get(DataEntry.FNAME.getString()), personResult.get(DataEntry.FNAME.getString()));
        assertEquals(newPerson.get(DataEntry.LNAME.getString()), personResult.get(DataEntry.LNAME.getString()));
        assertEquals(newPerson.get(DataEntry.ADDRESS.getString()), personResult.get(DataEntry.ADDRESS.getString()));
        assertEquals(newPerson.get(DataEntry.CITY.getString()), personResult.get(DataEntry.CITY.getString()));
        assertEquals(newPerson.get(DataEntry.ZIP.getString()), personResult.get(DataEntry.ZIP.getString()));
        assertEquals(newPerson.get(DataEntry.PHONE.getString()), personResult.get(DataEntry.PHONE.getString()));
        assertEquals(newPerson.get(DataEntry.EMAIL.getString()), personResult.get(DataEntry.EMAIL.getString()));
    }

    @Test
    public void returnCorrectDataAfterSetData() {
        //ARRANGE
        dto.dataTypeContent = dataTest.get4PersonsData();
        dto.jsondao = jsondaoMock;
        ArgumentCaptor<Map> allDataCapture = ArgumentCaptor.forClass(Map.class);
        dto.allData = dataTest.getAllData();
        Map newPerson = dataTest.getNewPersonData();
        newPerson.remove(DataEntry.FNAME.getString());
        newPerson.remove(DataEntry.LNAME.getString());
        //ACT
        dto.setData(1, newPerson);

        //ASSERT
        verify(jsondaoMock).jsonWriter(allDataCapture.capture());
        Map<String,ArrayList<Map<String,String>>> finalData = (Map<String,ArrayList<Map<String,String>>>) allDataCapture.getValue();
        Map<String, String> personResult = finalData.get(Datatype.PERSO.getString()).get(1);
        assertEquals(newPerson.get(DataEntry.ADDRESS.getString()), personResult.get(DataEntry.ADDRESS.getString()));
        assertEquals(newPerson.get(DataEntry.CITY.getString()), personResult.get(DataEntry.CITY.getString()));
        assertEquals(newPerson.get(DataEntry.ZIP.getString()), personResult.get(DataEntry.ZIP.getString()));
        assertEquals(newPerson.get(DataEntry.PHONE.getString()), personResult.get(DataEntry.PHONE.getString()));
        assertEquals(newPerson.get(DataEntry.EMAIL.getString()), personResult.get(DataEntry.EMAIL.getString()));
    }

    @Test
    public void returnCorrectEntryNumberAfterDeletePersonsEntry() {
        //ARRANGE
        dto.dataTypeContent = dataTest.get4PersonsData();
        dto.jsondao = jsondaoMock;
        dto.allData = dataTest.getAllData();
        ArgumentCaptor<Map> allDataCapture = ArgumentCaptor.forClass(Map.class);

        //ACT
        dto.removeData(1);

        //ASSERT
        verify(jsondaoMock).jsonWriter(allDataCapture.capture());
        Map<String,ArrayList> finalData = (Map<String,ArrayList>) allDataCapture.getValue();
        assertEquals(3, finalData.get(Datatype.PERSO.getString()).size());
        assertEquals(3, finalData.get(Datatype.MEDREC.getString()).size());
    }

    @Test
    public void returnCorrectEntryNumberAfterDeleteMedicalRecordsEntry() {
        //ARRANGE
        dto = new DTO(Datatype.MEDREC);
        dto.dataTypeContent = dataTest.get4MedrecData();
        dto.jsondao = jsondaoMock;
        dto.allData = dataTest.getAllData();
        ArgumentCaptor<Map> allDataCapture = ArgumentCaptor.forClass(Map.class);

        //ACT
        dto.removeData(1);

        //ASSERT
        verify(jsondaoMock).jsonWriter(allDataCapture.capture());
        Map<String,ArrayList> finalData = (Map<String,ArrayList>) allDataCapture.getValue();
        assertEquals(3, finalData.get(Datatype.PERSO.getString()).size());
        assertEquals(3, finalData.get(Datatype.MEDREC.getString()).size());
    }

    @Test
    public void returnCorrectStationAddressAccordingToTheID() {
        //ARRANGE
        dto.dataTypeContent = dataTest.get4PersonsData();
        dto.jsondao = jsondaoMock;
        dto.allData = dataTest.getAllData();
        ArgumentCaptor<Map> allDataCapture = ArgumentCaptor.forClass(Map.class);

        //ACT
        dto.removeData(1);

        //ASSERT
        verify(jsondaoMock).jsonWriter(allDataCapture.capture());
        Map<String,ArrayList> finalData = (Map<String,ArrayList>) allDataCapture.getValue();
        assertEquals(3, finalData.get(Datatype.PERSO.getString()).size());
        assertEquals(3, finalData.get(Datatype.MEDREC.getString()).size());
    }

}