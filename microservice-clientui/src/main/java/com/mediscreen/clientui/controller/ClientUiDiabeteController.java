package com.mediscreen.clientui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mediscreen.clientui.beans.DiabeteBean;
import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.DiabetesProxies;
import com.mediscreen.clientui.proxies.NotesProxies;
import com.mediscreen.clientui.proxies.PatientsProxies;

@Controller
public class ClientUiDiabeteController {
	@Autowired
	DiabetesProxies diabetesProxies;
	
	@Autowired
	PatientsProxies patientProxies;
	
	@Autowired
	NotesProxies noteProxies;
	
	
	@GetMapping("/patient/{id}/diabetes/getInfo")
	public String getCase(@PathVariable("id") Integer patientId, Model model)
	{
		DiabeteBean diabetes = new DiabeteBean();
		
		PatientBean patient = patientProxies.getPatient(patientId).get();
		
		List<NoteBean> noteList = noteProxies.getAllNotes(patientId);
		
		List<String> newList = new ArrayList<>();
		
		for (NoteBean noteBean : noteList)
		{
			newList.add(noteBean.getComment());
		}
	
		
		diabetes.setPatientBirthdate(patient.getBirthdate());
		diabetes.setPatientGender(patient.getGender());
		diabetes.getPatientNote().addAll(newList);
		System.out.println(patient.getBirthdate());
		
		String result = diabetesProxies.getCase(patientId, diabetes);
		
		model.addAttribute("result", result);
		
		return "diabete/diabetes";
	}
	
}
