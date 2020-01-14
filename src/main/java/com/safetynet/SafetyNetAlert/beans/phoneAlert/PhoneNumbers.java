package com.safetynet.SafetyNetAlert.beans.phoneAlert;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlRootElement
public class PhoneNumbers
{
    Set<String> phoneNumbers;
    
    public PhoneNumbers() {
        this.phoneNumbers = new HashSet<String>();
    }
    
    public Set<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }
    
    public void setPhoneNumbers(final Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
