package com.safetynet.SafetyNetAlert.domain;


public class Firestation {

    private Integer id;
    private String address;
    private String station;

    public Firestation() {
    }

    public Firestation(Integer id, String address, String station) {
        this.id = id;
        this.address = address;
        this.station = station;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}