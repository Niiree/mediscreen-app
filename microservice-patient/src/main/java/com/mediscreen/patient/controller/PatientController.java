package com.mediscreen.patient.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;

@RestController
public class PatientController {
	
	private Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;
	
	@GetMapping("/patient/getAll") 
	public ResponseEntity<List<Patient>> getAllPatient(){
		
		return ResponseEntity.ok(patientService.getAll());	
	}
	
	@GetMapping("/patient/read/{id}")
	public ResponseEntity<Object> getPatient(@PathVariable("id") Integer id){
		try{
			Patient patient = patientService.getPatient(id);
			return ResponseEntity.ok(patient);
		}catch (Exception e) {
			e.printStackTrace();
			logger.warn("Not found");
			return patientNotFound();
		}	
	}
	
	@PostMapping("/patient/add")
	public ResponseEntity<Object> addPatient(@RequestBody Patient patient){
		return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(patient));
	}
	
	@PostMapping("/patient/update/{id}")
	public ResponseEntity<Object>  updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient){
		try {
			logger.info("try update patient");
			return ResponseEntity.ok(patientService.update(id, patient));
		}catch(PatientNotFoundException e) {
			e.printStackTrace();
			logger.warn("Not found");
			return patientNotFound();
		}
		
	}
	
	@GetMapping("/patient/delete/{id}")
	public ResponseEntity<Object> deletePatient(@PathVariable("id") Integer id)	{
		if(patientService.delete(id)) {
			return ResponseEntity.ok("Deletion successful !");
		}else {
			return patientNotFound();
		}	
	}
	
	private ResponseEntity<Object> patientNotFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient was not found.");
	}

}
