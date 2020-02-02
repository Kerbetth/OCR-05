package com.safetynet.SafetyNetAlert.beans;

import java.util.List;

public class Child {

    private String firstName;
    private String lastName;
    private String address;
    private Integer age;
    private List<String> houseHoldMembers;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getHouseHoldMembers() {
        return houseHoldMembers;
    }

    public void setHouseHoldMembers(List<String> houseHoldMembers) {
        this.houseHoldMembers = houseHoldMembers;
    }
}
