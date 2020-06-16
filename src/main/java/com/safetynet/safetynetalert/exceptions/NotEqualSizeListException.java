package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.exceptions.loggerargument.LogArgs;

public class NotEqualSizeListException extends IllegalArgumentException {
    /**
     * throw if the input data doesn't match the conventional format of the entry
     *
     */

    public NotEqualSizeListException() {
        super(LogArgs.getNotEqualSizeMessage());
    }
}
