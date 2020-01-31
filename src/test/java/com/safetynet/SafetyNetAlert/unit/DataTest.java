package com.safetynet.SafetyNetAlert.unit;

import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


@ExtendWith(MockitoExtension.class)
public class DataTest {
    private ArrayList personsAgeList;
    private ArrayList personsFirstNameList;
    private ArrayList personsLastNameList;
    private ArrayList personsAddressList;
    private ArrayList personsCityList;
    private ArrayList personsZipList;
    private ArrayList personsPhoneList;
    private ArrayList personsEmailList;
    private ArrayList medBirthdateList;
    private ArrayList medMedicationsList;
    private ArrayList medAllergiesList;
    private Set<String> stationAddresses;
    private ArrayList firestationStationNumbers;
    private Set<String> noStationAddresses;


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
        noStationAddresses = new HashSet<>();
        medBirthdateList = new ArrayList<>();
        firestationStationNumbers = new ArrayList<>();
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
        medBirthdateList.add("02/04/1989");
        medBirthdateList.add("12/04/1979");
        medBirthdateList.add("02/08/1981");
        medBirthdateList.add("05/06/1999");
        medAllergiesList.add(getMedicationList1());
        medAllergiesList.add(getMedicationList2());
        medAllergiesList.add(getMedicationList3());
        medAllergiesList.add(getMedicationList4());
        medMedicationsList.add(getMedicationList1());
        medMedicationsList.add(getMedicationList2());
        medMedicationsList.add(getMedicationList3());
        medMedicationsList.add(getMedicationList4());
        stationAddresses.add("3333 broadway");
        firestationStationNumbers.add("1");
        firestationStationNumbers.add("2");
        firestationStationNumbers.add("1");
        firestationStationNumbers.add("3");
        noStationAddresses.add("noAddress");
    }

    public ArrayList getPersonsAgeList() {
        return personsAgeList;
    }

    public ArrayList getPersonsFirstNameList() {
        return personsFirstNameList;
    }

    public ArrayList getPersonsLastNameList() {
        return personsLastNameList;
    }

    public ArrayList getPersonsAddressList() {
        return personsAddressList;
    }

    public ArrayList getPersonsCityList() {
        return personsCityList;
    }

    public ArrayList getPersonsZipList() {
        return personsZipList;
    }

    public ArrayList getPersonsPhoneList() {
        return personsPhoneList;
    }

    public ArrayList getPersonsEmailList() {
        return personsEmailList;
    }

    public ArrayList getMedMedicationsList() {
        return medMedicationsList;
    }

    public ArrayList getMedAllergiesList() {
        return medAllergiesList;
    }

    public Set<String> getStationAddresses() {
        return stationAddresses;
    }

    public Set<String> getWrongStationAddresses() {
        return noStationAddresses;
    }

    public ArrayList<String> getMedicationList1() {
        ArrayList<String> med = new ArrayList<>();
        med.add("\"bric\"");
        med.add("\"broc\"");
        return med;
    }

    public ArrayList<String> getMedicationList2() {
        ArrayList<String> med = new ArrayList<>();
        med.add("\"paracet\"");
        return med;
    }

    public ArrayList<String> getMedicationList3() {
        ArrayList<String> med = new ArrayList<>();
        return med;
    }

    public ArrayList<String> getMedicationList4() {
        ArrayList<String> med = new ArrayList<>();
        med.add("\"virus\"");
        med.add("\"othervirus\"");
        return med;
    }

    public Map<String, String> getPersonData(Integer id) {
        Map<String, String> person = new HashMap<>();
        person.put(DataEntry.FNAME.getString(), (String) personsFirstNameList.get(id));
        person.put(DataEntry.LNAME.getString(), (String) personsLastNameList.get(id));
        person.put(DataEntry.ADDRESS.getString(), (String) personsAddressList.get(id));
        person.put(DataEntry.CITY.getString(), (String) personsCityList.get(id));
        person.put(DataEntry.ZIP.getString(), (String) personsZipList.get(id));
        person.put(DataEntry.PHONE.getString(), (String) personsPhoneList.get(id));
        person.put(DataEntry.EMAIL.getString(), (String) personsEmailList.get(id));
        return person;
    }

    public Map<String, String> getMedrecData(Integer id) {
        Map<String, String> person = new HashMap<>();
        person.put(DataEntry.FNAME.getString(), (String) personsFirstNameList.get(id));
        person.put(DataEntry.LNAME.getString(), (String) personsLastNameList.get(id));
        person.put(DataEntry.BIRTHDATE.getString(), (String) medBirthdateList.get(id));
        person.put(DataEntry.MEDIC.getString(), (String) medMedicationsList.get(id).toString());
        person.put(DataEntry.ALLERGI.getString(), (String) medAllergiesList.get(id).toString());
        return person;
    }

    public Map<String, String> getFirestationData(Integer id) {
        Map<String, String> person = new HashMap<>();
        person.put(DataEntry.STATION.getString(), (String) firestationStationNumbers.get(id));
        person.put(DataEntry.ADDRESS.getString(), (String) personsAddressList.get(id));
        return person;
    }

    public ArrayList<Map> get4PersonsData() {
        ArrayList<Map> persons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> person = new HashMap<>();

            person.put(DataEntry.FNAME.getString(), (String) personsFirstNameList.get(i));
            person.put(DataEntry.LNAME.getString(), (String) personsLastNameList.get(i));
            person.put(DataEntry.ADDRESS.getString(), (String) personsAddressList.get(i));
            person.put(DataEntry.CITY.getString(), (String) personsCityList.get(i));
            person.put(DataEntry.ZIP.getString(), (String) personsZipList.get(i));
            person.put(DataEntry.PHONE.getString(), (String) personsPhoneList.get(i));
            person.put(DataEntry.EMAIL.getString(), (String) personsEmailList.get(i));
            persons.add(person);
        }
        return persons;
    }

    public ArrayList<Map> get4MedrecData() {
        ArrayList<Map> persons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> medrec = new HashMap<>();
            medrec.put(DataEntry.FNAME.getString(), (String) personsFirstNameList.get(i));
            medrec.put(DataEntry.LNAME.getString(), (String) personsLastNameList.get(i));
            medrec.put(DataEntry.BIRTHDATE.getString(), (String) medBirthdateList.get(i));
            //medrec.put(DataEntry.MEDIC.getString(), (String) medMedicationsList.get(i));
            //medrec.put(DataEntry.ALLERGI.getString(), (String) medAllergiesList.get(i));
            persons.add(medrec);
        }
        return persons;
    }

    public Map getAllData() {
        Map all = new HashMap();
        all.put(Datatype.PERSO.getString(), get4PersonsData());
        all.put(Datatype.MEDREC.getString(), get4MedrecData());
        return all;
    }

    public Map getNewPersonData() {
        Map person = new HashMap();
        person.put(DataEntry.FNAME.getString(), "newName");
        person.put(DataEntry.LNAME.getString(), "newLastName");
        person.put(DataEntry.ADDRESS.getString(), "newAddress");
        person.put(DataEntry.CITY.getString(), "newCity");
        person.put(DataEntry.ZIP.getString(), "newZip");
        person.put(DataEntry.PHONE.getString(), "new-phone-000");
        person.put(DataEntry.EMAIL.getString(), "new@mail");
        return person;
    }

}
