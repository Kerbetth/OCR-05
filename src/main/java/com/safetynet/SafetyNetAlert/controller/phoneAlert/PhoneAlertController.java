package com.safetynet.SafetyNetAlert.controller.phoneAlert;

import com.safetynet.SafetyNetAlert.beans.phoneAlert.*;
import com.safetynet.SafetyNetAlert.dao.*;
import org.springframework.web.bind.annotation.*;
import org.json.simple.*;
import java.util.*;

@RestController
public class PhoneAlertController
{
    Set<String> phoneNumbers;
    String stationAddress;
    
    public PhoneAlertController() {
        this.phoneNumbers = new HashSet<String>();
    }
    
    @RequestMapping(method = { RequestMethod.GET }, value = { "/phoneAlert" }, produces = { "application/json" })
    @ResponseBody
    public PhoneNumbers getPhoneNumbers(@RequestParam(required = false) final Integer firestation) {
        this.phoneNumbers.clear();
        final PhoneNumbers phoneNumbersBean = new PhoneNumbers();
        this.stationAddress = JsonDataDAO.getFirestationAddress(firestation);
        phoneNumbersBean.setPhoneNumbers(this.getPhoneNumbersSet(this.stationAddress));
        return phoneNumbersBean;
    }
    
    Set getPhoneNumbersSet(final String stationAddress) {
        final JSONArray persons = JsonDataDAO.getPersons();
        for (final Object personObj : persons) {
            final JSONObject person = (JSONObject)personObj;
            if (person.get((Object)"address").equals(stationAddress) || stationAddress == null) {
                final String phoneNumber = (String)person.get((Object)"phone");
                this.phoneNumbers.add(phoneNumber);
            }
        }
        return this.phoneNumbers;
    }
}
