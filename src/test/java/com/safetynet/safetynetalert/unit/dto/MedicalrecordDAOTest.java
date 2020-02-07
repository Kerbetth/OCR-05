package com.safetynet.safetynetalert.unit.dto;

import com.safetynet.safetynetalert.DataTest;
import com.safetynet.safetynetalert.dao.JsonWriter;
import com.safetynet.safetynetalert.dao.MedicalrecordDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.Enum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MedicalrecordDAOTest {

    private DataTest dataTest;

    @Mock
    static Database databaseMock;
    @Mock
    static JsonWriter jsonWriterMock;

    MedicalrecordDao personDao = new MedicalrecordDao();

    @Before
    public void setup() {
        dataTest = new DataTest();
        personDao.database =databaseMock;
        personDao.jsonWriter =jsonWriterMock;
        when(databaseMock.getPersons()).thenReturn(dataTest.getPersonlist());
        when(databaseMock.getMedicalrecords()).thenReturn(dataTest.getMedicalrecords());
        doNothing().when(jsonWriterMock).writer(any(),anyString());
        doNothing().when(databaseMock).setPersons(anyList());
        doNothing().when(databaseMock).setMedicalrecords(anyList());
    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        ArgumentCaptor<List<Person>> personsArgCapt = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<Medicalrecord>> medicalRecordArgCapt = ArgumentCaptor.forClass(List.class);
        Medicalrecord m = dataTest.getMedicalrecords().get(0);

        //ACT
        personDao.addMedicalrecord(m);

        //ASSERT
        verify(databaseMock).setPersons(personsArgCapt.capture());
        verify(databaseMock).setMedicalrecords(medicalRecordArgCapt.capture());
        assertEquals(personsArgCapt.getValue().size(), medicalRecordArgCapt.getValue().size());
        assertEquals(5, personsArgCapt.getValue().size());
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Medicalrecord m = dataTest.getMedicalrecords().get(0);
        m.setBirthdate(dataTest.getMedicalrecords().get(3).getBirthdate());
        m.setAllergies(dataTest.getMedicationList3());
        m.setMedications(dataTest.getMedicationList3());
        //ACT
        personDao.setMedicalrecord(m);

        //ASSERT
        assertEquals(4, databaseMock.getMedicalrecords().size());
        assertThat(databaseMock.getMedicalrecords().get(0)).extracting(Enum.FNAME.str(),Enum.LNAME.str(),Enum.BDATE.str(),Enum.MED.str(),Enum.ALLERG.str())
                .contains(m.getFirstName(), m.getLastName(), m.getBirthdate(),m.getMedications(),m.getAllergies());
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        personDao.deleteMedicalRecordAndPersonEntry(0);

        //ASSERT
        assertEquals(3, databaseMock.getPersons().size());
        assertEquals(3, databaseMock.getMedicalrecords().size());
    }
}