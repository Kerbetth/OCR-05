
package com.safetynet.safetynetalert.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Person {
    @NotNull(message = "FirstName not specify, process aborted")
    private String firstName;
    @NotNull(message = "LastName not specify, process aborted")
    private String lastName;
    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;
}
