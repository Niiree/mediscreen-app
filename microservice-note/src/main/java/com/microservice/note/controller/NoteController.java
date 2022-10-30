package com.microservice.note.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.note.model.Note;
import com.microservice.note.service.NoteService;

@RestController
public class NoteController {
	
	private Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@Autowired
	private NoteService noteService;
	
	@GetMapping("/patient/{id}/notes")
	public ResponseEntity<List<Note>> getAllNote(@PathVariable ("id") Integer idPatient){
		
		return ResponseEntity.ok(noteService.getAllNotes(idPatient));
	}

	@PostMapping("patient/{id}/notes/add")
	public ResponseEntity<Object> addNote(@PathVariable("id") Integer idPatient, @RequestBody Note note){
		return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(idPatient, note));
	}
	
	@GetMapping("update/{id}")
	public ResponseEntity<Object> getNote (@PathVariable("id") String id ){
		try {
			return ResponseEntity.ok(noteService.get(id));
		}catch(Exception e) {
			e.printStackTrace();
			logger.warn("Not found");
			return noteNotFound();		}
	}
	
	@PostMapping("update/{id}")
	public ResponseEntity<Object> updateNote(@PathVariable("id")String id, @RequestBody Note note){
		try {
			return ResponseEntity.ok(noteService.update(id, note));
		}catch(Exception e) {
			e.printStackTrace();
			logger.warn("Not found");
			return noteNotFound();		}
		
	}
	
	@GetMapping("delete/{id}")
	public ResponseEntity<Object> deleteNote(@PathVariable("id")String id){
		if(noteService.delete(id)) {
			return ResponseEntity.ok("Deletion successful !");
		}else{
			return noteNotFound();
		}
	}
	
	private ResponseEntity<Object> noteNotFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note was not found.");
	}
}
