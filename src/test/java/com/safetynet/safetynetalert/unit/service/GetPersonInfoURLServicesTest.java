package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.daodto.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.getservice.GetService;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonInfo;
import com.safetynet.safetynetalert.exceptions.NoFnameOrLnameException;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetPersonInfoURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao personDao;
    @Mock
    static MedicalRecordDao medicalRecordDao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnPersonInfoWithCorrectDate(){
        //ARRANGE
        List<Person> pl1 = new ArrayList<>();
        pl1.add(dataTest.getPersons().get(1));
        when(personDao.findPersonsWithSameFirstNameOrLastName(anyString(),anyString())).thenReturn(pl1);
        when(medicalRecordDao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<PersonInfo> personInfos = getService.personInfo("test","test");

        //ASSERT
        assertThat(personInfos).hasSize(1);
        assertThat(personInfos.get(0)).extracting("name","address","email","medications","allergies")
                .contains(pl1.get(0).getFirstName() + " " + pl1.get(0).getLastName(), pl1.get(0).getAddress(), pl1.get(0).getEmail(),dataTest.getMedicationList1(), dataTest.getMedicationList2());
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        List<Person> emptylist = new ArrayList<>();
        when(personDao.findPersonsWithSameFirstNameOrLastName(anyString(),anyString())).thenReturn(emptylist);
        //ACT
        assertThrows(NoFnameOrLnameException.class, () -> getService.personInfo("test","test"));
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}