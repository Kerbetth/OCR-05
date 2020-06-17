package com.safetynet.safetynetalert.daodto.firestationdao;

import com.safetynet.safetynetalert.daodto.jsonreader.JsonReaderWriterInterface;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.exceptions.NoEntryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FirestationDao implements FirestationDaoInterface {

    @Autowired
    private JsonReaderWriterInterface jsonReaderWriter;


    @Override
    public List<Firestation> getFirestations() {
        return jsonReaderWriter.getDtb().getFirestations();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void updateJson(List<Firestation> firestations){
        jsonReaderWriter.updateFirestations(firestations);
    }
}
