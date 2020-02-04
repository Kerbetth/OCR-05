package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HouseHold {
    private String address;
    private List<PersonFloodAndFire> personList;
}
