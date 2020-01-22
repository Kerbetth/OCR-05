package com.safetynet.SafetyNetAlert.domain;


import java.util.ArrayList;

public class MedicalRecord {

    private Integer id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private ArrayList medications;
    private ArrayList allergies;

    public MedicalRecord() {
    }

    public MedicalRecord(Integer id, String firstName, String lastName, String birthDate, ArrayList medications, ArrayList allergies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public ArrayList getMedications() {
        return medications;
    }

    public void setMedications(ArrayList medications) {
        this.medications = medications;
    }

    public ArrayList getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList allergies) {
        this.allergies = allergies;
    }
}