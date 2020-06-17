package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.SafetyNetAlertApplication;
import com.safetynet.safetynetalert.SafetyNetAlertApplicationTests;
import com.safetynet.safetynetalert.controllers.apicontrollers.GetControllers;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.domain.*;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class APIGetIT {

    @Autowired
    GetControllers getControllers;

    DataTest dataTest = new DataTest();
    Person p1;
    Person p2;
    Person p3;


    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        p1 = dataTest.getPersons().get(0);
        p2 = dataTest.getPersons().get(1);
        p3 = dataTest.getPersons().get(2);
    }

    @Test
    public void returnCorrectChildAlertData() {
        //ACT
        List<Child> children = getControllers.childAlert("3333 broadway");
        //ASSERT
        assertThat(children).hasSize(1);
        assertThat("JohnSchaffer").isEqualTo(children.get(0).getFirstName() + children.get(0).getLastName());
    }

    @Test
    public void throwErrorIfAddressDoesntExistForChildAlert() {
        //ACT
        assertThrows(NoEntryException.class, () -> getControllers.childAlert("noaddress"));
    }

    @Test
    public void returnCorrectCommunityEmailData() {
        //ACT
        List<String> emailList = getControllers.communityEmail("NYC");
        //ASSERT
        assertThat(emailList).hasSize(2);
    }

    @Test
    public void returnCorrectFirestationData() {
        //ACT
        FirestationGet getfirestation = getControllers.firestation(1);

        //ASSERT
        Count count = getfirestation.getCount();
        List<PersonFirestation> personFirestation = getfirestation.getPersonFirestations();
        assertThat(count.getAdults()).isEqualTo(2);
        assertThat(count.getChildren()).isEqualTo(1);
        assertThat(personFirestation).extracting(
                PersonFirestation::getFirstName,
                PersonFirestation::getLastName,
                PersonFirestation::getAddress,
                PersonFirestation::getPhone)
                .contains(tuple(p1.getFirstName(), p1.getLastName(), p1.getAddress(), p1.getPhone()),
                        tuple(p2.getFirstName(), p2.getLastName(), p2.getAddress(), p2.getPhone()),
                        tuple(p3.getFirstName(), p3.getLastName(), p3.getAddress(), p3.getPhone()));
    }

    @Test
    public void returnCorrectFireData() {
        //ACT
        Fire getfire = getControllers.fire(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = getfire.getPersonFloodAndFires();
        assertThat(getfire.getStationNumber()).isEqualTo(1);
        assertThat(personFloodAndFires).hasSize(2);
        assertThat(personFloodAndFires).extracting("name", "phone", "medications", "allergies")
                .contains(tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnCorrectFloodStationData() {
        //ACT
        List<HouseHold> getfloodStations = getControllers.floodStation("1,2");

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = getfloodStations.get(0).getPersonList();
        assertThat(getfloodStations).hasSize(3);
        assertThat(personFloodAndFires).hasSize(2);
        assertThat(personFloodAndFires).extracting("name", "phone", "medications", "allergies")
                .contains(tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnCorrectPersonInfoData() {
        //ACT
        List<PersonInfo> personInfos = getControllers.personInfo("John", "Schaffer");

        //ASSERT
        assertThat(personInfos).hasSize(1);
        assertThat(personInfos.get(0)).extracting("name", "address", "email", "medications", "allergies")
                .contains(p1.getFirstName() + " " + p1.getLastName(), p1.getAddress(), p1.getEmail(), dataTest.getMedicationList1(), dataTest.getMedicationList2());
    }

    @Test
    public void returnCorrectPhoneAlertData() {
        //ACT
        Set<String> phoneAlert = getControllers.phoneAlert(1);
        //ASSERT
        assertThat(phoneAlert).hasSize(3);
    }

    @Test
    public void returnErrorIfStationNumberDoesntExist() {
        //ACT
        assertThrows(NullPointerException.class, () -> getControllers.phoneAlert(0));
    }

}
