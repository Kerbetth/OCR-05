package com.safetynet.safetynetalert.integration;

import com.safetynet.safetynetalert.DaoTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.WritingCleanJsonData;
import com.safetynet.safetynetalert.apiservices.persandmedservice.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
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

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class APIMedRecIT {

    DataTest dataTest = new DataTest();
    @Spy
    private DaoTest dao = new DaoTest();
    @Spy
    private Logger logger = LogManager.getLogger("GetServiceSpy");

    Medicalrecord m1;

    @InjectMocks
    private MedicalrecordService medicalrecordService = new MedicalrecordService();

        @Before
        public void setup() {
            WritingCleanJsonData.writingCleanJsonDataTest();
            medicalrecordService.getDao().setDatabase(dao.getDtb());
            medicalrecordService.getDao().setJsonWriter("target/classes/datatest.json");
            m1 = dataTest.getMedicalrecords().get(0);
        }

        @After
        public void finish() {
            WritingCleanJsonData.writingCleanJsonDataTest();
        }

    @Test
    public void addPersonWithCorrectData() {
        //ACT
        m1.setFirstName("newname");
        medicalrecordService.addMedicalrecord(m1);

        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(5, dao2.getDtb().getMedicalrecords().size());
        assertEquals(5, dao2.getDtb().getPersons().size());
        WritingCleanJsonData.writingCleanJsonDataTest();
    }

    @Test
    public void setPersonWithCorrectData() {
        //ACT
        medicalrecordService.setMedicalrecord("JeffLoomis", m1);

        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(4, dao2.getDtb().getMedicalrecords().size());
        assertThat(dao2.getDtb().getMedicalrecords().get(1)).extracting(Enum.FNAME.str(), Enum.LNAME.str(), Enum.BDATE.str(), Enum.MED.str(), Enum.ALLERG.str())
                .contains("Jeff", "Loomis", m1.getBirthdate(), m1.getMedications(), m1.getAllergies());
    }

    @Test
    public void assertDeletePerson() {
        //ACT
        medicalrecordService.deleteMedicalRecordAndPersonEntry("JeffLoomis");
        //ASSERT
        DaoTest dao2 = new DaoTest();
        assertEquals(3,dao2.getDtb().getPersons().size());
        assertEquals(3,dao2.getDtb().getMedicalrecords().size());
    }

}