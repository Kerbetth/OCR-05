package com.safetynet.safetynetalert.unit.Services.personServices;

import com.safetynet.safetynetalert.apiservices.MedicalRecordService;
import com.safetynet.safetynetalert.dao.MedicalrecordDao;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.unit.DataTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MedicalRecordsAPIServicesTest {

    private DataTest dataTest = new DataTest();

    @Mock
    static MedicalrecordDao medicalrecordDao;

    @InjectMocks
    MedicalRecordService aPIService;


    @Test
    public void returnMedicalRecordWithCorrectData() {
        //ARRANGE
        doNothing().when(medicalrecordDao).addMedicalrecord(any());
        ArgumentCaptor<Medicalrecord> medicalRecordArgCapt = ArgumentCaptor.forClass(Medicalrecord.class);

        //ACT
        aPIService.postMedicalRecord(dataTest.getMedicalrecords().get(0));

        //ASSERT
        verify(medicalrecordDao).addMedicalrecord(medicalRecordArgCapt.capture());
        Medicalrecord pl1 = dataTest.getMedicalrecords().get(0);
        assertThat(medicalRecordArgCapt.getValue()).extracting("firstName","lastName","birthdate","medications","allergies")
                .contains(pl1.getFirstName(), pl1.getLastName(), pl1.getBirthdate(), pl1.getMedications(), pl1.getAllergies());
    }


    @Test
    public void medicalRecordCorrectlyPut() {
        //ARRANGE
        doNothing().when(medicalrecordDao).setMedicalrecord(any());
        when(medicalrecordDao.getIdByName(anyString())).thenReturn(1);
        when(medicalrecordDao.findMedicalrecordByID(anyInt())).thenReturn(dataTest.getMedicalrecords().get(0));
        ArgumentCaptor<Medicalrecord> medicalRecordArgCapt = ArgumentCaptor.forClass(Medicalrecord.class);

        //ACT
        aPIService.putMedicalRecord("name",dataTest.getMedicalrecords().get(1));

        //ASSERT
        verify(medicalrecordDao).setMedicalrecord(medicalRecordArgCapt.capture());
        Medicalrecord pl1 = dataTest.getMedicalrecords().get(0);
        Medicalrecord pl2 = dataTest.getMedicalrecords().get(1);
        assertThat(medicalRecordArgCapt.getValue()).extracting("firstName","lastName","birthdate","medications","allergies")
                .contains(pl1.getFirstName(), pl1.getLastName(), pl2.getBirthdate(), pl2.getMedications(), pl2.getAllergies());
    }

    @Test
    public void medicalRecordIsDeletedWithDelete() {
        //ARRANGE
        String name = "name";
        when(medicalrecordDao.getIdByName(anyString())).thenReturn(1);
        ArgumentCaptor<Integer> numberArgCapt = ArgumentCaptor.forClass(Integer.class);

        //ACT
        ResponseEntity responseEntity = aPIService.deletePersonAndMedicalRecord("noname");

        //ASSERT
        verify(medicalrecordDao).deleteMedicalRecordAndPersonEntry(numberArgCapt.capture());
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, numberArgCapt.getValue());
    }
}