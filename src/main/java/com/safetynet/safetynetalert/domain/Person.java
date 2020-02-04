
package com.safetynet.safetynetalert.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;
}
