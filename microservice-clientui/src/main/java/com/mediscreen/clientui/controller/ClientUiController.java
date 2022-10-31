package com.mediscreen.clientui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientsProxies;

@Controller
public class ClientUiController {
	
	private final PatientsProxies patientsProxy;

	public ClientUiController(PatientsProxies patientsProxy){
	       this.patientsProxy = patientsProxy;
	}
	   
	@GetMapping("/")
	public String index(Model model) {
		List<PatientBean> patients = patientsProxy.getAllPatient();
	    model.addAttribute("patients", patients);
		return "patient/index";
	}

	@GetMapping("/patient/add")
	public String addPatient(PatientBean patientBean, Model model)	{
		return "patient/formAddPatient";
	}
	
	
	@PostMapping("/patient/add") 
	public String addPatient(@Valid @ModelAttribute PatientBean patientBean, BindingResult result, Model model){
		if (result.hasErrors()){
			return "patient/formAddPatient";
		}
		model.addAttribute("patientBean", patientBean);
		patientsProxy.addPatient(patientBean);	
		return "redirect:/";
	}
	

	
	@GetMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id, Model model){
		PatientBean patientBean = patientsProxy.getPatient(id).get();
		model.addAttribute("patientBean", patientBean);
		return "patient/formUpdatePatient";
	}
	
	
	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id, @Valid @ModelAttribute PatientBean patientBean, 
			 Model model,BindingResult result){
		if (result.hasErrors()){
			return "patient/formUpdatePatient";
		}

		patientsProxy.updatePatient(id, patientBean);
		return "redirect:/";
	}
	
	@GetMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable("id") Integer id){
		patientsProxy.deletePatient(id);
		return "redirect:/";
	}
}