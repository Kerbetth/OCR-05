package com.safetynet.SafetyNetAlert.beans.firestation;

import javax.xml.bind.annotation.*;
import org.json.simple.*;
import java.util.*;

@XmlRootElement
public class PersonsList
{
    Set<JSONObject> personsList;
    Integer numberAdults;
    Integer numberChildren;
    
    public PersonsList() {
        this.personsList = new HashSet<JSONObject>();
    }
    
    public Set<JSONObject> getPhoneNumbers() {
        return this.personsList;
    }
    
    public void setPhoneNumbers(final Set<JSONObject> personsList) {
        this.personsList = personsList;
    }
    
    public Integer getAdultsNumber() {
        return this.numberAdults;
    }
    
    public void setAdultsNumber(final Integer numberAdults) {
        this.numberAdults = numberAdults;
    }
    
    public Integer getChildsNumber() {
        return this.numberChildren;
    }
    
    public void setChildrenNumber(final Integer numberChildren) {
        this.numberChildren = numberChildren;
    }
}
