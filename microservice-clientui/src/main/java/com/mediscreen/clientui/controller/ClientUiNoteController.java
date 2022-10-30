package com.mediscreen.clientui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mediscreen.clientui.beans.NoteBean;
import com.mediscreen.clientui.proxies.NotesProxies;

@Controller
public class ClientUiNoteController {

	
	@Autowired
	private NotesProxies notesProxies;

	@GetMapping("/patient/{id}/notes")
	public String readNotes(@PathVariable("id") Integer id, Model model){
		List<NoteBean> notes = notesProxies.getAllNotes(id);
		model.addAttribute("notes", notes);
		return "note/notesList";
	}
	
	
	@GetMapping("/patient/{id}/notes/add")
	public String AccessAddNotesForm(@PathVariable("id") Integer id, NoteBean noteBean, Model model){
		model.addAttribute("idPatient", id);
		return "note/formAddNote";
	}
	
	
	@PostMapping("/patient/{id}/notes/add") 
	public String addNote (@PathVariable("id") Integer id, @Valid NoteBean noteBean, BindingResult result, Model model){
		if (result.hasErrors()){
			return "note/formAddNote";
		}	
		model.addAttribute("noteBean", noteBean);
		noteBean.setIdPatient(id);
		notesProxies.addNote(id,noteBean);
		return "redirect:/patient/{id}/notes";
	}
	

	@GetMapping("/patient/{patientId}/notes/update/{noteId}")
	public String updatePatient(@PathVariable("noteId") String noteId, @PathVariable("patientId") Integer idPatient, Model model){
		model.addAttribute("noteUpdate", notesProxies.getNote(noteId).get());
		model.addAttribute("idPatient", idPatient);
		return "note/FormUpdateNote";
	}
	
	@PostMapping("/patient/{patientId}/notes/{noteId}")
	public String updatePatient(@PathVariable("noteId") String noteId, @PathVariable("patientId") Integer idPatient, @Valid NoteBean noteUpdate, 
			BindingResult result, Model model){

		if (result.hasErrors()){
			return "note/FormUpdateNote";
		}	
		notesProxies.updateNote(noteId, noteUpdate);	
		return "redirect:/patient/{patientId}/notes";
	}
	
	@GetMapping("/patient/{patientId}/notes/delete/{noteId}")
	public String deleteNote(@PathVariable("noteId") String noteId, @PathVariable("patientId") Integer idPatient){
		notesProxies.deleteNote(noteId);
		return "redirect:/patient/{patientId}/notes";
	}	

}
