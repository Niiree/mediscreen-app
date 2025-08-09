package com.mediscreen.patient.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;

@RestController
@RequestMapping("/patient")
@Validated
public class PatientController {

	private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		logger.info("GET /patient - Fetching all patients");
		return ResponseEntity.ok(patientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable Integer id) {
		logger.info("GET /patient/{} - Fetching patient", id);
		Patient patient = patientService.getPatient(id); // lance PatientNotFoundException si introuvable
		return ResponseEntity.ok(patient);
	}

	@PostMapping
	public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
		logger.info("POST /patient - Creating new patient");
		Patient created = patientService.create(patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @Valid @RequestBody Patient patient) {
		logger.info("PUT /patient/{} - Updating patient", id);
		Patient updated = patientService.update(id, patient); // lance PatientNotFoundException si introuvable
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
		logger.info("DELETE /patient/{} - Deleting patient", id);
		patientService.delete(id); // lance PatientNotFoundException si introuvable
		return ResponseEntity.noContent().build(); // 204
	}
}
