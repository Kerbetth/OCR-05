package com.safetynet.safetynetalert.unit.Services.getServices;

import com.safetynet.safetynetalert.apiservices.GetService;
import com.safetynet.safetynetalert.dao.PersonDao;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonInfo;
import com.safetynet.safetynetalert.DataTest;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetPersonInfoURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao dao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnPersonInfoWithCorrectDate(){
        //ARRANGE
        List<Person> pl1 = new ArrayList<>();
        pl1.add(dataTest.getPersonlist().get(1));
        when(dao.findPersonsWithSameFirstNameOrLastName(anyString(),anyString())).thenReturn(pl1);
        when(dao.findMedicalrecordByPerson(any()))
                .thenReturn(dataTest.getMedicalrecords().get(0))
                .thenReturn(dataTest.getMedicalrecords().get(1));

        //ACT
        List<PersonInfo> personInfos = getService.personInfo("test","test");

        //ASSERT
        assertEquals(1, personInfos.size());
        assertThat(personInfos.get(0)).extracting("name","address","email","medications","allergies")
                .contains(pl1.get(0).getFirstName() + " " + pl1.get(0).getLastName(), pl1.get(0).getAddress(), pl1.get(0).getEmail(),dataTest.getMedicationList1(), dataTest.getMedicationList2());
    }

    @Test
    public void returnNoFireDataIfNumberStationDoesntExist(){
        List<Person> emptylist = new ArrayList<>();
        when(dao.findPersonsWithSameFirstNameOrLastName(anyString(),anyString())).thenReturn(emptylist);
        //ACT
        List<PersonInfo> personInfos = getService.personInfo("test","test");
        //ASSERT
        verify(loggermock, times(1)).error(anyString());
        assertEquals(0, personInfos.size());
    }
}