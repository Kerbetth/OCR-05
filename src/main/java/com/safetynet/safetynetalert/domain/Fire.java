package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Fire {
    private List<PersonFloodAndFire> personFloodAndFires;
    private Integer stationNumber;
}
