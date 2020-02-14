package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.DaoAccessTest;
import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.service.persandmedservice.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MedicalrecordServiceTest {

    private DataTest dataTest;
    @Mock
    static Logger loggerMock;

    @Spy
    DaoAccessTest dao = new DaoAccessTest();

    @InjectMocks
    MedicalrecordService medicalrecordDao = new MedicalrecordService();

    @Before
    public void setup() {
        dataTest = new DataTest();
        medicalrecordDao.logger = loggerMock;
        doNothing().when(dao).writer(any());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setFirstName("newOne");

        //ACT
        medicalrecordDao.addMedicalrecord(m);
        //ASSERT
        assertEquals(5, medicalrecordDao.getDtb().getMedicalrecords().size());
        assertEquals(5, medicalrecordDao.getDtb().getPersons().size());
    }

    @Test
    public void returnErrorIfTheNewMedicalRecordHaveTheSameName() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);

        //ACT
        medicalrecordDao.addMedicalrecord(m);

        //ASSERT
        verify(loggerMock, times(1)).error(anyString());
        assertEquals(medicalrecordDao.getDtb().getMedicalrecords().size(), 4);
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setBirthdate(dataTest.getMedicalrecords().get(3).getBirthdate());
        m.setAllergies(dataTest.getMedicationList3());
        m.setMedications(dataTest.getMedicationList3());

        //ACT
        medicalrecordDao.setMedicalrecord(m.getFirstName() + m.getLastName(), m);

        //ASSERT
        assertEquals(4, medicalrecordDao.getDtb().getMedicalrecords().size());
        assertThat(medicalrecordDao.getDtb().getMedicalrecords().get(0)).extracting(Enum.FNAME.str(), Enum.LNAME.str(), Enum.BDATE.str(), Enum.MED.str(), Enum.ALLERG.str())
                .contains(m.getFirstName(), m.getLastName(), m.getBirthdate(), m.getMedications(), m.getAllergies());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        medicalrecordDao.deleteMedicalRecordAndPersonEntry("JohnSchaffer");

        //ASSERT
        assertEquals(3, medicalrecordDao.getDtb().getPersons().size());
        assertEquals(3, medicalrecordDao.getDtb().getMedicalrecords().size());
    }
}