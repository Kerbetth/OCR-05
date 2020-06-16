package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.exceptions.loggerargument.LogArgs;

public class NoFnameOrLnameException extends IllegalArgumentException{
    /**
     * throw if there is no entry with the given firstName or lastName
     *
     */

    public NoFnameOrLnameException(String fname, String lname) {
        super(LogArgs.getNoFnameOrLNameMessage(fname, lname));
    }
}
