package com.mediscreen.clientui.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mediscreen.clientui.beans.DiabeteBean;
import com.mediscreen.clientui.beans.DiabetesRiskResponse;
import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.DiabetesProxies;
import com.mediscreen.clientui.proxies.NotesProxies;
import com.mediscreen.clientui.proxies.PatientsProxies;

@Controller
public class ClientUiDiabeteController {

	private final DiabetesProxies diabetesProxies;
	private final PatientsProxies patientProxies;
	private final NotesProxies noteProxies;

	public ClientUiDiabeteController(DiabetesProxies diabetesProxies,
									 PatientsProxies patientProxies,
									 NotesProxies noteProxies) {
		this.diabetesProxies = diabetesProxies;
		this.patientProxies = patientProxies;
		this.noteProxies = noteProxies;
	}


	@GetMapping("/patient/{id}/diabetes/getInfo")
	public String getDiabetesInfo(@PathVariable("id") Integer patientId, Model model) {

		// Patient
		Optional<PatientBean> patientOpt = patientProxies.getPatient(patientId);
		if (patientOpt.isEmpty()) {
			return "redirect:/";
		}
		PatientBean patient = patientOpt.get();

		// Notes -> commentaires
		List<String> notesComments = noteProxies.getAllNotes(patientId).stream()
				.map(NoteBean::getComment)
				.collect(Collectors.toList());

		// Payload d’analyse
		DiabeteBean diabetes = new DiabeteBean();
		diabetes.setPatientBirthdate(patient.getBirthdate());
		diabetes.setPatientGender(patient.getGender());
		diabetes.getPatientNote().addAll(notesComments);

		// Appel au service d'analyse (POST /patient/{id}/diabetes/risk)
		DiabetesRiskResponse resp = diabetesProxies.assessRisk(patientId, diabetes);

		// Modèle pour la vue
		model.addAttribute("patient", patient);
		model.addAttribute("riskLevel", resp != null ? resp.getRiskLevel() : null);
		model.addAttribute("resultMessage", resp != null ? resp.getMessage() : "No result");

		return "diabete/diabetes";
	}
}
