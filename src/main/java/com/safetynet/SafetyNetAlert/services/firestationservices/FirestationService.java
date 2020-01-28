package com.safetynet.SafetyNetAlert.services.firestationservices;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetyNetAlert.domain.Firestation;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FirestationService {

    DTO dTOFirestation = new DTO(Datatype.FSTATION);
    private ArrayList<Firestation> firestations;

    public FirestationService() {
        loadData();
    }

    public List<Firestation> listAllfirestations() {
        return firestations;
    }

    private void loadData() {
        firestations = new ArrayList<>();
        ArrayList<Map> firestationsList = dTOFirestation.getDataTypeContent();
        System.out.println(firestations.toString());
        for (int i = 0; i < firestationsList.size(); i++) {
            Firestation firestation = new Firestation();
            firestation.setId(i);
            firestation.setAddress(firestationsList.get(i).get(DataEntry.ADDRESS.getString()).toString());
            firestation.setStation(firestationsList.get(i).get(DataEntry.STATION.getString()).toString());
            firestations.add(firestation);
        }
    }

    public void addData(Firestation firestation) {
        if (firestation != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> firestationToAdd = oMapper.convertValue(firestation, Map.class);
            firestationToAdd.remove("id");
            dTOFirestation.addData(firestationToAdd);
        } else {
            throw new RuntimeException("Please write infos about the firestation");
        }
    }


    public void updateData(Firestation firestation) {
        if (firestation != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> firestationToEdit = oMapper.convertValue(firestation, Map.class);
            Integer id = (Integer) firestationToEdit.get("id");
            firestationToEdit.remove("id");
            dTOFirestation.setData(id, firestationToEdit);
        } else {
            throw new RuntimeException("Please write infos about the firestation");
        }
    }

    public void removeData(String Address) {
        int id = dTOFirestation.getIdByAddress(Address);
        dTOFirestation.removeData(id);
    }

    public Firestation getfirestationByAddress(String address){
        Integer id = dTOFirestation.getIdByAddress(address);
        Firestation firestation=firestations.get(id);
    return firestation;
    }
}
