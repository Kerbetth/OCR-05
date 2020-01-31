
package com.safetynet.SafetyNetAlert.mentorat.db;

import java.util.List;

@SuppressWarnings("unused")
public class Database {

    private List<Firestation> firestations;
    private List<Medicalrecord> medicalrecords;
    private List<Person> persons;

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<Medicalrecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

}
