package com.mediscreen.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mediscreen.clientui.beans.NoteBean;

@FeignClient(name="microservice-notes", url = "localhost:9002")
public interface NotesProxies {
	
	
	@GetMapping(value = "/patient/{id}/notes")
	public List<NoteBean> getAllNotes(@PathVariable("id") Integer id);
	
	
	@PostMapping(value="/patient/{id}/notes/add")
	public NoteBean addNote(@PathVariable("id") Integer id, NoteBean noteBean);
	
	
	@GetMapping(value = "update/{noteId}")
	public Optional<NoteBean> getNote(@PathVariable("noteId") String id);
	
	
	@PostMapping(value="update/{noteId}")
	public NoteBean updateNote(@PathVariable("noteId")String id, NoteBean noteBean);
	
	@GetMapping(value="delete/{noteId}")
	public void deleteNote(@PathVariable("noteId")String id);
	

}
