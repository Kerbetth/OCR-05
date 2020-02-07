package com.safetynet.safetynetalert.unit.Services.personServices;

import com.safetynet.safetynetalert.apiservices.PersonService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.DataTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonAPIServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao personDao;

    @InjectMocks
    PersonService aPIService;


    @Test
    public void returnPersonWithCorrectData() {

        //ARRANGE
        doNothing().when(personDao).addPerson(any());
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);

        //ACT
        aPIService.postPerson(dataTest.getPersonlist().get(0));

        //ASSERT
        verify(personDao).addPerson(personArgumentCaptor.capture());
        Person pl1 = dataTest.getPersonlist().get(0);
        assertThat(personArgumentCaptor.getValue()).extracting("firstName","lastName","address","city","zip","phone","email")
                .contains(pl1.getFirstName(), pl1.getLastName(), pl1.getAddress(), pl1.getCity(), pl1.getZip(), pl1.getPhone(), pl1.getEmail());
    }


    @Test
    public void returnPersonCorrectlyPut() {
        //ARRANGE
        doNothing().when(personDao).setPerson(any());
        when(personDao.findPersonByName(anyString())).thenReturn(dataTest.getPersonlist().get(0));
        ArgumentCaptor<Person> medicalRecordArgCapt = ArgumentCaptor.forClass(Person.class);

        //ACT
        aPIService.putPerson("name",dataTest.getPersonlist().get(1));

        //ASSERT
        verify(personDao).setPerson(medicalRecordArgCapt.capture());
        Person pl1 = dataTest.getPersonlist().get(0);
        Person pl2 = dataTest.getPersonlist().get(1);
        assertThat(medicalRecordArgCapt.getValue()).extracting("firstName","lastName","address","city","zip","phone","email")
                .contains(pl1.getFirstName(), pl1.getLastName(), pl2.getAddress(), pl2.getCity(), pl2.getZip(), pl2.getPhone(), pl2.getEmail());
    }

    @Test
    public void medicalRecordIsDeletedWithDelete() {
        //ARRANGE
        String name = "name";
        when(personDao.getIdByName(anyString())).thenReturn(1);
        ArgumentCaptor<Integer> numberArgCapt = ArgumentCaptor.forClass(Integer.class);

        //ACT
        ResponseEntity responseEntity = aPIService.deletePersonAndMedicalRecord("noname");

        //ASSERT
        verify(personDao).deleteMedicalRecordAndPersonEntry(numberArgCapt.capture());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, numberArgCapt.getValue());
    }
}