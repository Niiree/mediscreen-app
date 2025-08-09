package com.mediscreen.clientui.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

		// Récupération du patient
		Optional<PatientBean> patientOpt = patientProxies.getPatient(patientId);
		if (patientOpt.isEmpty()) {
			return "redirect:/";
		}
		PatientBean patient = patientOpt.get();

		// Récupération et transformation des notes
		List<String> notesComments = noteProxies.getAllNotes(patientId).stream()
				.map(NoteBean::getComment)
				.collect(Collectors.toList());

		// Construction de l'objet DiabeteBean
		DiabeteBean diabetes = new DiabeteBean();
		diabetes.setPatientBirthdate(patient.getBirthdate());
		diabetes.setPatientGender(patient.getGender());
		diabetes.getPatientNote().addAll(notesComments);

		// Appel au service d'analyse
		String result = diabetesProxies.getCase(patientId, diabetes);

		// Ajout au modèle
		model.addAttribute("result", result);
		model.addAttribute("patient", patient);

		return "diabete/diabetes";
	}
}
