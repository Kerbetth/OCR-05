package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.GetService;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Child;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.byLessThan;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetChildAlertURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static Dao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnChildAlertMapContentWithCorrectData() {
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(1));
        addressPerson.remove(addressPerson.get(2));
        String firstNameLastNamea = (addressPerson.get(0).getFirstName() + addressPerson.get(0).getLastName());
        String firstNameLastNameb = (addressPerson.get(1).getFirstName() + addressPerson.get(1).getLastName());
        when(dao.findPersonByAddress(dataTest.getPersons().get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(firstNameLastNamea)).thenReturn(dataTest.getMedicalrecords().get(0));
        when(dao.findMedicalrecordByPerson(firstNameLastNameb)).thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<Child> children = getService.childAlert(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(children.get(0).getFirstName()).isEqualTo(dataTest.getPersons().get(0).getFirstName());
        assertThat(children.get(0).getLastName()).isEqualTo(dataTest.getPersons().get(0).getLastName());
        assertThat(children).hasSize(1);
        assertThat(children.get(0).getHouseHoldMembers()).hasSize(1);
        double birthdateplusage = dataTest.getMedicalrecords().get(0).getBirthdate().getYear() + children.get(0).getAge();
        assertThat((double) LocalDate.now().getYear()).isEqualTo(birthdateplusage, byLessThan(1.1));
        assertThat(children.get(0).getAge()).isLessThan(18);
    }

    @Test
    public void returnNoChildAlertDataIfNoChildInSpecifyAddress() {
        //ARRANGE
        List<Person> addressPerson = new ArrayList<>();
        when(dao.findPersonByAddress("noaddress")).thenReturn(addressPerson);

        //ACT
        assertThrows(NoEntryException.class, () -> getService.childAlert("noaddress"));

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }

    @Test
    public void returnAllChildAlertDataifNoAddressSpecify() {
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(1));
        addressPerson.remove(addressPerson.get(2));
        String firstNameLastNamea = (addressPerson.get(0).getFirstName() + addressPerson.get(0).getLastName());
        String firstNameLastNameb = (addressPerson.get(1).getFirstName() + addressPerson.get(1).getLastName());
        when(dao.findPersonByAddress(dataTest.getPersons().get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(firstNameLastNamea)).thenReturn(dataTest.getMedicalrecords().get(0));
        when(dao.findMedicalrecordByPerson(firstNameLastNameb)).thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<Child> children = getService.childAlert(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(children.get(0).getFirstName()).isEqualTo(dataTest.getPersons().get(0).getFirstName());
        assertThat(children.get(0).getLastName()).isEqualTo(dataTest.getPersons().get(0).getLastName());
        assertThat(children).hasSize(1);
        assertThat(children.get(0).getHouseHoldMembers()).hasSize(1);
        double age = dataTest.getMedicalrecords().get(0).getBirthdate().getYear() + children.get(0).getAge();
        assertThat((double) LocalDate.now().getYear()).isEqualTo(age, byLessThan(1.1));
    }
}