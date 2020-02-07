package com.safetynet.safetynetalert.unit.dto;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.dao.JsonWriter;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DAOTest {

    private DataTest dataTest;

    @Mock
    static JsonWriter jsonWriterMock;

    Dao dao = new Dao();

    @Before
    public void setup() {
        dataTest = new DataTest();
        dao.database.setPersons(dataTest.getPersonlist());
        dao.database.setMedicalrecords(dataTest.getMedicalrecords());
        dao.database.setFirestations(dataTest.getFirestations());
        dao.jsonWriter =jsonWriterMock;
    }

    @Test
    public void returnCorrectPersonByGivingTheName() {
        //ACT
        Person byName = dao.findPersonByName(
                dataTest.getPersonlist().get(0).getFirstName()+
                dataTest.getPersonlist().get(0).getLastName());
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersonlist().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getPersonlist().get(0)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains(byName.getFirstName(), byName.getLastName(), byName.getAddress(),byName.getCity(),byName.getZip(), byName.getEmail(),byName.getPhone());

        assertThat(byAddress.size()).isEqualTo(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheAddress() {
        //ACT
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersonlist().get(0).getAddress());

        //ASSERT
        assertThat(byAddress.size()).isEqualTo(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheCity() {
        //ACT
        List<Person> byAddress = dao.findPersonByCity(
                dataTest.getPersonlist().get(0).getCity());

        //ASSERT
        assertThat(byAddress.size()).isEqualTo(2);
    }

    @Test
    public void returnCorrectMedRecAccordingToThePerson() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByPerson(
                dataTest.getPersonlist().get(0));

        //ASSERT
        assertThat(dataTest.getPersonlist().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersonlist().get(0).getLastName()).isEqualTo(med.getLastName());
    }

    @Test
    public void returnCorrectMedRecAccordingToTheID() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByID(0);

        //ASSERT
        assertThat(dataTest.getPersonlist().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersonlist().get(0).getLastName()).isEqualTo(med.getLastName());
    }

    @Test
    public void returnCorrectFirestationAccordingToTheAddress() {
        //ACT
        Firestation firestation = dao.findFirestationByAddress(dataTest.getPersonlist().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getFirestation1().getAddress()).isEqualTo(firestation.getAddress());
    }

    @Test
    public void returnCorrectFirestationsAccordingToTheNumberOfStation() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1");

        //ASSERT
        assertThat(dataTest.getFirestations().get(0)).isEqualTo(firestations.get(0));
        assertThat(dataTest.getFirestations().get(1)).isEqualTo(firestations.get(1));
        assertEquals(2,firestations.size());
    }

    @Test
    public void ignoreNumbersOfFirestationThatDoesntExist() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1,2,3");

        //ASSERT
        assertThat(firestations.get(0).getStation()).isEqualTo(1);
        assertThat(firestations.get(1).getStation()).isEqualTo(1);
        assertThat(firestations.get(2).getStation()).isEqualTo(2);
        assertEquals(3,firestations.size());
    }

    @Test
    public void returnCorrectOfPersonWithTheSameName() {
        //ACT
        List<Person> persons = dao.findPersonsWithSameFirstNameOrLastName(dataTest.getPersonlist().get(1).getFirstName(),dataTest.getPersonlist().get(1).getLastName());

        //ASSERT
        assertThat(persons.get(0).getLastName()).isEqualTo(persons.get(1).getLastName());
        assertEquals(2,persons.size());
    }
}