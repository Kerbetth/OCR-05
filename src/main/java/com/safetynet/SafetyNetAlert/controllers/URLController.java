package com.safetynet.SafetyNetAlert.controllers;


import com.safetynet.SafetyNetAlert.services.*;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
public class URLController
{

  private JsonOutputService setJsonOutput;

   @RequestMapping(method = { RequestMethod.GET }, value = { "/phoneAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getPhoneNumbers(@RequestParam(required = false) Integer firestation) {
       setJsonOutput = new JsonOutputServicePhoneAlert(firestation);
       String output = setJsonOutput.getRequest();
       return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/firestation" }, produces = { "application/json" })
    @ResponseBody
    public String getFirestation(@RequestParam(required = false) Integer stationNumber) {
        setJsonOutput = new JsonOutputServiceFirestation(stationNumber);
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/childAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getChildAlert(@RequestParam(required = false) String address) {
        setJsonOutput = new JsonOutputServiceChildAlert(address);
        String output = setJsonOutput.getRequest();
        return output;
    }

    /*@Autowired
    @Qualifier("jsonOutputServicePhoneAlert")
    public void setJsonOutputPhoneAlert(JsonOutputService jsonOutputPhoneAlert) {
        this.setJsonOutputPhoneAlert = jsonOutputPhoneAlert;
    }*/

}
