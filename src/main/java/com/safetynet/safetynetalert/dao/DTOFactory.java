package com.safetynet.safetynetalert.dao;


import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import java.time.LocalDate;
import java.time.Period;


public class DTOFactory {


    static Person createdefaultPerson(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    static Medicalrecord createdefaultMedicalRecord(String firstName, String lastName) {
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName(firstName);
        medicalrecord.setLastName(lastName);
        return medicalrecord;
    }

    public static Integer getAge(LocalDate birthdate) {
        Period period = Period.between(birthdate, LocalDate.now());
        if (birthdate == null) return null;
        return period.getYears();
    }
}