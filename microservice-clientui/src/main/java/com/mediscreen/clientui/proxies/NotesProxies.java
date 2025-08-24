package com.mediscreen.clientui.proxies;

import java.util.List;
import java.util.Optional;

import com.mediscreen.clientui.beans.NoteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
		name = "notes-service",
		url = "${notes.base-url}",
		configuration = com.mediscreen.clientui.config.FeignConfig.class
)
public interface NotesProxies {
	@GetMapping("/patient/{id}")
	List<NoteBean> getAllNotes(@PathVariable("id") Integer id);

	@PostMapping("/patient/{id}")
	NoteBean addNote(@PathVariable("id") Integer id, @RequestBody NoteBean noteBean);

	@GetMapping("/{noteId}")
	Optional<NoteBean> getNote(@PathVariable("noteId") String id);

	@PutMapping("/{noteId}")
	NoteBean updateNote(@PathVariable("noteId") String id, @RequestBody NoteBean noteBean);

	@DeleteMapping("/{noteId}")
	void deleteNote(@PathVariable("noteId") String id);
}
