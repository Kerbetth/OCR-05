package com.safetynet.safetynetalert.unit.controller;

import com.safetynet.safetynetalert.controllers.apicontrollers.MedicalRecordControllers;
import com.safetynet.safetynetalert.service.CRUDservice.MedicalrecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordControllers.class)
public class MedicalRecordControllerTest {

    @MockBean
    private MedicalrecordService medicalRecordService;

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void addmedicalRecordController() throws Exception {
        String json = "{\"firstName\":\"name1\",\"lastName\":\"name2\", \"allergies\":[\"myAll\", \"myAll2\"]}";
        this.mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void addmedicalRecordControllerWithNoBody() throws Exception {
        this.mockMvc.perform(post("/medicalRecord")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void setmedicalRecordController() throws Exception {

        String json = "{\"firstName\":\"name1\",\"lastName\":\"name2\", \"allergies\":[\"myAll\", \"myAll2\"]}";
        this.mockMvc.perform(put("/medicalRecord/newAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void setmedicalRecordControllerWithNoBody() throws Exception {
        this.mockMvc.perform(put("/medicalRecord")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletemedicalRecordController() throws Exception {
        this.mockMvc.perform(delete("/medicalRecord/newAddress")
        )
                .andExpect(status().isOk());
    }
}
