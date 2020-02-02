package com.safetynet.SafetyNetAlert.beans;

import java.util.List;

public class HouseHold {
    private String address;
    private List<PersonFloodAndFire> personList;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonFloodAndFire> getpersonList() {
        return personList;
    }

    public void setpersonList(List<PersonFloodAndFire> personList) {
        this.personList =personList;
    }


}
