package com.safetynet.SafetyNetAlert.services.firestationservices;


import com.safetynet.SafetyNetAlert.domain.Firestation;
import com.safetynet.SafetyNetAlert.services.dto.FirestationsDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FirestationService {

    FirestationsDTO firestationsDTO = new FirestationsDTO();
    private ArrayList<Firestation> firestations;

    public FirestationService() {
        loadfirestations();
    }

    public List<Firestation> listAllfirestations() {
        return firestations;
    }

    private void loadfirestations() {
        firestations = new ArrayList<>();
        ArrayList<Map<String, String>> firestationsList = firestationsDTO.getFirestations();
        for (int i = 0; i < firestationsList.size(); i++) {
            Firestation firestation = new Firestation();
            firestation.setId(i);
            firestation.setAddress(firestationsList.get(i).get("address"));
            firestation.setStation(firestationsList.get(i).get("station"));
            firestations.add(firestation);
        }
    }
/*
    public void addfirestation(Firestation firestation) {
        if (firestation != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> firestationToAdd = oMapper.convertValue(firestation, Map.class);
            Integer id = -1;
            firestationToAdd.remove("id");
            firestationsDTO.setfirestationsData(id, firestationToAdd);
        } else {
            throw new RuntimeException("Please write infos about the firestation");
        }
    }


    public void updatefirestation(Firestation firestation) {
        if (firestation != null) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> firestationToEdit = oMapper.convertValue(firestation, Map.class);
            Integer id = (Integer) firestationToEdit.get("id");
            firestationToEdit.remove("id");
            firestationsDTO.setFirestationsData(id, firestationToEdit);
        } else {
            throw new RuntimeException("Please write infos about the firestation");
        }
    }

    public Firestation getfirestationById(Integer id) {
        return firestations.get(id);
    }

    public void removefirestationDelete(Integer id) {
        firestationsDTO.removefirestationData(id);
    }

*/

}
