package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.persandmedservice.MedicalrecordService;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class MedicalrecordServiceTest {

    private DataTest dataTest;
    private Database database;
    @Mock
    static Logger loggerMock;

    @Mock
    Dao dao = new Dao("datatest.json");

    @InjectMocks
    MedicalrecordService medicalrecordDao = new MedicalrecordService();

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        medicalrecordDao.logger = loggerMock;
        doNothing().when(dao).writer(any());
        database = dataTest.getDatabase();
        when(dao.getDtb()).thenReturn(database);
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setFirstName("newOne");
        when(dao.findPersonByName(anyString())).thenReturn(null);

        //ACT
        medicalrecordDao.addMedicalrecord(m);
        //ASSERT
        assertThat(medicalrecordDao.getDtb().getMedicalrecords()).hasSize(5);
        assertThat(medicalrecordDao.getDtb().getPersons()).hasSize(5);
    }

    @Test
    public void returnErrorIfTheNewMedicalRecordHaveTheSameName() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        when(dao.findPersonByName(anyString())).thenReturn(database.getPersons().get(0));

        //ACT
        medicalrecordDao.addMedicalrecord(m);

        //ASSERT
        verify(loggerMock, times(1)).error(anyString());
        assertThat(medicalrecordDao.getDtb().getMedicalrecords()).hasSize(4);
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setBirthdate(dataTest.getMedicalrecords().get(3).getBirthdate());
        m.setAllergies(dataTest.getMedicationList3());
        m.setMedications(dataTest.getMedicationList3());
        when(dao.findMedicalrecordByID(anyInt())).thenReturn(database.getMedicalrecords().get(0));

        //ACT
        medicalrecordDao.setMedicalrecord(m.getFirstName() + m.getLastName(), m);

        //ASSERT
        assertThat(medicalrecordDao.getDtb().getMedicalrecords()).hasSize(4);
        assertThat(medicalrecordDao.getDtb().getMedicalrecords().get(0)).extracting(
                Medicalrecord::getFirstName,
                Medicalrecord::getLastName,
                Medicalrecord::getBirthdate,
                Medicalrecord::getMedications,
                Medicalrecord::getAllergies)
                .contains(m.getFirstName(), m.getLastName(), m.getBirthdate(), m.getMedications(), m.getAllergies());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        medicalrecordDao.deleteMedicalRecordAndPersonEntry("JohnSchaffer");

        //ASSERT
        assertThat(medicalrecordDao.getDtb().getMedicalrecords()).hasSize(3);
        assertThat(medicalrecordDao.getDtb().getPersons()).hasSize(3);
    }
}