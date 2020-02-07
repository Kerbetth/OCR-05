package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Child;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetChildAlertURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;

    @InjectMocks
    GetService getService;

    @Test
    public void returnChildAlertMapContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersonlist();
        addressPerson.remove(addressPerson.get(1));
        addressPerson.remove(addressPerson.get(2));
        when(dao.findPersonByAddress(dataTest.getPersonlist().get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(addressPerson.get(0))).thenReturn(dataTest.getMedicalrecords().get(0));
        when(dao.findMedicalrecordByPerson(addressPerson.get(1))).thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<Child> children = getService.childAlert(dataTest.getPersonlist().get(0).getAddress());

        //ASSERT
        assertEquals(dataTest.getPersonlist().get(0).getFirstName(), children.get(0).getFirstName());
        assertEquals(dataTest.getPersonlist().get(0).getLastName(), children.get(0).getLastName());
        assertEquals(1, children.size());
        assertEquals(1, children.get(0).getHouseHoldMembers().size());
        double birthdateplusage = dataTest.getMedicalrecords().get(0).getBirthdate().getYear()+children.get(0).getAge();
        assertThat((double)LocalDate.now().getYear()).isEqualTo(birthdateplusage, byLessThan(1.1));
        assertThat(children.get(0).getAge()).isLessThan(18);
    }

    @Test
    public void returnNoChildAlertDataIfNoChildInSpecifyAddress(){
        //ARRANGE
        List<Person> addressPerson = new ArrayList<>();
        when(dao.findPersonByAddress("noaddress")).thenReturn(addressPerson);

        //ACT
        List<Child> children = getService.childAlert("noaddress");

        //ASSERT
        assertNull(children);
    }

    @Test
    public void returnAllChildAlertDataifNoAddressSpecify(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersonlist();
        addressPerson.remove(addressPerson.get(1));
        addressPerson.remove(addressPerson.get(2));
        when(dao.findPersonByAddress(dataTest.getPersonlist().get(0).getAddress())).thenReturn(addressPerson);
        when(dao.findMedicalrecordByPerson(addressPerson.get(0))).thenReturn(dataTest.getMedicalrecords().get(0));
        when(dao.findMedicalrecordByPerson(addressPerson.get(1))).thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<Child> children = getService.childAlert(dataTest.getPersonlist().get(0).getAddress());

        //ASSERT
        assertEquals(dataTest.getPersonlist().get(0).getFirstName(), children.get(0).getFirstName());
        assertEquals(dataTest.getPersonlist().get(0).getLastName(), children.get(0).getLastName());
        double age = dataTest.getMedicalrecords().get(0).getBirthdate().getYear()+children.get(0).getAge();
        assertThat((double)LocalDate.now().getYear()).isEqualTo(age, byLessThan(1.1));
        assertEquals(1, children.size());
        assertEquals(1, children.get(0).getHouseHoldMembers().size());
    }
}