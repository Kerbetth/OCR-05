package com.safetynet.SafetyNetAlert.services.dto;

import com.safetynet.SafetyNetAlert.services.enumerations.DataDefaultValue;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DTOFactory {


    public static Map<String, String> createdefaultPerson(String firstName, String lastName) {
        Map<String, String> defaultPerson = new HashMap<>();
        defaultPerson.put(DataEntry.FNAME.getString(), firstName);
        defaultPerson.put(DataEntry.LNAME.getString(), lastName);
        defaultPerson.put(DataEntry.ADDRESS.getString(), DataDefaultValue.UNKNOW.getString());
        defaultPerson.put(DataEntry.CITY.getString(), DataDefaultValue.UNKNOW.getString());
        defaultPerson.put(DataEntry.ZIP.getString(), DataDefaultValue.UNKNOW.getString());
        defaultPerson.put(DataEntry.PHONE.getString(), DataDefaultValue.UNKNOW.getString());
        defaultPerson.put(DataEntry.EMAIL.getString(), DataDefaultValue.UNKNOW.getString());
        return defaultPerson;
    }

    public static Map<String, String> createdefaultMedicalRecord(String firstName, String lastName) {
        Map<String, String> defaultMedicalRecord = new HashMap<>();
        defaultMedicalRecord.put(DataEntry.FNAME.getString(), firstName);
        defaultMedicalRecord.put(DataEntry.LNAME.getString(), lastName);
        defaultMedicalRecord.put(DataEntry.BIRTHDATE.getString(), DataDefaultValue.UNKNOW.getString());
        defaultMedicalRecord.put(DataEntry.MEDIC.getString(), DataDefaultValue.EMPTY.getString());
        defaultMedicalRecord.put(DataEntry.ALLERGI.getString(), DataDefaultValue.EMPTY.getString());
        return defaultMedicalRecord;
    }

    public static String getAge(String birthdate) {
        long longage = -1;
        try {
            Date birthDate = new SimpleDateFormat("dd/mm/yyyy").parse(birthdate);
            longage = ((System.currentTimeMillis() / 60000 - birthDate.getTime() / 60000) / (525560));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer age;
        if (longage == -1) {
            return DataDefaultValue.UNKNOW.getString();
        } else {
            age = (int) longage;
        }
        return age.toString();
    }
}