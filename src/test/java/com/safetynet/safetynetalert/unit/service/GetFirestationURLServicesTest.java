package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.daodto.firestationdao.FirestationDao;
import com.safetynet.safetynetalert.daodto.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.domain.*;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.getservice.GetService;
import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetFirestationURLServicesTest {

    private DataTest dataTest = new DataTest();


    @Mock
    static PersonDao personDao;
    @Mock
    static FirestationDao firestationDao;
    @Mock
    static MedicalRecordDao medicalRecordDao;
    @Mock
    private JsonReaderWriter dao = new JsonReaderWriter("datatest.json");
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnFirestationContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(3));
        List<Firestation> firestations = dataTest.getFirestations();
        firestations.remove(2);
        when(firestationDao.findFirestationsByNumber("1")).thenReturn(firestations);
        when(personDao.findPersonByAddress(addressPerson.get(0).getAddress())).thenReturn(addressPerson);
        when(medicalRecordDao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(1))
                .thenReturn(dataTest.getMedicalrecords().get(2));

        //ACT
        FirestationGet getfirestation = getService.firestation(1);

        //ASSERT
        Count count = getfirestation.getCount();
        List<PersonFirestation> personFirestation = getfirestation.getPersonFirestations();
        assertThat(count.getAdults()).isEqualTo(2);
        assertThat(count.getChildren()).isEqualTo(1);
        Person p1 =  dataTest.getPersons().get(0);
        Person p2 =  dataTest.getPersons().get(1);
        Person p3 =  dataTest.getPersons().get(2);
        assertThat(personFirestation).extracting(PersonFirestation::getFirstName,PersonFirestation::getLastName,PersonFirestation::getAddress,PersonFirestation::getPhone)
                .contains(  tuple(p1.getFirstName(), p1.getLastName(), p1.getAddress(), p1.getPhone()),
                            tuple(p2.getFirstName(), p2.getLastName(), p2.getAddress(), p2.getPhone()),
                            tuple(p3.getFirstName(), p3.getLastName(), p3.getAddress(), p3.getPhone()));
    }

    @Test
    public void returnNoFirestationDataIfNumberStationDoesntExist(){
        //ARRANGE
        List<Firestation> firestations = new ArrayList<>();
        when(firestationDao.findFirestationsByNumber("5")).thenReturn(firestations);

        //ACT
        assertThrows(NoEntryException.class, () -> getService.firestation(5));

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}