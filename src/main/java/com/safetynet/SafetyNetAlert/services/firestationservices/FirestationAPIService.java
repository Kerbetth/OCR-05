package com.safetynet.SafetyNetAlert.services.firestationservices;




import com.safetynet.SafetyNetAlert.services.APIServices;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.dto.DTOFactory;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirestationAPIService implements APIServices {

    DTO dTOFirestation = new DTO(Datatype.FSTATION);

    public String postMethod(Map<String, String> firestationData) {
        Map<String, String> firestation = new HashMap<>();
        firestation.put(DataEntry.ADDRESS.getString(), firestationData.get(DataEntry.ADDRESS.getString()));
        firestation.put(DataEntry.STATION.getString(), firestationData.get(DataEntry.STATION.getString()));
        for (Map.Entry<String, String> value : firestation.entrySet()) {
            if (value.getValue() == null) {
                return "The " + value.getKey() + " value is not specify, operation aborted";
            }
        }
        dTOFirestation.addData(firestation);
        return "Ok";
    }

    public void putMethod(String address, Map<String,String> stationNumber) {
        Integer id = dTOFirestation.getIdByAddress(address);
        Map<String, String> firestation = (Map) dTOFirestation.getDataTypeContent().get(id);
        firestation.put(DataEntry.STATION.getString(),stationNumber.get(DataEntry.STATION.getString()) );
        dTOFirestation.setData(id, firestation);
    }

    public void deleteMethod(String address) {
        Integer id = dTOFirestation.getIdByAddress(address);
        dTOFirestation.removeData(id);
    }
}