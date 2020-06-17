package com.safetynet.safetynetalert.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalert.controllers.apicontrollers.FirestationControllers;
import com.safetynet.safetynetalert.domain.Firestation;
import com.safetynet.safetynetalert.service.CRUDservice.CRUDService;
import com.safetynet.safetynetalert.service.CRUDservice.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FirestationControllers.class)
public class FirestationControllerTest {

    @MockBean
    private FirestationService firestationService;

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void addFirestationController() throws Exception {
        String json = "{\"address\":\"newAddress\", \"station\":\"1\"}";
        this.mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void addFirestationControllerWithNoBody() throws Exception {
        this.mockMvc.perform(post("/firestation")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void setFirestationController() throws Exception {

        String json = "{\"address\":\"newAddress\", \"station\":\"1\"}";
        this.mockMvc.perform(put("/firestation/newAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void setFirestationControllerWithNoBody() throws Exception {
        this.mockMvc.perform(put("/firestation")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteFirestationController() throws Exception {
        this.mockMvc.perform(delete("/firestation/newAddress")
        )
                .andExpect(status().isOk());
    }
}
