package com.safetynet.safetynetalert.unit;

import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.enumerations.DataEntry;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class DataTest {
    private ArrayList<Person> personlist;
    private ArrayList<Medicalrecord> medicalrecords;
    private Set<Firestation> firestations;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public DataTest() {
        personlist = new ArrayList<>();
        medicalrecords = new ArrayList<>();
        firestations = new HashSet<>();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Schaffer");
        person1.setAddress("3333 broadway");
        person1.setCity("NYC");
        person1.setZip(9887);
        person1.setPhone("555-555-55");
        person1.setEmail("jo@jo.co");
        personlist.add(person1);
        Person person2 = new Person();
        person2.setFirstName("Jeff");
        person2.setLastName("Loomis");
        person2.setAddress("Falun Kvid");
        person2.setCity("Malmo");
        person2.setZip(98777);
        person2.setPhone("558-44-4444");
        person2.setEmail("ji@ji.co");
        personlist.add(person2);
        Person person3 = new Person();
        person3.setFirstName("Nita");
        person3.setLastName("Strauss");
        person3.setAddress("3333 brodaway");
        person3.setCity("NYC");
        person3.setZip(79877);
        person3.setPhone("558-44-484");
        person3.setEmail("ju@ju.co");
        personlist.add(person3);
        Person person4 = new Person();
        person4.setFirstName("Ola");
        person4.setLastName("Englund");
        person4.setAddress("Edelstein");
        person4.setCity("Hamburg");
        person4.setZip(1177);
        person4.setPhone(null);
        person4.setEmail("mi@mi.co");
        personlist.add(person4);
        Medicalrecord medicalrecord1 = new Medicalrecord();
        medicalrecord1.setFirstName("John");
        medicalrecord1.setLastName("Schaffer");
        medicalrecord1.setBirthdate(LocalDate.parse("12/12/2012", formatter));
        medicalrecord1.setMedications(getMedicationList1());
        medicalrecord1.setAllergies(getMedicationList2());
        medicalrecords.add(medicalrecord1);
        Medicalrecord medicalrecord2 = new Medicalrecord();
        medicalrecord2.setFirstName("Jeff");
        medicalrecord2.setLastName("Loomis");
        medicalrecord2.setBirthdate(LocalDate.parse("12/25/1978", formatter));
        medicalrecord2.setMedications(getMedicationList2());
        medicalrecord2.setAllergies(getMedicationList3());
        medicalrecords.add(medicalrecord2);
        Medicalrecord medicalrecord3 = new Medicalrecord();
        medicalrecord3.setFirstName("Nita");
        medicalrecord3.setLastName("Strauss");
        medicalrecord3.setBirthdate(LocalDate.parse("12/02/1985", formatter));
        medicalrecord3.setMedications(getMedicationList4());
        medicalrecord3.setAllergies(getMedicationList3());
        medicalrecords.add(medicalrecord3);
        Medicalrecord medicalrecord4 = new Medicalrecord();
        medicalrecord4.setFirstName("Ola");
        medicalrecord4.setLastName("Englund");
        medicalrecord4.setBirthdate(LocalDate.parse("08/12/2009", formatter));
        medicalrecord4.setMedications(getMedicationList1());
        medicalrecord4.setAllergies(getMedicationList4());
        medicalrecords.add(medicalrecord4);
        Firestation firestation1 = new Firestation();
        firestation1.setStation(1);
        firestation1.setAddress("3333 broadway");
        firestations.add(firestation1);
        Firestation firestation2 = new Firestation();
        firestation2.setStation(1);
        firestation2.setAddress("Falun Kvid");
        firestations.add(firestation2);
        Firestation firestation3 = new Firestation();
        firestation3.setStation(2);
        firestation3.setAddress("Edelstein");
        firestations.add(firestation3);
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

    public ArrayList<Person> getPersonlist() {
        return personlist;
    }

    public ArrayList<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public Set<Firestation> getFirestations() {
        return firestations;
    }

    public Firestation getFirestation1() {
        Firestation firestation1 = new Firestation();
        firestation1.setStation(1);
        firestation1.setAddress("3333 broadway");
        return firestation1;
    }
}
