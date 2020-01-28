package com.safetynet.SafetyNetAlert.unit.Services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class DataTest {
    private ArrayList<String> personsAgeList;
    private ArrayList<String> personsFirstNameList;
    private ArrayList<String> personsLastNameList;
    private ArrayList<String> personsAddressList;
    private ArrayList<String> personsCityList;
    private ArrayList<String> personsZipList;
    private ArrayList<String> personsPhoneList;
    private ArrayList<String> personsEmailList;
    private ArrayList<String> medMedicationsList;
    private ArrayList<String> medAllergiesList;
    private Set<String> stationAddresses;


    public DataTest() {
        personsAgeList = new ArrayList<>();
        personsFirstNameList = new ArrayList<>();
        personsLastNameList = new ArrayList<>();
        personsAddressList = new ArrayList<>();
        personsCityList = new ArrayList<>();
        personsZipList = new ArrayList<>();
        personsPhoneList = new ArrayList<>();
        personsEmailList = new ArrayList<>();
        medMedicationsList = new ArrayList<>();
        medAllergiesList = new ArrayList<>();
        stationAddresses = new HashSet<>();
        personsAgeList.add("15");
        personsAgeList.add("17");
        personsAgeList.add("40");
        personsAgeList.add("unknow");
        personsFirstNameList.add("John");
        personsFirstNameList.add("Michael");
        personsFirstNameList.add("Jeff");
        personsFirstNameList.add("Ola");
        personsLastNameList.add("Schaffer");
        personsLastNameList.add("Amott");
        personsLastNameList.add("Loomis");
        personsLastNameList.add("Englund");
        personsAddressList.add("3333 broadway");
        personsAddressList.add("Falun kvid");
        personsAddressList.add("3333 broadway");
        personsAddressList.add("Edelstein");
        personsCityList.add("NYC");
        personsCityList.add("Malmö");
        personsCityList.add("NYC");
        personsCityList.add("Hambourg");
        personsZipList.add("38888");
        personsZipList.add("84538");
        personsZipList.add("28725");
        personsZipList.add("28585");
        personsPhoneList.add("555-555-555");
        personsPhoneList.add("888-888-888");
        personsPhoneList.add("000-666-750");
        personsPhoneList.add("000-666-777");
        personsEmailList.add("jo@go.org");
        personsEmailList.add("ji@gi.org");
        personsEmailList.add("ma@mi.org");
        personsEmailList.add("pa@pi.org");
        medAllergiesList.add("[bric,a,broc]");
        medAllergiesList.add("[bric,a,brac]");
        medAllergiesList.add("[]");
        medAllergiesList.add("[]");
        medMedicationsList.add("[bric,medoc]");
        medMedicationsList.add("[]");
        medMedicationsList.add("[medoc]");
        medMedicationsList.add("[medoc]");
        stationAddresses.add("3333 broadway");
    }

    public ArrayList<String> getPersonsAgeList() {
        return personsAgeList;
    }

    public ArrayList<String> getPersonsFirstNameList() {
        return personsFirstNameList;
    }

    public ArrayList<String> getPersonsLastNameList() {
        return personsLastNameList;
    }

    public ArrayList<String> getPersonsAddressList() {
        return personsAddressList;
    }

    public ArrayList<String> getPersonsCityList() {
        return personsCityList;
    }

    public ArrayList<String> getPersonsZipList() {
        return personsZipList;
    }

    public ArrayList<String> getPersonsPhoneList() {
        return personsPhoneList;
    }

    public ArrayList<String> getPersonsEmailList() {
        return personsEmailList;
    }

    public ArrayList<String> getMedMedicationsList() {
        return medMedicationsList;
    }

    public ArrayList<String> getMedAllergiesList() {
        return medAllergiesList;
    }

    public Set<String> getStationAddresses() {
        return stationAddresses;
    }
}
