package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.getservice.GetService;
import com.safetynet.safetynetalert.domain.Person;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetCommunityEmailURLServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static PersonDao personDao;
    @Mock
    static Logger loggermock;

    @InjectMocks
    GetService getService;

    @Test
    public void returnCommunityEmailContentWithCorrectData(){
        //ARRANGE
        List<Person> addressPerson = dataTest.getPersons();
        addressPerson.remove(addressPerson.get(1));
        addressPerson.remove(addressPerson.get(2));
        when(personDao.findPersonByCity(dataTest.getPersons().get(0).getCity())).thenReturn(addressPerson);

        //ACT
        List<String> emailList = getService.communityEmail(dataTest.getPersons().get(0).getCity());

        //ASSERT
        assertThat(emailList).hasSize(2);
        assertThat(dataTest.getPersons().get(0).getEmail()).isEqualTo(emailList.get(0));
        assertThat(dataTest.getPersons().get(1).getEmail()).isEqualTo(emailList.get(1));
    }

    @Test
    public void returnNoCommunityEmailDataIfNoEmailInTheCity(){
        //ARRANGE
        List<Person> addressPerson = new ArrayList<>();
        when(personDao.findPersonByAddress("noaddress")).thenReturn(addressPerson);

        //ACT
        assertThrows(NoEntryException.class, () -> getService.childAlert("noaddress"));

        //ASSERT
        verify(loggermock, times(1)).error(anyString());
    }
}