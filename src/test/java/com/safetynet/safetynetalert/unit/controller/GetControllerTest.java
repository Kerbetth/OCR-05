package com.safetynet.safetynetalert.unit.controller;

import com.safetynet.safetynetalert.controllers.apicontrollers.GetControllers;
import com.safetynet.safetynetalert.service.getservice.GetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetControllers.class)
public class GetControllerTest {

    @MockBean
    private GetService getService;

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void getFirestationController() throws Exception {
        this.mockMvc.perform(get("/firestation")
                .param("stationNumber", "1")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getFirestationControllerWithNoBody() throws Exception {
        this.mockMvc.perform(get("/firestation")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getChildAlert() throws Exception {
        this.mockMvc.perform(get("/childAlert")
                .param("address", "myAddress")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getChildAlertWithNoBody() throws Exception {
        this.mockMvc.perform(get("/childAlert")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getPhoneAlert() throws Exception {
        this.mockMvc.perform(get("/phoneAlert")
                .param("firestation", "1")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getfire() throws Exception {
        this.mockMvc.perform(get("/fire")
                .param("address", "myAddress")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getfloodstations() throws Exception {
        this.mockMvc.perform(get("/flood/stations")
                .param("stations", "[\"myAddress\",\"anotherAddress\"]")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonInfo() throws Exception {
        this.mockMvc.perform(get("/personInfo")
                .param("firstName", "myname")
                .param("lastName", "myname")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getCommunityEmail() throws Exception {
        this.mockMvc.perform(get("/communityEmail")
                .param("city", "myCity")
        )
                .andExpect(status().isOk());
    }
}
