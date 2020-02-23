package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.loggerargument.LogArgs;

public class ExistingDataException extends IllegalArgumentException {
    /**
     * throw if the name of the Person allready exist in the database
     */

    public ExistingDataException(String argument) {
        super(LogArgs.getExistingNameMessage(argument));
    }
}
