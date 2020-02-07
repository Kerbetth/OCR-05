package com.safetynet.safetynetalert.unit.dto;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.dao.JsonWriter;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonDAOTest {

    private DataTest dataTest;

    @Mock
    static Database databaseMock;
    @Mock
    static JsonWriter jsonWriterMock;

    PersonDao personDao = new PersonDao();

    @Before
    public void setup() {
        dataTest = new DataTest();
        personDao.database =databaseMock;
        personDao.jsonWriter =jsonWriterMock;
        when(databaseMock.getPersons()).thenReturn(dataTest.getPersonlist());
        when(databaseMock.getMedicalrecords()).thenReturn(dataTest.getMedicalrecords());
        doNothing().when(jsonWriterMock).writer(any(),anyString());
        doNothing().when(databaseMock).setPersons(anyList());
        doNothing().when(databaseMock).setMedicalrecords(anyList());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        ArgumentCaptor<List<Person>> personsArgCapt = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<Medicalrecord>> medicalRecordArgCapt = ArgumentCaptor.forClass(List.class);
        Person p = dataTest.getNewPerson();

        //ACT
        personDao.addPerson(p);

        //ASSERT
        verify(databaseMock).setPersons(personsArgCapt.capture());
        verify(databaseMock).setMedicalrecords(medicalRecordArgCapt.capture());
        assertEquals(personsArgCapt.getValue().size(), medicalRecordArgCapt.getValue().size());
        assertEquals(5, personsArgCapt.getValue().size());
        assertThat(personsArgCapt.getValue().get(4)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Person p = dataTest.getPersonlist().get(0);
        p.setAddress("modified");
        p.setZip(5542);
        p.setPhone("5544488775");
        //ACT
        personDao.setPerson(p);

        //ASSERT
        assertEquals(4, databaseMock.getPersons().size());
        assertThat(databaseMock.getPersons().get(0)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains(p.getFirstName(), p.getLastName(), p.getAddress(),p.getCity(),p.getZip(), p.getEmail(),p.getPhone());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        personDao.deleteMedicalRecordAndPersonEntry(0);

        //ASSERT
        assertEquals(3, databaseMock.getPersons().size());
        assertEquals(3, databaseMock.getMedicalrecords().size());
    }
}