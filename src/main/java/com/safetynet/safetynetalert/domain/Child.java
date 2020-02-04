package com.safetynet.safetynetalert.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Child {
    private String firstName;
    private String lastName;
    private String address;
    private Integer age;
    private List<String> houseHoldMembers;
}
