package com.mediscreen.clientui.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.proxies.NotesProxies;

@Controller
@RequestMapping
public class ClientUiNoteController {

	private final NotesProxies notesProxies;

	public ClientUiNoteController(NotesProxies notesProxies) {
		this.notesProxies = notesProxies;
	}

	// LIST
	// UI:   GET /patient/{patientId}/
	// Proxy:GET /notes/patient/{patientId}
	@GetMapping("/notes/patient/{patientId}/")
	public String listNotes(@PathVariable Integer patientId, Model model) {
		List<NoteBean> notes = notesProxies.getAllNotes(patientId);
		model.addAttribute("notes", notes);
		model.addAttribute("patientId", patientId);
		return "notes/notesList";
	}

	// SHOW ADD FORM
	// UI:   GET /patient/{patientId}/notes/add
	@GetMapping("/notes/patient/{patientId}/add")
	public String showAddNoteForm(@PathVariable Integer patientId, Model model) {
		model.addAttribute("noteBean", new NoteBean());
		model.addAttribute("patientId", patientId);
		return "notes/formAddNote";
	}

	// ADD
	// UI:   POST /patient/{patientId}/notes/add
	// Proxy:POST /notes/patient/{patientId}
	@PostMapping("/notes/patient/{patientId}/add")
	public String addNote(@PathVariable Integer patientId,
						  @Valid @ModelAttribute("noteBean") NoteBean noteBean,
						  BindingResult result) {
		if (result.hasErrors()) {
			return "notes/formAddNote";
		}
		noteBean.setIdPatient(patientId);
		notesProxies.addNote(patientId, noteBean);
		return "redirect:/notes/patient/" + patientId ;
	}

	// SHOW UPDATE FORM
	// UI:   GET /patient/{patientId}/notes/{noteId}/edit
	// Proxy:GET /notes/{noteId}
	@GetMapping("/notes/patient/{patientId}/{noteId}/edit")
	public String showUpdateNoteForm(@PathVariable Integer patientId,
									 @PathVariable String noteId,
									 Model model) {
		Optional<NoteBean> noteOpt = notesProxies.getNote(noteId);
		if (noteOpt.isEmpty()) {
			return "redirect:/notes/patient/" + patientId ;
		}
		model.addAttribute("noteBean", noteOpt.get());
		model.addAttribute("patientId", patientId);
		model.addAttribute("noteId", noteId);
		return "notes/formUpdateNote";
	}

	// UPDATE
	// UI:   POST /patient/{patientId}/notes/{noteId}/edit
	// Proxy:PUT  /notes/{noteId}   (ou POST /notes/update/{noteId} si backend legacy)
	@PostMapping("/notes/patient/{patientId}/{noteId}/edit")
	public String updateNote(@PathVariable Integer patientId,
							 @PathVariable String noteId,
							 @Valid @ModelAttribute("noteBean") NoteBean noteBean,
							 BindingResult result) {
		if (result.hasErrors()) {
			return "notes/formUpdateNote";
		}
		notesProxies.updateNote(noteId, noteBean);
		return "redirect:/notes/patient/" + patientId ;
	}

	// DELETE
	// UI:   GET /patient/{patientId}/notes/{noteId}/delete
	// Proxy:DELETE /notes/{noteId} (ou GET /notes/delete/{noteId} si legacy)
	@GetMapping("/notes/patient/{patientId}/{noteId}/delete")
	public String deleteNote(@PathVariable Integer patientId,
							 @PathVariable String noteId) {
		notesProxies.deleteNote(noteId);
		return "redirect:/notes/patient/" + patientId;
	}
}