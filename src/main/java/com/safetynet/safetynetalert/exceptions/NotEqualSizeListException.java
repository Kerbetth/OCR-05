package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.loggerargument.LogArgs;

public class NotEqualSizeListException extends InternalError{
    /**
     * throw if the input data doesn't match the conventional format of the entry
     *
     */

    public NotEqualSizeListException() {
        super(LogArgs.getNotEqualSizeMessage());
    }
}
