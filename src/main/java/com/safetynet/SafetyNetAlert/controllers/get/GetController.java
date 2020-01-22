package com.safetynet.SafetyNetAlert.controllers.get;


import com.safetynet.SafetyNetAlert.services.getservices.*;
import com.safetynet.SafetyNetAlert.services.getservices.impl.GetURLService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class GetController
{

  private GetURLService setJsonOutput;

    @RequestMapping(method = { RequestMethod.GET }, value = { "/firestation" }, produces = { "application/json" })
    @ResponseBody
    public String getFirestation(@RequestParam(required = false) String stationNumber) {
        setJsonOutput = new GetFirestationURLService(stationNumber);
        String output = setJsonOutput.getRequest();
        return output;
    }

   @RequestMapping(method = { RequestMethod.GET }, value = { "/phoneAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getPhoneNumbers(@RequestParam(required = false) Integer firestation) {
       setJsonOutput = new GetPhoneAlertURLService(firestation);
       String output = setJsonOutput.getRequest();
       return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/childAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getChildAlert(@RequestParam(required = false) String address) {
        setJsonOutput = new GetChildAlertURLService(address);
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/fire" }, produces = { "application/json" })
    @ResponseBody
    public String getFire(@RequestParam(required = false) String address) {
        setJsonOutput = new GetFireURLService(address);
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/flood/stations" }, produces = { "application/json" })
    @ResponseBody
    public String getFloodStations(@RequestParam(required = false) String stations) {
        setJsonOutput = new GetFloodStationsURLService(stations);
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/personInfo" }, produces = { "application/json" })
    @ResponseBody
    public String getPersonInfo(@RequestParam(required = false) Map<String,String> name) {
        setJsonOutput = new GetPersonInfoURLService(name);
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/communityEmail" }, produces = { "application/json" })
    @ResponseBody
    public String getCommunityEmail(@RequestParam(required = false) String city) {
        setJsonOutput = new GetCommunityEmailURLService(city);
        String output = setJsonOutput.getRequest();
        return output;
    }

}
