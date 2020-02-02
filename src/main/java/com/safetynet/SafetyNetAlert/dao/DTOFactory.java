package com.safetynet.SafetyNetAlert.dao;


import com.safetynet.SafetyNetAlert.beans.Medicalrecord;
import com.safetynet.SafetyNetAlert.beans.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static Integer getAge(Date date) {
        long longage;
        longage = ((System.currentTimeMillis() / 60000 - date.getTime() / 60000) / (525560));
        Integer age;
        if (longage == -1) {
            return null;
        } else {
            age = (int) longage;
        }
        return age;
    }

    public static Date getDate(String dateString) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/mm/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static List getList(String listString) {
        return new ArrayList<>((Arrays.asList(listString.split(","))));
    }
}