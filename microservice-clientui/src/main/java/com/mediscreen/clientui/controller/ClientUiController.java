package com.mediscreen.clientui.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientsProxies;

@Controller
@RequestMapping("/")
public class ClientUiController {

	private final PatientsProxies patientsProxy;

	public ClientUiController(PatientsProxies patientsProxy) {
		this.patientsProxy = patientsProxy;
	}

	@GetMapping
	public String index(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatient();
		model.addAttribute("patients", patients);
		return "patient/index";
	}

	@GetMapping("/patient/add")
	public String showAddPatientForm(Model model) {
		model.addAttribute("patientBean", new PatientBean());
		return "patient/formAddPatient";
	}

	@PostMapping("/patient/add")
	public String addPatient(@Valid @ModelAttribute("patientBean") PatientBean patientBean, BindingResult result) {
		if (result.hasErrors()) {
			return "patient/formAddPatient";
		}
		patientsProxy.addPatient(patientBean);
		return "redirect:/";
	}

	@GetMapping("/patient/update/{id}")
	public String showUpdatePatientForm(@PathVariable("id") Integer id, Model model) {
		Optional<PatientBean> patientBeanOpt = patientsProxy.getPatient(id);
		if (patientBeanOpt.isEmpty()) {
			return "redirect:/"; // ou page d'erreur si tu veux
		}
		model.addAttribute("patientBean", patientBeanOpt.get());
		return "patient/formUpdatePatient";
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id,
								@Valid @ModelAttribute("patientBean") PatientBean patientBean,
								BindingResult result) {
		if (result.hasErrors()) {
			return "patient/formUpdatePatient";
		}
		patientsProxy.updatePatient(id, patientBean);
		return "redirect:/";
	}

	@PostMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable("id") Integer id) {
		patientsProxy.deletePatient(id);
		return "redirect:/";
	}
}
