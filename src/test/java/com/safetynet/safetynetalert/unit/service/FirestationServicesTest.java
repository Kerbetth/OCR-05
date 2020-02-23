package com.safetynet.safetynetalert.unit.service;
import com.safetynet.safetynetalert.dao.Dao;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.unit.DataTest;
import com.safetynet.safetynetalert.service.firestationservice.FirestationService;
import com.safetynet.safetynetalert.domain.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class FirestationServicesTest {

    private DataTest dataTest;
private Database database;

    @Mock
    Dao dao = new Dao("datatest.json");

    @InjectMocks
    FirestationService firestationService = new FirestationService();

    @BeforeEach
    public void setup() {
        dataTest = new DataTest();
        database = dataTest.getDatabase();
        doNothing().when(dao).writer(any());
        when(dao.getDtb()).thenReturn(database);

    }

    @Test
    public void returnCorrectDataOfPersonAndEqualityOfSizeForMedRecAndPersons() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();

        //ACT
        firestationService.addFirestation(m);

        //ASSERT
        assertThat(firestationService.getDtb().getFirestations()).hasSize(4);
    }

    @Test
    public void returnModifiedDataOfPerson() {
        //ARRANGE
        Firestation m = dataTest.getFirestation1();
        m.setStation(8);

        //ACT
        firestationService.setFirestation("3333 broadway",8);

        //ASSERT
        assertThat(firestationService.getDtb().getFirestations()).hasSize(3);
        assertThat(firestationService.getDtb().getFirestations().get(0).getStation()).isEqualTo(8);
    }

    @Test
    public void returnOnePointLessSizeOfPersonListAfterDelete() {
        //ARRANGE
        //ACT
        firestationService.deleteFirestation("3333 broadway");

        //ASSERT
        assertThat(firestationService.getDtb().getFirestations()).hasSize(2);
    }
}