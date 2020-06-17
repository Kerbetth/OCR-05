package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.unit.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class PersonDaoTest {

    private DataTest dataTest=new DataTest();
    private Database database;

    @Mock
    JsonReaderWriter jsonReaderWriter;

    @InjectMocks
    PersonDao dao;

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        doNothing().when(jsonReaderWriter).writer(any());
        database = dataTest.getDatabase();
        when(jsonReaderWriter.getDtb()).thenReturn(database);
    }

    @Test
    public void returnCorrectPersonByGivingTheName() {

        //ACT
        Person byName = dao.findPersonByName(
                dataTest.getPersons().get(0).getFirstName()+
                dataTest.getPersons().get(0).getLastName());
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(dataTest.getPersons().get(0)).extracting(
                Person::getFirstName,
                Person::getLastName,
                Person::getAddress,
                Person::getCity,
                Person::getZip,
                Person::getPhone,
                Person::getEmail)
                .contains(byName.getFirstName(), byName.getLastName(), byName.getAddress(),byName.getCity(),byName.getZip(), byName.getEmail(),byName.getPhone());

        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheAddress() {
        //ACT
        List<Person> byAddress = dao.findPersonByAddress(
                dataTest.getPersons().get(0).getAddress());

        //ASSERT
        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectNumberOfPersonsLivingAtTheCity() {
        //ACT
        List<Person> byAddress = dao.findPersonByCity(
                dataTest.getPersons().get(0).getCity());

        //ASSERT
        assertThat(byAddress).hasSize(2);
    }

    @Test
    public void returnCorrectOfPersonWithTheSameName() {
        //ACT
        List<Person> persons = dao.findPersonsWithSameFirstNameOrLastName(dataTest.getPersons().get(1).getFirstName(),dataTest.getPersons().get(1).getLastName());

        //ASSERT
        assertThat(persons.get(0).getLastName()).isEqualTo(persons.get(1).getLastName());
        assertThat(persons).hasSize(2);
    }
}