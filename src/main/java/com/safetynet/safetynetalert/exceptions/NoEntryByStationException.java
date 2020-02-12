package com.safetynet.safetynetalert.exceptions;

import com.safetynet.safetynetalert.loggerargument.LogArgs;

public class NoEntryByStationException extends NoEntryException{
    /**
     * throw if there is no addresses affiliated to the station
     *
     */

    public NoEntryByStationException(String station) {
        super(LogArgs.getNoEntryByStationMessage(station));
    }
}
