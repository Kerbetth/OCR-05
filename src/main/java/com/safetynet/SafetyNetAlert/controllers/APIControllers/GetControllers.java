package com.safetynet.SafetyNetAlert.controllers.APIControllers;

import com.safetynet.SafetyNetAlert.beans.Child;
import com.safetynet.SafetyNetAlert.beans.HouseHold;
import com.safetynet.SafetyNetAlert.beans.PersonInfo;
import com.safetynet.SafetyNetAlert.APIservices.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/", method = RequestMethod.GET)
public class GetControllers {

    @Autowired
    private GetService service;

    @GetMapping(path = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> firestation(@RequestParam("stationNumber") Integer stationNumber) {
        return service.firestation(stationNumber);
    }

    @GetMapping(path = "/childAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Child> childAlert(@RequestParam("address") String address) {
        return service.childAlert(address);
    }

    @GetMapping(path = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> phoneAlert(@RequestParam("firestation") Integer stationNumber) {
        return service.phoneAlert(stationNumber);
    }

    @GetMapping(path = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> fire(@RequestParam("address") String address) {
        return service.fire(address);
    }

    @GetMapping(path = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseHold> floodStation(@RequestParam("stations") String stations) {
        return service.floodstations(stations);
    }

    @GetMapping(path = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonInfo> personInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return service.personInfo(firstName, lastName);
    }

    @GetMapping(path = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> communityEmail(@RequestParam("city") String city) {
        return service.communityEmail(city);
    }

}
