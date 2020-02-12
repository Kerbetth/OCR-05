package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.DaoTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.apiservices.persandmedservice.PersonService;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class APIPersonIT {

    DataTest dataTest = new DataTest();
    @Spy
    private DaoTest dao = new DaoTest();
    @Spy
    private Logger logger = LogManager.getLogger("GetServiceSpy");

    Person p1;

    @InjectMocks
    private PersonService personService = new PersonService();

    @Before
    public void setup() {
        WritingCleanJsonData.writingCleanJsonDataTest();
        personService.getDao().setDatabase(dao.getDtb());
        personService.getDao().setJsonWriter("target/classes/datatest.json");
        p1 = dataTest.getPersons().get(0);
    }
    @After
    public void finish() {
        WritingCleanJsonData.writingCleanJsonDataTest();
    }

    @Test
    public void addPersonWithCorrectData() {
        //ACT
        p1.setFirstName("newname");
        personService.addPerson(p1);

        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(5, dao2.getDtb().getPersons().size());
    }

    @Test
    public void setPersonWithCorrectData() {
        //ACT
        personService.setPerson("JeffLoomis",p1);

        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(4, dao2.getDtb().getPersons().size());
        assertThat(dao2.getDtb().getPersons().get(1)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.ADDRESS.str(),Enum.CITY.str(),Enum.ZIP.str(),Enum.PHONE.str(),Enum.EMAIL.str())
                .contains("Jeff", "Loomis", p1.getAddress(),p1.getCity(),p1.getZip(), p1.getEmail(),p1.getPhone());

    }

    @Test
    public void assertDeletePerson() {
        //ACT
        personService.deleteMedicalRecordAndPersonEntry("JeffLoomis");

        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(3, dao2.getDtb().getPersons().size());
        assertEquals(3, dao2.getDtb().getMedicalrecords().size());
    }

}
