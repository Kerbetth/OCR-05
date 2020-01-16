package com.safetynet.SafetyNetAlert.services;


import com.safetynet.SafetyNetAlert.dto.MedicalRecordsDTO;
import com.safetynet.SafetyNetAlert.dto.PersonsDTO;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonOutputServiceChildAlert implements JsonOutputService {

    PersonsDTO jsonDataDTO = new PersonsDTO();
    MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();

    private String address;

    public JsonOutputServiceChildAlert(String address) {
        this.address = address;
    }

    @Override
    public String getRequest() {
        /*try {
            String prevURL="";
            String decodeURL = address;
            while(!prevURL.equals(decodeURL))
            {
                prevURL=decodeURL;
                decodeURL= URLDecoder.decode( decodeURL, "UTF-8" );
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();;
        }*/
        ArrayList<Map<String, String>> childrenList = medicalRecordsDTO.getChildrenFirstNameLastNameAndAge();
        ArrayList<Map<String, String>> personsList = jsonDataDTO.getPersons();
        for (Map<String, String> child : childrenList) {
            ArrayList<String> householdMemberList = new ArrayList<>();
            for (Map<String, String> person : personsList) {
                if (child.get("firstName").equals(person.get("firstName")) && child.get("lastName").equals(person.get("lastName"))) {
                    if (person.get("address").equals(address) || address == null);
                    for (Map<String, String> personbis : personsList) {
                        if (personbis.get("address").equals(person.get("address")) && !personbis.get("firstName").equals(child.get("firstName"))) {
                            householdMemberList.add(personbis.get("firstName"));
                        }
                    }
                }
            }
            child.put("householdMemberList", householdMemberList.toString());
        }


        return childrenList.toString();
    }


}
