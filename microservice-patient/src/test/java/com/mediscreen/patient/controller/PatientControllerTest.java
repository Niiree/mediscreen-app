package com.mediscreen.patient.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest{

	
	@Autowired
	private WebApplicationContext context;
	
	@InjectMocks
	PatientController patientController;
	
	@MockBean
	PatientService patientService;
	
	@Mock
	PatientRepository patientRepository;
	
	private Patient patient;
	private MockMvc mockMvc;
	
	
	@BeforeEach
    public void setupMockmvc() {
	
		String response = "test";
		patient = new Patient(4, response, response, null, response, response, response, null, response);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }
	

    @Test
    public void getAll() throws Exception {
        String response = "firstName";
        List <Patient> listPatients = new ArrayList<>();
        listPatients.add(patient);
        when(patientService.getAll()).thenReturn(listPatients);

        MvcResult result = mockMvc.perform(get("/patient/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    @Test
    public void getPatient() throws Exception {
        String response = "firstName";
        when(patientService.getPatient(4)).thenReturn(patient);


        MvcResult result = mockMvc.perform(get("/patient/read/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }

    
    @Test
    public void getPatientError() throws Exception {
        String response = "Patient was not found.";
        when(patientService.getPatient(4)).thenThrow();//
        MvcResult result = mockMvc.perform(get("/patient/read/-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
    @Test
    public void addPatient() throws Exception {
        when(patientService.create(patient)).thenReturn(patient);
        MvcResult result = mockMvc.perform(post("/patient/add")
        		.content("{\"id\":1,\"firstName\":\"M\",\"lastName\":\"t\",\"birthdate\":\"2021-09-28T22:00:00.000+00:00\",\"gender\":\"M\",\"city\":\"city\",\"address\":\"address\",\"postalCode\":444,\"phoneNumber\":\"111\"}")
        		.contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isCreated())
                .andReturn();

    }
    
    @Test
    public void updatePatient() throws Exception {
        String response = "firstName";
        when(patientService.update(any(Integer.class),any())).thenReturn(patient);
        MvcResult result = mockMvc.perform(post("/patient/update/4")
           		.content("{\"id\":1,\"firstName\":\"M\",\"lastName\":\"t\",\"birthdate\":\"2021-09-28T22:00:00.000+00:00\",\"gender\":\"M\",\"city\":\"city\",\"address\":\"address\",\"postalCode\":444,\"phoneNumber\":\"111\"}")
        		.contentType(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
    @Test
    public void getDelete() throws Exception {
        String response = "Deletion successful !";
        when(patientService.delete(4)).thenReturn(true);
        MvcResult result = mockMvc.perform(get("/patient/delete/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
    @Test
    public void getDeleteError() throws Exception {
        String response = "Patient was not found.";
        MvcResult result = mockMvc.perform(get("/patient/delete/-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
    
    
}