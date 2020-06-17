package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FirestationGet {
    private List<PersonFirestation> personFirestations;
    private Count count;
}
