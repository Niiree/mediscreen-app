package com.mediscreen.clientui.proxies;

import java.util.List;
import java.util.Optional;

import com.mediscreen.clientui.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
		name = "notes-service",
		url = "${patients.base-url}/notes",      // ex: http://microservice-gateway:8080
		configuration = com.mediscreen.clientui.config.FeignConfig.class
)
public interface NotesProxies {

	// GET toutes les notes d'un patient
	@GetMapping("/patient/{id}")
	List<NoteBean> getAllNotes(@PathVariable("id") Integer id);

	// ADD note
	@PostMapping("/patient/{id}/")
	NoteBean addNote(@PathVariable("id") Integer id, @RequestBody NoteBean noteBean);

	// GET une note
	@GetMapping("/{noteId}")
	Optional<NoteBean> getNote(@PathVariable("noteId") String id);

	// UPDATE note (POST selon ton backend actuel)
	@PutMapping("/{noteId}")
	NoteBean updateNote(@PathVariable("noteId") String id, @RequestBody NoteBean noteBean);

	// DELETE note (GET selon ton backend actuel)
	@DeleteMapping("/{noteId}")
	void deleteNote(@PathVariable("noteId") String id);
}
