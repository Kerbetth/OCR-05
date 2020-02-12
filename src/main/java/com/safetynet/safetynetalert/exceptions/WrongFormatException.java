package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.loggerargument.LogArgs;

public class WrongFormatException extends IllegalArgumentException{
    /**
     * throw if the size of Person and Medicalrecords lists are not equal
     *
     */

    public WrongFormatException(String argument) {
        super(LogArgs.getWrongFormatMessage(argument));
    }
}
