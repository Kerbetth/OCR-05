package com.safetynet.SafetyNetAlert.services.enumerations;

public enum Datatype {

    PERSO("persons"),
    MEDREC("medicalrecords"),
    FSTATION("firestations");

    private String datatype;

    Datatype(String envUrl) {
        this.datatype = envUrl;
    }

    public String getString() {
        return datatype;
    }
}
