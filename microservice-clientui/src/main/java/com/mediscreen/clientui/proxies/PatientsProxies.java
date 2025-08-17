package com.mediscreen.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.mediscreen.clientui.beans.PatientBean;

@FeignClient(
		name = "patient-service",
		url = "${patients.base-url}/patient",
		configuration = com.mediscreen.clientui.config.FeignConfig.class
)
public interface PatientsProxies {

	@GetMapping("/getAll")
	List<PatientBean> getAllPatient();

	@GetMapping("/{id}")
	Optional<PatientBean> getPatient(@PathVariable("id") Integer id);

	@PostMapping()
	PatientBean addPatient(@RequestBody PatientBean patientBean);

	@PutMapping("/{id}")
	PatientBean updatePatient(@PathVariable("id") Integer id, @RequestBody PatientBean patientBean);

	@DeleteMapping("/{id}")
	void deletePatient(@PathVariable("id") Integer id);
}