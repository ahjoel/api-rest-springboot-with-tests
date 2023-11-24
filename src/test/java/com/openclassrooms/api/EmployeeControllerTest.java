package com.openclassrooms.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.api.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEMployees() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Laurent")));
    }

    @Test
    public void testGetEmployee() throws Exception{
        mockMvc.perform(get("/employee/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));

        mockMvc.perform(get("/employee/{id}", 6L))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveEmployee() throws Exception{
        Employee employeeToCreate = new Employee();
        employeeToCreate.setFirstName("John");
        employeeToCreate.setLastName("Doe");
        employeeToCreate.setMail("j.doe@gmail.com");
        employeeToCreate.setPassword("p@$$");

        // Convertis l'objet Employee en JSON
        String employeeJson = objectMapper.writeValueAsString(employeeToCreate);

        // Effectue une requête POST sur l'endpoint "/employee" avec le JSON de l'employé
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.mail", is("j.doe@gmail.com")));

    }
}
