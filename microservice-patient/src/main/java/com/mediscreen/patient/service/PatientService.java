package com.mediscreen.patient.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;

@Service
public class PatientService {
	
	private static final Logger logger = LogManager.getLogger(PatientService.class);
	
	@Autowired
	private PatientRepository patientRepository;
	
	
	public Patient create (Patient patient) {
		Patient newPatient = new Patient(
				patient.getFirstName(),
				patient.getLastName(),
				patient.getBirthdate(),
				patient.getGender(),
				patient.getCity(),
				patient.getAddress(),
				patient.getPostalCode(),
				patient.getPhoneNumber());	
		
		patientRepository.save(newPatient);
		logger.info("New patient create");
		return newPatient;	
	
	}
	
	public Patient getPatient(Integer id)  {
		 Optional<Patient> patient = patientRepository.findById(id);
		if(patient.isPresent()) {
			logger.info("get patient id : "+id);
			return patient.get();
		}
		throw new PatientNotFoundException("Patient was not found");
		
	}
	
	public List<Patient> getAll(){
		return patientRepository.findAll();
	}

	public Patient update(Integer id , Patient patient) {
		 Optional<Patient> Optionalpatient = patientRepository.findById(id);
		if(Optionalpatient.isPresent()) {
				Patient updatePatient = Optionalpatient.get();	
				updatePatient.setFirstName(patient.getFirstName());
				updatePatient.setLastName(patient.getLastName());
				updatePatient.setBirthdate(patient.getBirthdate());
				updatePatient.setGender(patient.getGender());
				updatePatient.setCity(patient.getCity());
				updatePatient.setAddress(patient.getAddress());
				updatePatient.setPostalCode(patient.getPostalCode());
				updatePatient.setPhoneNumber(patient.getPhoneNumber());
				
				patientRepository.save(updatePatient);
				logger.info("UPDATE :  patient id : "+id);
				return updatePatient;
		}else {
			logger.warn("Patient was not found...");
			throw new PatientNotFoundException("Patient was not found");
		}
	}
	
	public boolean delete(Integer id) {
		 Optional<Patient> Optionalpatient = patientRepository.findById(id);
		if(Optionalpatient.isPresent()) {
			logger.info("DELETE : patient id :"+id);
			patientRepository.deleteById(id);
			return true;
		}else {
			logger.warn("Deletion abort because patient was not found...");
			return false;
		}
	}
}


