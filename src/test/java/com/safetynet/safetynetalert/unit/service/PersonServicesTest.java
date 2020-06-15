package com.safetynet.safetynetalert.unit.service;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.persandmedservice.PersonService;
import com.safetynet.safetynetalert.domain.Person;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class PersonServicesTest {

    private DataTest dataTest;
    private Database database;
    @Mock
    static Logger loggerMock;
    @Mock
    JsonReaderWriter dao = new JsonReaderWriter("datatest.json");

    @InjectMocks
    PersonService personService = new PersonService();

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        personService.logger = loggerMock;
        doNothing().when(dao).writer(any());
        database = dataTest.getDatabase();
        when(dao.getDtb()).thenReturn(database);
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Person p = dataTest.getNewPerson();
        when(dao.findPersonByName(anyString())).thenReturn(null);
        //ACT
        personService.addPerson(p);

        //ASSERT
        assertThat(personService.getDtb().getPersons()).hasSize(5);
        assertThat(personService.getDtb().getMedicalrecords()).hasSize(5);
        assertThat(personService.getDtb().getPersons().get(4)).extracting(
                Person::getFirstName,
                Person::getLastName,
                Person::getAddress,
                Person::getCity,
                Person::getZip,
                Person::getPhone,
                Person::getEmail)
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        when(dao.findPersonByName(anyString())).thenReturn(database.getPersons().get(0));
        Person p = dataTest.getPersons().get(0);
        p.setAddress("modified");
        p.setZip(5542);
        p.setPhone("5544488775");
        //ACT
        personService.setPerson(p.getFirstName()+p.getLastName(), p);

        //ASSERT
        assertThat(personService.getDtb().getPersons()).hasSize(4);
        assertThat(personService.getDtb().getPersons().get(0)).extracting(
                Person::getFirstName,
                Person::getLastName,
                Person::getAddress,
                Person::getCity,
                Person::getZip,
                Person::getPhone,
                Person::getEmail)
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE

        //ACT
        personService.deleteMedicalRecordAndPersonEntry("JohnSchaffer");

        //ASSERT
        assertThat(personService.getDtb().getPersons()).hasSize(3);
        assertThat(personService.getDtb().getMedicalrecords()).hasSize(3);
    }
}