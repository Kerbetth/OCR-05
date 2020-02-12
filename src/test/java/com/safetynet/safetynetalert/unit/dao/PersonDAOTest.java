package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.DaoTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.persandmedservice.PersonService;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonDAOTest {

    private DataTest dataTest;
    @Mock
    static Logger loggerMock;

    @Spy
    DaoTest dao = new DaoTest();

    @InjectMocks
    PersonService personService = new PersonService();

    @Before
    public void setup() {
        dataTest = new DataTest();
        personService.logger = loggerMock;
        doNothing().when(dao).writer(any());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Person p = dataTest.getNewPerson();

        //ACT
        personService.addPerson(p);

        //ASSERT
        assertEquals(personService.getDtb().getPersons().size(), personService.getDtb().getMedicalrecords().size());
        assertEquals(5, personService.getDtb().getPersons().size());
        assertThat(personService.getDtb().getPersons().get(4)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Person p = dataTest.getPersons().get(0);
        p.setAddress("modified");
        p.setZip(5542);
        p.setPhone("5544488775");
        //ACT
        personService.setPerson(p.getFirstName()+p.getLastName(), p);

        //ASSERT
        assertEquals(4, personService.getDtb().getPersons().size());
        assertThat(personService.getDtb().getPersons().get(0)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        personService.deleteMedicalRecordAndPersonEntry("JohnSchaffer");

        //ASSERT
        assertEquals(3, personService.getDtb().getPersons().size());
        assertEquals(3, personService.getDtb().getPersons().size());
    }
}