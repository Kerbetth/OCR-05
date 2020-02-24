package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DaoTest {

    private DataTest dataTest=new DataTest();

    @Autowired
    Dao dao;

    @Test
    public void returnCorrectPersonByGivingTheName() {

        //ACT
        Person byName = dao.findPersonByName(
                dataTest.getPersons().get(0).getFirstName()+
                dataTest.getPersons().get(0).getLastName());
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getPersons().get(0)).extracting(
                Person::getFirstName,
                Person::getLastName,
                Person::getAddress,
                Person::getCity,
                Person::getZip,
                Person::getPhone,
                Person::getEmail)
                .contains(byName.getFirstName(), byName.getLastName(), byName.getAddress(),byName.getCity(),byName.getZip(), byName.getEmail(),byName.getPhone());

        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheAddress() {
        //ACT
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheCity() {
        //ACT
        List<Person> byAddress = dao.findPersonByCity(
                dataTest.getPersons().get(0).getCity());

        //ASSERT
        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectMedRecAccordingToThePerson() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByPerson(
                dataTest.getPersons().get(0).getFirstName()+dataTest.getPersons().get(0).getLastName());

        //ASSERT
        assertThat(dataTest.getPersons().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersons().get(0).getLastName()).isEqualTo(med.getLastName());
    }

    @Test
    public void returnCorrectMedRecAccordingToTheID() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByID(0);

        //ASSERT
        assertThat(dataTest.getPersons().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersons().get(0).getLastName()).isEqualTo(med.getLastName());
    }

    @Test
    public void returnCorrectFirestationAccordingToTheAddress() {
        //ACT
        Firestation firestation = dao.findFirestationByAddress(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getFirestation1().getAddress()).isEqualTo(firestation.getAddress());
    }

    @Test
    public void returnCorrectFirestationsAccordingToTheNumberOfStation() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1");

        //ASSERT
        assertThat(dataTest.getFirestations().get(0).getStation()).isEqualTo(firestations.get(0).getStation());
        assertThat(dataTest.getFirestations().get(1).getAddress()).isEqualTo(firestations.get(1).getAddress());
        assertThat(firestations).hasSize(2);
    }

    @Test
    public void ignoreNumbersOfFirestationThatDoesntExist() {
        //ACT
        List<Firestation> firestations = dao.findFirestationsByNumber("1,2,3");

        //ASSERT
        assertThat(firestations.get(0).getStation()).isEqualTo(1);
        assertThat(firestations.get(1).getStation()).isEqualTo(1);
        assertThat(firestations.get(2).getStation()).isEqualTo(2);
        assertThat(firestations).hasSize(3);
    }

    @Test
    public void returnCorrectOfPersonWithTheSameName() {
        //ACT
        List<Person> persons = dao.findPersonsWithSameFirstNameOrLastName(dataTest.getPersons().get(1).getFirstName(),dataTest.getPersons().get(1).getLastName());

        //ASSERT
        assertThat(persons.get(0).getLastName()).isEqualTo(persons.get(1).getLastName());
        assertThat(persons).hasSize(2);
    }
}