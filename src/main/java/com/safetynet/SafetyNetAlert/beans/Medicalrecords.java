package com.safetynet.SafetyNetAlert.beans;

import java.util.*;

public class Medicalrecords
{
    String firstName;
    String lastName;
    String birthDate;
    String city;
    List medications;
    List allergies;
    
    public Medicalrecords() {
        this.medications = new ArrayList();
        this.allergies = new ArrayList();
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    public String getBirthDate() {
        return this.birthDate;
    }
    
    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(final String city) {
        this.city = city;
    }
    
    public List getMedications() {
        return this.medications;
    }
    
    public void setMedications(final List medications) {
        this.medications = medications;
    }
    
    public List getAllergies() {
        return this.allergies;
    }
    
    public void setAllergies(final List allergies) {
        this.allergies = allergies;
    }
}
