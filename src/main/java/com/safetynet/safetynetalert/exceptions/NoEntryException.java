package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.exceptions.loggerargument.LogArgs;

public class NoEntryException extends NullPointerException{
    /**
     * throw if there is no entry corresponding with the given name
     *
     */

    public NoEntryException(String argument) {
        super(LogArgs.getNoEntryMessage(argument));
    }
}
