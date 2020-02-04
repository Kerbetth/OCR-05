package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonInfo {
    private String name;
    private String address;
    private Integer age;
    private String email;
    private List<String> allergies;
    private List<String> medications;
}
