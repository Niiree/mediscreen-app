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
@RequestMapping("/patient/{patientId}/notes")
public class ClientUiNoteController {

	private final NotesProxies notesProxies;

	public ClientUiNoteController(NotesProxies notesProxies) {
		this.notesProxies = notesProxies;
	}

	@GetMapping
	public String listNotes(@PathVariable("patientId") Integer patientId, Model model) {
		List<NoteBean> notes = notesProxies.getAllNotes(patientId);
		model.addAttribute("notes", notes);
		model.addAttribute("patientId", patientId);
		return "note/notesList";
	}

	@GetMapping("/add")
	public String showAddNoteForm(@PathVariable("patientId") Integer patientId, Model model) {
		model.addAttribute("noteBean", new NoteBean());
		model.addAttribute("patientId", patientId);
		return "note/formAddNote";
	}

	@PostMapping("/add")
	public String addNote(@PathVariable("patientId") Integer patientId,
						  @Valid @ModelAttribute("noteBean") NoteBean noteBean,
						  BindingResult result) {
		if (result.hasErrors()) {
			return "note/formAddNote";
		}
		noteBean.setIdPatient(patientId);
		notesProxies.addNote(patientId, noteBean);
		return "redirect:/patient/{patientId}/notes";
	}

	@GetMapping("/update/{noteId}")
	public String showUpdateNoteForm(@PathVariable("noteId") String noteId,
									 @PathVariable("patientId") Integer patientId,
									 Model model) {
		Optional<NoteBean> noteOpt = notesProxies.getNote(noteId);
		if (noteOpt.isEmpty()) {
			return "redirect:/patient/{patientId}/notes"; // ou page d'erreur
		}
		model.addAttribute("noteBean", noteOpt.get());
		model.addAttribute("patientId", patientId);
		return "note/formUpdateNote";
	}

	@PostMapping("/update/{noteId}")
	public String updateNote(@PathVariable("noteId") String noteId,
							 @PathVariable("patientId") Integer patientId,
							 @Valid @ModelAttribute("noteBean") NoteBean noteBean,
							 BindingResult result) {
		if (result.hasErrors()) {
			return "note/formUpdateNote";
		}
		notesProxies.updateNote(noteId, noteBean);
		return "redirect:/patient/{patientId}/notes";
	}

	@GetMapping("/delete/{noteId}")
	public String deleteNote(@PathVariable("noteId") String noteId,
							 @PathVariable("patientId") Integer patientId) {
		notesProxies.deleteNote(noteId);
		return "redirect:/patient/{patientId}/notes";
	}
}
