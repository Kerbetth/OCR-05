package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonFloodAndFire {
    private String name;
    private String phone;
    private Integer age;
    private List<String> allergies;
    private List<String> medications;
}
