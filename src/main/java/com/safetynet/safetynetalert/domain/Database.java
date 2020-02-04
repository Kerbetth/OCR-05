
package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Database {
    private List<Person> persons;
    private List<Medicalrecord> medicalrecords;
    private List<Firestation> firestations;
}
