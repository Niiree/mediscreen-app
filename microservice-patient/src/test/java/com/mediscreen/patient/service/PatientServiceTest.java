package com.mediscreen.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest{

	@InjectMocks
	PatientService patientService;
	
	@Mock
	PatientRepository patientRepository;
	
	Patient patientTest = new Patient(
			"firstName",
			"lastName",
			new Date(),
			"gender",
			"city",
			"address",
			0000,
			"phoneNumber");
	
	@Test
	public void createPatientTest(){
		patientTest.setId(1);
		Patient newPatientTest = patientService.create(patientTest);
		assertEquals("firstName", newPatientTest.getFirstName());
		assertEquals("lastName", newPatientTest.getLastName());
		assertEquals("address", newPatientTest.getAddress());
		assertEquals("phoneNumber", newPatientTest.getPhoneNumber());
	}
	
	@Test
	public void updatePatientTest(){
		patientTest.setId(1);
		Patient newPatientTest = new Patient();
		when(patientRepository.findById(1)).thenReturn(Optional.of(newPatientTest));
		
		patientService.update(1, patientTest);
		
		assertEquals("firstName", newPatientTest.getFirstName());
		assertEquals("lastName", newPatientTest.getLastName());
		assertEquals("address", newPatientTest.getAddress());
		assertEquals("phoneNumber", newPatientTest.getPhoneNumber());
		
	}
	
	@Test
	public void updatePatientNotFoundTest(){
		try{
			patientService.update(10, patientTest);
		}catch(Exception e) {
			assertEquals(e.getMessage(), "Patient was not found");
		}
		
		

	}
	
	@Test
	public void getPatientTest(){
		Patient newPatientTest = new Patient(
				"patientFirstName",
				"patientLastName",
				new Date(),
				"patientGender",
				"patientCity",
				"patientAddress",
				000,
				"patientPhoneNumber");
		newPatientTest.setId(1);
		
		when(patientRepository.findById(1)).thenReturn(Optional.of(newPatientTest));
		
		Patient readPatientDTO = patientService.getPatient(1);
		
		assertEquals("patientFirstName", readPatientDTO.getFirstName());
		assertEquals("patientLastName", readPatientDTO.getLastName());
		assertEquals("patientGender", readPatientDTO.getGender());
		assertEquals("patientAddress", readPatientDTO.getAddress());
	}
	
	@Test
	public void getPatientNotFoundTest(){
		Patient newPatientTest = new Patient(
				"patientFirstName",
				"patientLastName",
				new Date(),
				"patientGender",
				"patientCity",
				"patientAddress",
				000,
				"patientPhoneNumber");
	
		
		newPatientTest.setId(1);
		
		try {
		Patient readPatientDTO = patientService.getPatient(1);
	
		}catch(Exception e) {
			assertEquals(e.getMessage(), "Patient was not found");
		}
		
		
	}
	
	@Test
	public void readAllPatientsTest()	{
		
		List<Patient> patientListTest = new ArrayList<>();
		Patient patient = new Patient(
				"patientFirstName",
				"patientLastName",
				new Date(),
				"patientGender",
				"patientCity",
				"patientAddress",
				000,
				"patientPhoneNumber");
	
		patientListTest.add(patient);
		
		when(patientRepository.findAll()).thenReturn(patientListTest);
		
		List<Patient> patientList = patientService.getAll();
		
		assertTrue(patientList.get(0).getFirstName().equals("patientFirstName"));
		
	}
	
	@Test
	public void deletePatientTest(){
		Patient newPatientTest = new Patient(
				"patientFirstName",
				"patientLastName",
				new Date(),
				"patientGender",
				"patientCity",
				"patientAddress",
				000,
				"patientPhoneNumber");
	
		when(patientRepository.findById(1)).thenReturn(Optional.of(newPatientTest));
		newPatientTest.setId(1);
		patientService.delete(1);
		patientRepository.delete(newPatientTest);
		
		verify(patientRepository).delete(newPatientTest);
	}
	
	@Test
	public void deletePatientNotFoundTest(){
		Patient newPatientTest = new Patient(
				"patientFirstName",
				"patientLastName",
				new Date(),
				"patientGender",
				"patientCity",
				"patientAddress",
				000,
				"patientPhoneNumber");
	
		
		newPatientTest.setId(1);
		when(patientRepository.findById(1)).thenReturn(Optional.of(newPatientTest));
		try {
		patientService.delete(1);
		}catch(Exception e) {
		assertEquals(e.getMessage(), "Patient was not found");
		}
	
	}

	

}