package com.safetynet.safetynetalert.dao.jsonreader;

import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;

import java.util.List;

public interface JsonReaderWriterInterface {
    void writer(Database database);

    void updateFirestations(List<Firestation> firestations);

    void updateMedicalRecords(List<Medicalrecord> medicalrecords);

    void updatePersons(List<Person> persons);

    Database getDtb();
}
