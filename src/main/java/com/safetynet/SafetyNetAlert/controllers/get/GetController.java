package com.safetynet.SafetyNetAlert.controllers.get;


import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.services.dto.DTO;
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
        setJsonOutput = new GetFirestationURLService(stationNumber, new DTO(Datatype.PERSO),new DTO(Datatype.MEDREC), new  DTO(Datatype.FSTATION));
        String output = setJsonOutput.getRequest();
        return output;
    }

   @RequestMapping(method = { RequestMethod.GET }, value = { "/phoneAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getPhoneNumbers(@RequestParam(required = false) Integer firestation) {
       setJsonOutput = new GetPhoneAlertURLService(firestation, new DTO(Datatype.PERSO));
       String output = setJsonOutput.getRequest();
       return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/childAlert" }, produces = { "application/json" })
    @ResponseBody
    public String getChildAlert(@RequestParam(required = false) String address) {
        setJsonOutput = new GetChildAlertURLService(address, new DTO(Datatype.PERSO),new DTO(Datatype.MEDREC));
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/fire" }, produces = { "application/json" })
    @ResponseBody
    public String getFire(@RequestParam(required = false) String address) {
        setJsonOutput = new GetFireURLService(address, new DTO(Datatype.PERSO),new DTO(Datatype.MEDREC));
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/flood/stations" }, produces = { "application/json" })
    @ResponseBody
    public String getFloodStations(@RequestParam(required = false) String stations) {
        setJsonOutput = new GetFloodStationsURLService(stations, new DTO(Datatype.PERSO), new DTO(Datatype.MEDREC), new DTO(Datatype.FSTATION));
        String output = setJsonOutput.getRequest();
        return output;
    }

    @RequestMapping(method = { RequestMethod.GET }, value = { "/personInfo" }, produces = { "application/json" })
    @ResponseBody
    public String getPersonInfo(@RequestParam(required = false) Map<String,String> name) {
        setJsonOutput = new GetPersonInfoURLService(name, new DTO(Datatype.PERSO), new DTO(Datatype.MEDREC));
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
