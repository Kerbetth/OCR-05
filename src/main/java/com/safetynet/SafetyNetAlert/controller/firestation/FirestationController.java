package com.safetynet.SafetyNetAlert.controller.firestation;

import org.json.simple.*;
import com.safetynet.SafetyNetAlert.beans.firestation.*;
import com.safetynet.SafetyNetAlert.dao.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class FirestationController
{
    Set<JSONObject> personbyStation;
    Integer adults;
    Integer children;
    String stationAddress;
    JSONArray persons;
    JSONArray medicalrecords;
    
    public FirestationController() {
        this.personbyStation = new HashSet<JSONObject>();
    }
    
    @RequestMapping(method = { RequestMethod.GET }, value = { "/firestation" }, produces = { "application/json" })
    @ResponseBody
    public PersonsList getPhoneNumbers(@RequestParam(required = false) final Integer stationNumber) {
        this.personbyStation.clear();
        this.persons = JsonDataDAO.getPersons();
        final PersonsList personListBean = new PersonsList();
        this.stationAddress = JsonDataDAO.getFirestationAddress(stationNumber);
        personListBean.setPhoneNumbers(this.getPersonbyStationSet(this.stationAddress));
        return personListBean;
    }
    
    Set getPersonbyStationSet(final String stationAddress) {
        for (final Object personObj : this.persons) {
            final JSONObject person = (JSONObject)personObj;
            if (person.get((Object)"address").equals(stationAddress) || stationAddress == null) {
                final JSONObject personInfo = new JSONObject();
                personInfo.put((Object)"firstName", person.get((Object)"firstName"));
                personInfo.put((Object)"lastName", person.get((Object)"lastName"));
                personInfo.put((Object)"address", person.get((Object)"address"));
                personInfo.put((Object)"phone", person.get((Object)"phone"));
                this.personbyStation.add(personInfo);
            }
        }
        return this.personbyStation;
    }
}
