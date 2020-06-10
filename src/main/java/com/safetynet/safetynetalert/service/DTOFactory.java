package com.safetynet.safetynetalert.service;


import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.domain.PersonFloodAndFire;

import java.time.LocalDate;
import java.time.Period;


public class DTOFactory {

    /**
     * createdefaultPerson Create an default entry of person corresponding with the MedicalRecord registered
     * createdefaultMedicalRecord Create an default entry of person corresponding with the Person registered
     * age calculate the age according to the birth date
     * createPersonFloodAndFire is a common data for Fire an FloodStation get request
     */

    public static Person createdefaultPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    public static Medicalrecord createdefaultMedicalRecord(String firstName, String lastName) {
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName(firstName);
        medicalrecord.setLastName(lastName);
        return medicalrecord;
    }

    public static Integer getAge(LocalDate birthdate) {
        Period period = Period.between(birthdate, LocalDate.now());
        return period.getYears();
    }

    public static PersonFloodAndFire createPersonFloodAndFire(Person person, Medicalrecord medicalrecord) {
        PersonFloodAndFire personFloodAndFire = new PersonFloodAndFire();
        personFloodAndFire.setName(person.getFirstName() + " " + person.getLastName());
        personFloodAndFire.setPhone(person.getPhone());
        personFloodAndFire.setAge(DTOFactory.getAge(medicalrecord.getBirthdate()));
        personFloodAndFire.setMedications(medicalrecord.getMedications());
        personFloodAndFire.setAllergies(medicalrecord.getAllergies());
        return personFloodAndFire;
    }
}