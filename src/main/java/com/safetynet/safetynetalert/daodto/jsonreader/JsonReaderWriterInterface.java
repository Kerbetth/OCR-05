package com.safetynet.safetynetalert.daodto.jsonreader;

import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;

import java.util.List;

/**
 *
 * writer method edit the jsonfile in order to registered the modifications
 * the get method retrieve all data belonging to the JSONfile
 * the update method send the edited data in order to be writing in the JSONfile
 */

public interface JsonReaderWriterInterface {
    void writer(Database database);

    void updateFirestations(List<Firestation> firestations);

    void updateMedicalRecords(List<Medicalrecord> medicalrecords);

    void updatePersons(List<Person> persons);

    Database getDtb();
}
