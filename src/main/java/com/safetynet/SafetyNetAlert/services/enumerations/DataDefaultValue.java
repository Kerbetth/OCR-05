package com.safetynet.SafetyNetAlert.services.enumerations;

public enum DataDefaultValue {

    UNKNOW("unknow"),
    EMPTY("[]");

    private String dataEntry;

    DataDefaultValue(String dataEntry) {
        this.dataEntry = dataEntry;
    }

    public String getString() {
        return dataEntry;
    }
}
