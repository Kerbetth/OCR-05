
package com.safetynet.SafetyNetAlert.beans;

import java.util.List;

@SuppressWarnings("unused")
public class Database {

    private List<Person> persons;
    private List<Medicalrecord> medicalrecords;
    private List<Firestation> firestations;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<Medicalrecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

}
