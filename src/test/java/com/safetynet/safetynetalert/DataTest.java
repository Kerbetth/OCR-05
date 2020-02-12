package com.safetynet.safetynetalert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.domain.Database;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.domain.Medicalrecord;
import com.safetynet.safetynetalert.domain.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@ExtendWith(MockitoExtension.class)
public class DataTest {
    private ArrayList<Person> personlist;
    private ArrayList<Medicalrecord> medicalrecords;
    private ArrayList<Firestation> firestations;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private Database database;

    public DataTest() {
        personlist = new ArrayList<>();
        medicalrecords = new ArrayList<>();
        firestations = new ArrayList<>();
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
        person3.setFirstName("Sarah");
        person3.setLastName("Claudius");
        person3.setAddress("3333 broadway");
        person3.setCity("NYC");
        person3.setZip(79877);
        person3.setPhone("558-44-484");
        person3.setEmail("ju@ju.co");
        personlist.add(person3);
        Person person4 = new Person();
        person4.setFirstName("Ola");
        person4.setLastName("Loomis");
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
        medicalrecord3.setFirstName("Sarah");
        medicalrecord3.setLastName("Claudius");
        medicalrecord3.setBirthdate(LocalDate.parse("12/02/1985", formatter));
        medicalrecord3.setMedications(getMedicationList4());
        medicalrecord3.setAllergies(getMedicationList3());
        medicalrecords.add(medicalrecord3);
        Medicalrecord medicalrecord4 = new Medicalrecord();
        medicalrecord4.setFirstName("Ola");
        medicalrecord4.setLastName("Loomis");
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
        database = new Database();
        database.setMedicalrecords(medicalrecords);
        database.setFirestations(firestations);
        database.setPersons(personlist);
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
        return new ArrayList<>();
    }

    public ArrayList<String> getMedicationList4() {
        ArrayList<String> med = new ArrayList<>();
        med.add("\"virus\"");
        med.add("\"othervirus\"");
        return med;
    }

    public Person getNewPerson() {
        Person person = new  Person();
        person.setFirstName("newName");
        person.setLastName("newLastName");
        person.setAddress("newAddress");
        person.setCity("newCity");
        person.setZip(000);
        person.setPhone("000000-000");
        person.setEmail("new@mail.com");
        return person;
    }

    public void writingCleanJsonDataTest(){
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = Obj.writeValueAsString(database);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("src/main/resources/datatest.json")) {
            file.write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Person> getPersonlist() {
        return personlist;
    }

    public ArrayList<Medicalrecord> getMedicalrecords() {
        return medicalrecords;
    }

    public ArrayList<Firestation> getFirestations() {
        return firestations;
    }

    public Firestation getFirestation1() {
        Firestation firestation1 = new Firestation();
        firestation1.setStation(1);
        firestation1.setAddress("3333 broadway");
        return firestation1;
    }
}
