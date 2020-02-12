
package com.safetynet.safetynetalert.domain;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Firestation {
    @NotNull(message = "Address not specify, process aborted")
    private String address;
    @NotNull(message = "Station number not specify, process aborted")
    private Integer station;
}
