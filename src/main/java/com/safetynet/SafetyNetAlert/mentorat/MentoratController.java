package com.safetynet.SafetyNetAlert.mentorat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/mentorat")
public class MentoratController {

    @Autowired
    private MentoratService service;

    @GetMapping(
            path = "/communityEmail",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<String> communityEmail(@RequestParam("city") String city) {
        return List.of("a@a.fr", "b@b.fr");
    }

    @GetMapping(
            path = "/personInfo",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<PersonInfo> personInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        return service.personInfo(firstName, lastName);
    }

}
