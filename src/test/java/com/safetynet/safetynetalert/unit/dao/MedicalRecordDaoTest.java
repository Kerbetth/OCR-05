package com.safetynet.safetynetalert.unit.dao;

import com.safetynet.safetynetalert.dao.medicalrecorddao.MedicalRecordDao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.dao.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.unit.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MedicalRecordDaoTest {

    private DataTest dataTest=new DataTest();
    private Database database;
    @Mock
    JsonReaderWriter jsonReaderWriter;

    @InjectMocks
    MedicalRecordDao dao;

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        doNothing().when(jsonReaderWriter).writer(any());
        database = dataTest.getDatabase();
        when(jsonReaderWriter.getDtb()).thenReturn(database);
    }

    @Test
    public void returnCorrectMedRecAccordingToThePerson() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByPerson(
                dataTest.getPersons().get(0).getFirstName()+dataTest.getPersons().get(0).getLastName());

        //ASSERT
        assertThat(dataTest.getPersons().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersons().get(0).getLastName()).isEqualTo(med.getLastName());
    }

    @Test
    public void returnCorrectMedRecAccordingToTheID() {
        //ACT
        Medicalrecord med = dao.findMedicalrecordByID(0);

        //ASSERT
        assertThat(dataTest.getPersons().get(0).getFirstName()).isEqualTo(med.getFirstName());
        assertThat(dataTest.getPersons().get(0).getLastName()).isEqualTo(med.getLastName());
    }

}