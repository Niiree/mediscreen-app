package com.mediscreen.patient.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;

@Service
public class PatientService {

	private static final Logger logger = LogManager.getLogger(PatientService.class);

	private final PatientRepository patientRepository;

	@Autowired
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	/**
	 * Crée un nouveau patient.
	 */
	public Patient create(Patient patient) {
		// Optionnel : Sanitize / valider / copier depuis un DTO
		Patient toSave = new Patient(
				patient.getFirstName(),
				patient.getLastName(),
				patient.getBirthdate(),
				patient.getGender(),
				patient.getCity(),
				patient.getAddress(),
				patient.getPostalCode(),
				patient.getPhoneNumber()
		);
		Patient saved = patientRepository.save(toSave);
		logger.info("Created new patient with id={}", saved.getId());
		return saved;
	}

	/**
	 * Récupère un patient par son ID, ou lève une exception si introuvable.
	 */
	public Patient getPatient(Integer id) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " was not found"));
		logger.info("Fetched patient with id={}", id);
		return patient;
	}

	/**
	 * Récupère tous les patients.
	 */
	public List<Patient> getAll() {
		logger.info("Fetching all patients");
		return patientRepository.findAll();
	}

	/**
	 * Met à jour un patient existant.
	 */
	@Transactional
	public Patient update(Integer id, Patient incoming) {
		Patient existing = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient with ID " + id + " was not found"));

		// Mise à jour des champs modifiables
		existing.setFirstName(incoming.getFirstName());
		existing.setLastName(incoming.getLastName());
		existing.setBirthdate(incoming.getBirthdate());
		existing.setGender(incoming.getGender());
		existing.setCity(incoming.getCity());
		existing.setAddress(incoming.getAddress());
		existing.setPostalCode(incoming.getPostalCode());
		existing.setPhoneNumber(incoming.getPhoneNumber());

		// Comme on est en contexte transactionnel, pas besoin d'appeler save() explicitement si l'entité est attachée.
		Patient updated = patientRepository.save(existing); // optionnel mais explicite
		logger.info("Updated patient with id={}", id);
		return updated;
	}

	/**
	 * Supprime un patient par son ID. Lève une exception si introuvable.
	 */
	public void delete(Integer id) {
		if (!patientRepository.existsById(id)) {
			logger.warn("Attempted to delete nonexistent patient with id={}", id);
			throw new PatientNotFoundException("Patient with ID " + id + " was not found");
		}
		patientRepository.deleteById(id);
		logger.info("Deleted patient with id={}", id);
	}
}
