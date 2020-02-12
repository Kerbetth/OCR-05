package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.DaoTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.domain.*;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class APIGetIT {

    DataTest dataTest;
    @Spy
    private DaoTest dao;
    @Spy
    private Logger logger = LogManager.getLogger("GetServiceTest");

    Person p1;
    Person p2;
    Person p3;

    @InjectMocks
    private GetService getService;

    @Before
    public void setup() {
        WritingCleanJsonData.writingCleanJsonDataTest();
        dataTest = new DataTest();
        p1 = dataTest.getPersons().get(0);
        p2 = dataTest.getPersons().get(1);
        p3 = dataTest.getPersons().get(2);
    }

    @Test
    public void returnCorrectChildAlertData() {
        //ACT
        List<Child> children = getService.childAlert("3333 broadway");
        //ASSERT
        Assertions.assertEquals(1, children.size());
        Assertions.assertEquals("JohnSchaffer", children.get(0).getFirstName() + children.get(0).getLastName());
    }

    @Test
    public void throwErrorIfAddressDoesntExistForChildAlert() {
        //ACT
        assertThrows(NoEntryException.class, () -> getService.childAlert("noaddress"));
        //ASSERT
        verify(logger, times(1)).error(anyString());
    }

    @Test
    public void returnCorrectCommunityEmailData() {
        //ACT
        List<String> emailList = getService.communityEmail("NYC");
        //ASSERT
        Assertions.assertEquals(2, emailList.size());
        Assertions.assertEquals(dataTest.getPersons().get(0).getEmail(), emailList.get(0));
        Assertions.assertEquals(dataTest.getPersons().get(2).getEmail(), emailList.get(1));
    }

    @Test
    public void returnCorrectFirestationData() {
        //ACT
        List<Object> getfirestation = getService.firestation(1);

        //ASSERT
        Count count = (Count) getfirestation.get(1);
        List<PersonFirestation> personFirestation = (List<PersonFirestation>) getfirestation.get(0);
        assertEquals(2, getfirestation.size());
        assertEquals(2, count.getAdults());
        assertEquals(1, count.getChildren());

        assertThat(personFirestation).extracting("firstName", "lastName", "address", "phone")
                .contains(tuple(p1.getFirstName(), p1.getLastName(), p1.getAddress(), p1.getPhone()),
                        tuple(p2.getFirstName(), p2.getLastName(), p2.getAddress(), p2.getPhone()),
                        tuple(p3.getFirstName(), p3.getLastName(), p3.getAddress(), p3.getPhone()));
    }

    @Test
    public void returnCorrectFireData() {
        //ACT
        List<Object> getfire = getService.fire(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = (List<PersonFloodAndFire>) getfire.get(1);
        assertEquals(2, getfire.size());
        assertEquals(1, (Integer) getfire.get(0));
        assertEquals(2, personFloodAndFires.size());
        assertThat(personFloodAndFires).extracting("name", "phone", "medications", "allergies")
                .contains(tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnCorrectFloodStationData() {
        //ACT
        List<HouseHold> getfloodStations = getService.floodstations("1,2");

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = getfloodStations.get(0).getPersonList();
        assertEquals(3, getfloodStations.size());
        assertEquals(2, personFloodAndFires.size());

        assertThat(personFloodAndFires).extracting("name", "phone", "medications", "allergies")
                .contains(tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnCorrectPersonInfoData() {
        //ACT
        List<PersonInfo> personInfos = getService.personInfo("John","Schaffer");

        //ASSERT
        assertEquals(1, personInfos.size());
        assertThat(personInfos.get(0)).extracting("name","address","email","medications","allergies")
                .contains(p1.getFirstName() + " " + p1.getLastName(), p1.getAddress(), p1.getEmail(),dataTest.getMedicationList1(), dataTest.getMedicationList2());
    }

    @Test
    public void returnCorrectPhoneAlertData() {
        //ACT
        Set<String> phoneAlert = getService.phoneAlert(1);
        //ASSERT
        assertEquals(3, phoneAlert.size());
    }

    @Test
    public void returnErrorIfStationNumberDoesntExist() {
        //ACT
        assertThrows(NullPointerException.class, () ->  getService.phoneAlert(0));
        //ASSERT
        verify(logger, times(1)).error(anyString());
    }

}
