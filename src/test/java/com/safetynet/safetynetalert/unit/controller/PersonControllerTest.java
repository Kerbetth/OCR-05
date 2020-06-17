package com.safetynet.safetynetalert.unit.controller;

import com.safetynet.safetynetalert.controllers.apicontrollers.PersonControllers;
import com.safetynet.safetynetalert.service.crudservice.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonControllers.class)
public class PersonControllerTest {

    @MockBean
    private PersonService PersonService;

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void addPersonController() throws Exception {
        String json = "{\"firstName\":\"name1\",\"lastName\":\"name2\", \"city\":\"myCity\"}";
        this.mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void addPersonControllerWithNoBody() throws Exception {
        this.mockMvc.perform(post("/person")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void setPersonController() throws Exception {

        String json = "{\"firstName\":\"name1\",\"lastName\":\"name2\", \"city\":\"myCity\"}";
        this.mockMvc.perform(put("/person/newAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void setPersonControllerWithNoBody() throws Exception {
        this.mockMvc.perform(put("/person")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePersonController() throws Exception {
        this.mockMvc.perform(delete("/person/newAddress")
        )
                .andExpect(status().isOk());
    }
}
