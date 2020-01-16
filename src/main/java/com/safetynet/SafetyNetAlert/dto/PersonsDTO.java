package com.safetynet.SafetyNetAlert.dto;

import com.safetynet.SafetyNetAlert.dao.JSONDAO;
import org.json.simple.parser.*;
import java.io.*;
import java.util.Set;

import org.json.simple.*;

public class PersonsDTO
{

    
    public JSONArray getPersons() {
        JSONObject jsonData = JSONDAO.getJsonData();
        JSONArray persons = (JSONArray)jsonData.get((Object)"persons");
        return persons;
    }



}
