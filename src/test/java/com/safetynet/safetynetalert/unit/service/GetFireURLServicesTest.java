package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.dao.FirestationDao;
import com.safetynet.safetynetalert.dao.MedicalRecordDao;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.GetService;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFloodAndFire;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetFireURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao personDao;
    @Mock
    static FirestationDao firestationDao;
    @Mock
    static MedicalRecordDao medicalRecordDao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFireContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(3));
        addressPerson.remove(addressPerson.get(1));
        when(firestationDao.findFirestationByAddress(anyString())).thenReturn(dataTest.getFirestation1());
        when(personDao.findPersonByAddress(addressPerson.get(0).getAddress())).thenReturn(addressPerson);
        when(medicalRecordDao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(2));

        //ACT
        List<Object> getfire = getService.fire(dataTest.getPersons().get(0).getAddress());

        //ASSERT
        List<PersonFloodAndFire> personFloodAndFires = (List<PersonFloodAndFire>) getfire.get(1);
        assertThat(getfire).hasSize(2);
        assertThat(personFloodAndFires).hasSize(2);
        assertThat(getfire.get(0)).isEqualTo(1);
        Person p1 =  dataTest.getPersons().get(0);
        Person p3 =  dataTest.getPersons().get(1);
        assertThat(personFloodAndFires).extracting("name","phone","medications","allergies")
                .contains(  tuple(p1.getFirstName() + " " + p1.getLastName(), p1.getPhone(), dataTest.getMedicationList1(), dataTest.getMedicationList2()),
                        tuple(p3.getFirstName() + " " + p3.getLastName(), p3.getPhone(), dataTest.getMedicationList4(), dataTest.getMedicationList3()));
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){

        //ACT
        List<Object> getfire = getService.fire("noaddress");

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        assertThat(getfire).hasSize(2);
        assertThat(getfire.get(0)).isEqualTo(0);
    }
}