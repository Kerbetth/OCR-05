package com.safetynet.safetynetalert.unit.service;

import com.safetynet.safetynetalert.daodto.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.daodto.persondao.PersonDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.crudservice.MedicalrecordService;
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
    static PersonDao personDao;
    @Mock
    static MedicalRecordDao medicalRecordDao;

    @InjectMocks
    MedicalrecordService medicalrecordDao = new MedicalrecordService();

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        medicalrecordDao.logger = loggerMock;
        database = dataTest.getDatabase();
        doNothing().when(medicalRecordDao).updateJson(any());
        doNothing().when(personDao).updateJson(any());
        when(personDao.getPersons()).thenReturn(database.getPersons());
        when(medicalRecordDao.getMedicalrecords()).thenReturn(database.getMedicalrecords());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setFirstName("newOne");
        when(personDao.findPersonByName(anyString())).thenReturn(null);

        //ACT
        medicalrecordDao.add(m);
        //ASSERT
        assertThat(medicalRecordDao.getMedicalrecords()).hasSize(5);
        assertThat(personDao.getPersons()).hasSize(5);
    }

    @Test
    public void returnErrorIfTheNewMedicalRecordHaveTheSameName() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        when(personDao.findPersonByName(anyString())).thenReturn(database.getPersons().get(0));

        //ACT
        medicalrecordDao.add(m);

        //ASSERT
        verify(loggerMock, times(1)).error(anyString());
        assertThat(medicalRecordDao.getMedicalrecords()).hasSize(4);
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setBirthdate(dataTest.getMedicalrecords().get(3).getBirthdate());
        m.setAllergies(dataTest.getMedicationList3());
        m.setMedications(dataTest.getMedicationList3());
        when(medicalRecordDao.findMedicalrecordByID(anyInt())).thenReturn(database.getMedicalrecords().get(0));

        //ACT
        medicalrecordDao.set(m.getFirstName() + m.getLastName(), m);

        //ASSERT
        assertThat(medicalRecordDao.getMedicalrecords()).hasSize(4);
        assertThat(medicalRecordDao.getMedicalrecords().get(0)).extracting(
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
        medicalrecordDao.delete("JohnSchaffer");

        //ASSERT
        assertThat(medicalRecordDao.getMedicalrecords()).hasSize(3);
        assertThat(personDao.getPersons()).hasSize(3);
    }
}