package com.safetynet.safetynetalert.dao;

import com.safetynet.safetynetalert.domain.Person;
import com.safetynet.safetynetalert.jsonreader.JsonReaderWriter;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FirestationDao {

    @Autowired
    private JsonReaderWriter jsonReaderWriter;


    /**
     * the Dao method deploy the content of the jsonfile in a database composed with corresponding object
     * the @Value give the name of the file, which is variable for test purposes
     * writer method is the one who edited the jsonfile in order to registered the modifications
     * All the find methods get all sort of data from the constructed database
     *
     */

    public List<Firestation> getFirestations() {
        return jsonReaderWriter.getDtb().getFirestations();
    }

    public Firestation findFirestationByAddress(String address) {
        Optional<Firestation> firestation1 =
                getFirestations()
                        .stream()
                        .filter(firestation -> Objects.equals(address, firestation.getAddress()))
                        .findFirst();
        if (firestation1.isEmpty()) {
            throw new NoEntryException(address);
        }
        return firestation1.get();
    }

    public List<Firestation> findFirestationsByNumber(String stationNumbers) {
        List<Firestation> stationAddressList = new ArrayList<>();
        if (stationNumbers != null) {
            Set<String> stationNumbersSet = new HashSet<>((Arrays.asList(stationNumbers.split(","))));
            for (String stationNumber : stationNumbersSet) {
                stationAddressList.addAll(getFirestations()
                        .stream()
                        .filter(firestation -> Objects.equals(firestation.getStation(), Integer.parseInt(stationNumber)))
                        .collect(Collectors.toList()));
            }
            if (stationAddressList.isEmpty()) {
                return null;
            }
            return stationAddressList;
        } else {
            return null;
        }
    }

    public Integer getIdByAddress(String address) {
        Integer id = 0;
        List<Firestation> firestations = getFirestations();
        for (Firestation firestation :firestations) {
            if ((firestation.getAddress()).equals(address)){
                break;
            } else id++;
        }
        return id;
    }

    public void updateJson(List<Firestation> firestations){
        jsonReaderWriter.updateFirestations(firestations);
    }
}
